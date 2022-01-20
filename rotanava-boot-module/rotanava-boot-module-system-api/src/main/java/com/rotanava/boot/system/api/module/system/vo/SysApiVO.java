package com.rotanava.boot.system.api.module.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 15:09
 */
@Data
public class SysApiVO implements Serializable {

    /**
     * 接口ID
     */
    @ApiModelProperty(value = "接口ID")
    private Integer sysApiPermissionId;

    /**
     * 页面资源ID
     */
    @ApiModelProperty(value = "页面资源ID")
    private Integer pageId;

    /**
     * 接口名称
     */
    @ApiModelProperty(value = "接口名称")
    private String apiName;

    /**
     * 接口能力类型
     */
    @ApiModelProperty(value = "接口能力类型 0增 1删 2改 3查")
    private Integer apiAbilityType;


    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    @ApiModelProperty(value = "接口鉴权方式:0-不鉴权;1-Token鉴权")
    private Integer apiAuthType;
}