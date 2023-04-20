package com.rotanava.boot.system.api.module.system.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-07-01 15:51
 **/
@Data
public class SystemAuthBean implements Serializable {

    /**
     * 过期时间
     */
    private Date authEndDate;

    /**
     * 授权天数
     */
    private Integer authPeriod;

    /**
     * ai额度
     */
    private Integer aiQuota;

    /**
     * ai 服务key
     */
    private String aiSecretKey;


    /**
     * 设备指纹信息
     */
    private String deviceInfo;

    /**
     * 私钥
     */
    private String privateKey;



    private boolean expiration;


}
