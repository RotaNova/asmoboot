package com.rotanava.boot.system.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HTTP;
import com.rotanava.boot.system.api.module.system.dto.GatewayInfoDTO;
import com.rotanava.boot.system.api.module.system.vo.ClusterResultsVO;
import com.rotanava.boot.system.api.module.system.vo.GatewayInfoVO;

import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-25 15:44
 **/

public interface SysPlatformDeployService {

    /**
     * @description : 系统重启
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    void systemReboot();

    HTTP getHttpClient(String url);


    /**
     * 获取网关信息集合
     *
     * @return {@link GatewayInfoVO }
     * @author WeiQiangMiao
     * @date 2021-03-25
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<GatewayInfoVO> getGatewayInfo();

    /**
     * 修改网关信息
     *
     * @param gatewayInfoDTO 网关信息签
     * @author WeiQiangMiao
     * @date 2021-03-25
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateGatewayConfig(GatewayInfoDTO gatewayInfoDTO);


    void updateSystemTimeZone(String zone);


    void syncNtpDate(String address);


    void updateSystemTime(String time);


    String getSystemTime();


    List<ClusterResultsVO> getCluster(String url);
}
