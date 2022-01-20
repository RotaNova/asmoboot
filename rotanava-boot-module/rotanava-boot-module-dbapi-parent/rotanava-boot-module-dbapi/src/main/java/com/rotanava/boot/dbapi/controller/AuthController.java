package com.rotanava.boot.dbapi.controller;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.OpenAppApiService;
import com.rotanava.boot.system.api.OpenAppService;
import com.rotanava.boot.system.api.module.system.bean.OpenApiInfoBean;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.RequestMethod;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.*;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/auth")
@Log4j2
public class AuthController {


    @DubboReference
    private OpenAppApiService openAppApiService;

    @DubboReference
    private OpenAppService openAppService;


    @Autowired
    RedisUtil redisUtil;

    private static final String openAppKey = "openAppKey:";


    @RequestMapping("/getAccessToken")
    @CrossOrigin(allowCredentials = "true")
    public RetData getAccessToken(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret) {
        QueryWrapper<OpenApp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_key", appKey);
        queryWrapper.eq("app_secret", appSecret);
        OpenApp openApp = openAppService.getAppByKeyAndSecret(appKey,appSecret);

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





}
