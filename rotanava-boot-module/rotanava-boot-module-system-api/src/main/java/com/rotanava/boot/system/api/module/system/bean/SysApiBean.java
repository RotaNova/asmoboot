package com.rotanava.boot.system.api.module.system.bean;

import lombok.Data;

import java.io.Serializable;


/**
 * 接口资源VO
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class SysApiBean implements Serializable {

    /**
     * 系统接口id
     */
    private Integer sysApiId;

    /**
     * 系统页面id
     */
    private Integer sysPageId;

    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口路径
     */
    private String apiUrl;

    /**
     * 接口请求方式
     */
    private Integer apiMethod;

    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    private Integer apiAuthType;

    /**
     * 接口能力类型
     */
    private Integer apiAbilityType;

}