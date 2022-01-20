package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 网关视图对象
 *
 * @author WeiQiangMiao
 * @date 2021-03-25
 */
@Data
public class GatewayInfoDTO {

    /**
     * 网关设备名称
     */
    @NotBlank(message = "设备名称不能为空")
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

    @NotNull(message = "是否为dhcp不能为空")
    Boolean isDhcp;
    
}
