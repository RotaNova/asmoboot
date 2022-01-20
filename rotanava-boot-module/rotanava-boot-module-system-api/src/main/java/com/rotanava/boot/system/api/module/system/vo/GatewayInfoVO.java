package com.rotanava.boot.system.api.module.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 网关视图对象
 * @author WeiQiangMiao
 * @date 2020/06/10/23:09
 */
@Data
@ApiModel("网关 信息视图对象类")
public class GatewayInfoVO {

    /**
     * 网关设备名称
     */
    String gatewayName;

    /**
     * 默认网关
     */
    String defaultGateway;

    /**
     * ip地址
     */
    String ipAddress;

    /**
     * 子网掩码
     */
    String subnetMask;

    /**
     * 首选dns
     */
    String preferredDns;

    /**
     * 备用dns
     */
    String standbyDns;

    /**
     * 网卡物理地址
     */
    String hardware;

    Boolean isDhcp;
    
}
