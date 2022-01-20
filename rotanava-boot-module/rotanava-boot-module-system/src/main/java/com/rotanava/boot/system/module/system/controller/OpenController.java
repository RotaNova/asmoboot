package com.rotanava.boot.system.module.system.controller;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bean.OpenApiInfoBean;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.boot.system.module.dao.OpenApiMapper;
import com.rotanava.boot.system.module.dao.OpenApiParamMapper;
import com.rotanava.boot.system.module.dao.OpenAppMapper;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.RequestMethod;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-16 15:51
 **/
@RestController
@RequestMapping("/open")
@Log4j2
public class OpenController {

    @Autowired
    OpenApiMapper openApiMapper;

    @Autowired
    OpenAppMapper openAppMapper;

    @Autowired
    OpenApiParamMapper openApiParamMapper;

    @Autowired
    private SysSearchConfigMapper sysSearchConfigMapper;

    @Autowired
    RedisUtil redisUtil;

    private static final String openAppKey = "openAppKey:";


    @RequestMapping("/auth/getAccessToken")
    public RetData getAccessToken(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret) {
        QueryWrapper<OpenApp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_key", appKey);
        queryWrapper.eq("app_secret", appSecret);
        OpenApp openApp = openAppMapper.selectOne(queryWrapper);
        if (openApp == null) {
            throw new CommonException(SysErrorCode.SYS_ERROR_10);
        } else {
            String token = null;
            Object objToken = redisUtil.get(openAppKey + openApp.getId());
            if (objToken != null) {
                token = objToken.toString();
            }
            if (StringUtil.isNullOrEmpty(token)) {
                token = BaseUtil.getUId();
                redisUtil.set(openAppKey + openApp.getId(), token, 86400);
                redisUtil.set(token, openApp);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("accessToken", token);
            return new RetData(map);
        }
    }


    @RequestMapping("/api/**")
    @AutoLog(value = "第三方应用接口")
    public Object openApi(HttpServletRequest request) {

        //获取token 及 method
        String token = request.getHeader("access_token");
        if (StringUtil.isNullOrEmpty(token)) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_00);
        }
        OpenApp openApp = (OpenApp) redisUtil.get(token);
        if (openApp == null) {
            throw new CommonException(SysErrorCode.SYS_ERROR_10);
        }

        String method = request.getMethod();
        String path = request.getRequestURI().replaceFirst("/open/api", "");
        Integer methodType = RequestMethod.getRequestType(method).getRequestMethod();
        Integer appId = openApp.getId();

        log.info("【path】=={} 【methodType】=={} 【appId】=={}",path,methodType,appId);


        //目前支持GET 及 POST
        if (RequestMethod.GET.getMethodName().equals(method) || RequestMethod.POST.getMethodName().equals(method)) {

            OpenApiInfoBean openApiInfoBean = getOpenApiInfo(path, methodType, appId);
            if (openApiInfoBean != null) {
                long parseBegin = System.currentTimeMillis();
                JSONObject param = parseRequestParam(request);
                if (!checkParam(param, openApiInfoBean)) {
                    //参数校验不通过
                    return BuildUtil.buildParamError();
                }
                openApiInfoBean = parseSql(param, openApiInfoBean);
                long parseEnd = System.currentTimeMillis();

                long executeBegin = System.currentTimeMillis();
                Object o = parseResult(openApiInfoBean, param);
                long executeEnd = System.currentTimeMillis();

                log.info(" ---------------------------------------------------------------------------------");
                log.info("---->【开放api请求 ==》 path = {} ,method = {} ,appName = {} 】", path, method, openApp.getAppName());
                log.info("---->【执行的sql语句==》 {}】", openApiInfoBean.getOpenApi().getSqlText());
                log.info("---->【解析语句耗时==》 {}ms  执行语句耗时==》 {}ms】", (parseEnd - parseBegin), (executeEnd - executeBegin));
                log.info(" ---------------------------------------------------------------------------------");
                return o;
            } else {
                return BuildUtil.buildError(404, "找不到该接口");
            }
        }

        return null;

    }


    private Object parseResult(OpenApiInfoBean openApiInfoBean, JSONObject param) {
//        String sql = openApiInfoBean.getOpenApi().getSqlText();
//        Map<String, Object> map = openApiInfoBean.getSqlPramMap(param, openApiInfoBean.getOpenApi().getSqlText());
//        IPage<Object> page = PageUtils.startPage(param);
//        map.put("page", page);
//        try {
////            if (sql.contains("INSERT") || sql.contains("insert") || sql.contains("UPDATE") || sql.contains("update") || sql.contains("DELETE") || sql.contains("delete")) {
////                //执行插入、更新、删除语句 返回执行条数
////               Integer result =  sysSearchConfigMapper.doUpdateSqlByObject(map);
////               return RetData.ok(result);
////            }
//            //执行查询语句
//            List<Map> list = sysSearchConfigMapper.doSearchSqlByObject(map);
//            Long total = page.getTotal();
//            return BuildUtil.buildListVO(total, openApiInfoBean.getResultMap(list));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return BuildUtil.buildError(500, "接口执行失败，请联系接口提供者");
//        }
        return null;
    }


    /**
     * @description : 正则解析sql
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private OpenApiInfoBean parseSql(JSONObject json, OpenApiInfoBean openApiInfoBean) {
        String sql = openApiInfoBean.getOpenApi().getSqlText();
        //${}$ 正则
        Pattern paramPattern = Pattern.compile("\\$\\{([\\s\\S]*?)}\\$");
        //#{} 正则
        Pattern placeholder = Pattern.compile("\\#\\{([\\s\\S]*?)}");

        Matcher paramMatcher = paramPattern.matcher(sql);
        if (paramMatcher.find()) {
            for (int i = 0; i < paramMatcher.groupCount(); i++) {
                String groupStr = paramMatcher.group(i);

                Matcher placeholderMatcher = placeholder.matcher(groupStr);
                placeholderMatcher.find();
                String param = placeholderMatcher.group(0);
                param = param.replace("#{", "");
                param = param.replace("}", "");
                if (json.containsKey(param)) {
                    //参数不为空
                    groupStr = groupStr.replace("${", "");
                    groupStr = groupStr.replace("}$", "");
//                    String value = String.format("'%s'",json.getString(param));
//                    groupStr = groupStr.replace(placeholderMatcher.group(0),value);
                    sql = sql.replace(paramMatcher.group(i), groupStr);
                } else {
                    //参数为空
                    sql = sql.replace(paramMatcher.group(i), "");
                }
            }
        }

//        Matcher placeholderMatcher = placeholder.matcher(sql);
//        placeholderMatcher.find();
//        for (int i = 0; i < placeholderMatcher.groupCount(); i++) {
//            String groupStr = placeholderMatcher.group(i);
//            groupStr = groupStr.replace("#{","");
//            groupStr = groupStr.replace("}","");
//            if (json.containsKey(groupStr)){
//                String value = String.format("'%s'",json.getString(groupStr));
//                sql = sql.replace(placeholderMatcher.group(i),value);
//            }
//        }

        openApiInfoBean.getOpenApi().setSqlText(SQLUtils.formatMySql(sql));
        return openApiInfoBean;

    }


    private OpenApiInfoBean getOpenApiInfo(String path, Integer methodType, Integer appId) {
        OpenApi apiByRequest = openApiMapper.getApiByRequest(path, appId, methodType);
        if (apiByRequest != null) {
            OpenApiInfoBean openApiInfoBean = new OpenApiInfoBean();
            openApiInfoBean.setOpenApi(apiByRequest);
            QueryWrapper<OpenApiParam> queryWrapper = new QueryWrapper();
            queryWrapper.eq("api_id", apiByRequest.getId());
            List<OpenApiParam> openApiParams = openApiParamMapper.selectList(queryWrapper);
            for (OpenApiParam openApiParam : openApiParams) {
                if (openApiParam.getType() == 1) {
                    openApiInfoBean.addRequestParam(openApiParam);
                } else if (openApiParam.getType() == 2) {
                    openApiInfoBean.addResultParam(openApiParam);
                }
            }
            return openApiInfoBean;
        } else {
            return null;
        }
    }


    private boolean checkParam(JSONObject param, OpenApiInfoBean openApiInfoBean) {
        List<OpenApiParam> requestParam = openApiInfoBean.getRequestParam();

        for (OpenApiParam openApiParam : requestParam) {
            String paramCode = openApiParam.getParamCode();
            if (openApiParam.getIsFill() == 1) {
                if (!param.containsKey(paramCode)) {
                    return false;
                }
                if (StringUtil.isNullOrEmpty(param.get(paramCode))) {
                    return false;
                }
            }
        }
        return true;

    }


    /**
     * @description : 解析请求参数
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private JSONObject parseRequestParam(HttpServletRequest request) {
        String method = request.getMethod();
        JSONObject json = new JSONObject();
        //取的body
        String body = BaseUtil.getRequestBodyStr(request);
        if (!StringUtil.isNullOrEmpty(body)) {
            json = JSON.parseObject(body);
        }

        Map<String, String[]> parameterMap = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (!json.containsKey(entry.getKey())) {
                if (!StringUtil.isNullOrEmpty(request.getParameter(entry.getKey()))) {
                    json.put(entry.getKey(), request.getParameter(entry.getKey()));
                }
            }
        }
        return json;
    }


}
