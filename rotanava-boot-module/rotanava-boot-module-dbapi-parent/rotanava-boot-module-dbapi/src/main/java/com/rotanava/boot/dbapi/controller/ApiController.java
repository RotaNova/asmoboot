package com.rotanava.boot.dbapi.controller;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.github.freakchick.orange.SqlMeta;
import com.rotanava.boot.dbapi.api.vo.ExecuteResult;
import com.rotanava.boot.dbapi.dao.DBApiMapper;

import com.rotanava.boot.dbapi.util.JdbcUtil;
import com.rotanava.boot.dbapi.util.SqlEngineUtil;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.OpenAppService;
import com.rotanava.boot.system.api.OpenDatasourceService;
import com.rotanava.boot.system.api.module.system.bean.OpenApiInfoBean;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.boot.system.api.module.system.bo.OpenDataSource;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.RequestMethod;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.util.*;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-11-11 17:34
 **/
@RestController
//@RequestMapping("/open")

@Log4j2

public class ApiController {


    @DubboReference
    private OpenAppApiService openAppApiService;

    @DubboReference
    private OpenAppService openAppService;


    @DubboReference
    private OpenDatasourceService openDatasourceService;

    @Autowired
    DBApiMapper dbApiMapper;


    @Autowired
    RedisUtil redisUtil;


    @RequestMapping("/openApi/**")
    @CrossOrigin(allowCredentials = "true")
    @AutoLog(value = "第三方应用接口")
    public Object openApi(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI().replaceFirst("/openApi", "");


        log.info("获取头部信息{}",JSONObject.toJSONString(request.getHeaderNames()));
        //获取token 及 method
        String token = request.getHeader("api-token");
        if (StringUtil.isNullOrEmpty(token)) {
            log.error("path : {} 获取不到token {}",path,token);
            throw new CommonException(AuthErrorCode.AUTH_ERROR_00);
        }
        OpenApp openApp = (OpenApp) redisUtil.get(token);
        if (openApp == null) {
            throw new CommonException(SysErrorCode.SYS_ERROR_10);
        }


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

                String sql = openApiInfoBean.getOpenApi().getSqlText();
                SqlMeta sqlMeta = null;
                try {
                    sqlMeta = SqlEngineUtil.getEngine().parse(sql, openApiInfoBean.getSqlPramMap(param));
                }catch (Exception e){
                    log.error("数据库sql配置有误",e);
                    throw new CommonException(DBErrorCode.DB_ERROR_16);
                }
                long parseEnd = System.currentTimeMillis();



                long executeBegin = System.currentTimeMillis();

                OpenDataSource dataSource = openDatasourceService.findDataSourceById(openApiInfoBean.getOpenApi().getDatasourceId());
                if (dataSource==null){
                    log.error("数据源为空，不执行");
                    return null;
                }
                Object o = null;
                try {
                   o = parseResult(dataSource, openApiInfoBean, param, sqlMeta);
                }catch (Exception e){
                    log.error("语法执行错误", e);
                    if (e instanceof CommonException){
                        CommonException exception = (CommonException) e;
                        throw exception;
                    }else {
                        throw new CommonException(DBErrorCode.DB_ERROR_15);
                    }
                }
                long executeEnd = System.currentTimeMillis();

                log.info(" ---------------------------------------------------------------------------------");
                log.info("---->【开放api请求 ==》 path = {} ,method = {} ,appName = {} 】", path, method, openApp.getAppName());
                log.info("---->【执行的sql语句==》 {}】", sqlMeta.getSql());
                log.info("---->【解析语句耗时==》 {}ms  执行语句耗时==》 {}ms】", (parseEnd - parseBegin), (executeEnd - executeBegin));
                log.info(" ---------------------------------------------------------------------------------");
                return o;
            } else {
                return BuildUtil.buildError(404, "找不到该接口");
            }
        }

        return null;

    }


    private Object parseResult(OpenDataSource openDataSource, OpenApiInfoBean openApiInfoBean, JSONObject param,SqlMeta sqlMeta){
        ExecuteResult executeResult = JdbcUtil.executeSql(openDataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
        if (ExecuteResult.UPDATE_TYPE == executeResult.getType()){
            return RetData.ok("操作成功，数量为"+executeResult.getUpdateCount());
        }else if (ExecuteResult.LIST_TYPE == executeResult.getType()){
            RetData retData = RetData.ok();
            List<JSONObject> list = (List<JSONObject>) executeResult.getData();
            if (list==null){
                return retData;
            }
            List<Map> resultMap = openApiInfoBean.getResultMap(list);
            //兼容amis返回数据格式
            if (resultMap.size()>1){
                JSONObject item = new JSONObject();
                item.put("items",resultMap);
                retData.setData(item);
            }else if (resultMap.size()==1){
                retData.setData(resultMap.get(0));
            }else {
                retData.setData(null);
            }
            return retData;
        }else {
            throw new CommonException(DBErrorCode.DB_ERROR_15);
        }

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
                    sql = sql.replace(paramMatcher.group(i), groupStr);
                } else {
                    //参数为空
                    sql = sql.replace(paramMatcher.group(i), "");
                }
            }
        }


        openApiInfoBean.getOpenApi().setSqlText(SQLUtils.formatMySql(sql));
        return openApiInfoBean;

    }


    private OpenApiInfoBean getOpenApiInfo(String path, Integer methodType, Integer appId) {
        OpenApi apiByRequest = openAppApiService.getApiByRequest(path, appId, methodType);

        if (apiByRequest != null) {
            OpenApiInfoBean openApiInfoBean = new OpenApiInfoBean();
            openApiInfoBean.setOpenApi(apiByRequest);
            List<OpenApiParam> openApiParams = openAppApiService.getParamByApiId( apiByRequest.getId());
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
        if (requestParam==null){
            return true;
        }
        for (OpenApiParam openApiParam : requestParam) {
            String paramCode = openApiParam.getCodeAlias();
            if (openApiParam.getIsFill() == 1) {
                String msg = null;
                if (!param.containsKey(paramCode)) {
                    msg = String.format("%s参数不存在，请填写后重试",paramCode);
                }
                else if (StringUtil.isNullOrEmpty(param.get(paramCode))) {
                    msg = String.format("%s参数为空，请填写后重试",paramCode);
                }
                if (msg!=null){
                    ErrorCode errorCode = new ErrorCode();
                    errorCode.setCode(404);
                    errorCode.setMsg(msg);
                    throw new CommonException(errorCode);
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
