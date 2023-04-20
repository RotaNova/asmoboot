package com.rotanava.boot.system.module.system.impl;

import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.rotanava.boot.platform.core.deploy.DeployUtil;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.module.system.dto.GatewayInfoDTO;
import com.rotanava.boot.system.api.module.system.vo.ClusterResultsVO;
import com.rotanava.boot.system.api.module.system.vo.ClusterTokenVO;
import com.rotanava.boot.system.api.module.system.vo.GatewayInfoVO;
import com.rotanava.framework.common.constant.CacheConstant;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.global.GlobalClass;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import com.rotanava.framework.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-25 15:45
 **/
@DubboService
@Log4j2
public class SysPlatformDeployServiceImpl implements SysPlatformDeployService {

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private GlobalClass globalClass;

    @Autowired
    DeployUtil deployUtil;

    @Value("${system.monitoring.url}")
    private String monitoringUrl;

    private final static String GATEWAY_CONFIG_GET = "/v1/networkConfig/listGatewayInfo";
    private final static String GATEWAY_CONFIG_UPDATE = "/v1/networkConfig/updateGatewayConfig";
    private final static String SYSTEM_REBOOT = "/v1/system/reboot";
    private final static String SYSTEM_UPDATESYSTEMTIME = "/v1/system/updateSystemTime";

    private final static String SYSTEM_UPDATESYSTEMZONE = "/v1/system/updateSystemTimeZone";

    private final static String SYSTEM_SYNCNTPDATE = "/v1/system/syncNtpDate";

    private final static String SYSTEM_GET_NOWTIME = "/v1/system/getNowTime";

    /**
     * @description : 系统重启
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @Override
    public void systemReboot() {
        HTTP http = getHttpClient(SYSTEM_REBOOT);
        http.sync("").post().getBody().toString();
    }





    /**
     * @description : 获取http操作对象
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @Override
    public HTTP getHttpClient(String url) {
        //官方文档 https://okhttps.ejlchina.com
//        HTTP http = HTTP.builder().baseUrl(globalClass.getMappingValue("deployUrl")).build();
        HTTP http = HTTP.builder().baseUrl(monitoringUrl).build();

        http.sync("").addHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyQWNjb3VudE5hbWUiOiJhZG1pbiJ9.kgl-PiZqBriNlvYRM2MFrvWmplRIK7NGbuSM1pcn72g");
        return http;
    }


    @Override
    public List<GatewayInfoVO> getGatewayInfo() {
        try {
            HTTP http = getHttpClient(GATEWAY_CONFIG_GET);

            return http.sync(GATEWAY_CONFIG_GET).get().getBody().toList(GatewayInfoVO.class);
        }catch (Exception e){
            log.error("获取网络配置异常",e);
        }
        return null;
    }


    @Override
    public void updateGatewayConfig(GatewayInfoDTO gatewayInfoDTO) {

        Map<String, Object> map = BeanUtil.beanToMap(gatewayInfoDTO, Boolean.FALSE, Boolean.TRUE);

        HTTP http = getHttpClient(GATEWAY_CONFIG_UPDATE);
        http.sync(GATEWAY_CONFIG_UPDATE).addJsonParam(map).put();
    }


    /**
     * @description : 修改系统时区
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void updateSystemTimeZone(String zone){
        HTTP http = getHttpClient(SYSTEM_UPDATESYSTEMZONE);
        http.async(SYSTEM_UPDATESYSTEMZONE).addUrlParam("zone",zone).get();
        return;
    }

    /**
     * @description :ntp校时
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void syncNtpDate(String address){
        HTTP http = getHttpClient(SYSTEM_UPDATESYSTEMZONE);
        http.async(SYSTEM_UPDATESYSTEMZONE).addUrlParam("address",address).get();
        return;
    }



    /**
     * @description : 修改系统时间
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void updateSystemTime(String time) {
        HTTP http = getHttpClient(SYSTEM_UPDATESYSTEMTIME);
        http.sync(SYSTEM_UPDATESYSTEMTIME).addUrlParam("time",time).get();
    }


    public String getSystemTime(){
        HTTP http = getHttpClient(SYSTEM_GET_NOWTIME);
        JSONObject jsonObject = http.sync(SYSTEM_GET_NOWTIME).get().getBody().toJsonObject();
        return jsonObject.getString("time");
    }


    @Override
    public List<ClusterResultsVO> getCluster(String url) {
       return deployUtil.getCluster(url);
    }


}
