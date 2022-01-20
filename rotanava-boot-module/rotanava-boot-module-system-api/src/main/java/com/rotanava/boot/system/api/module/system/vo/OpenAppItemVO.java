package com.rotanava.boot.system.api.module.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 17:36
 */
@Data
public class OpenAppItemVO implements Serializable {

    @ApiModelProperty(value = "应用名id")
    private Integer openAppId;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "应用描述")
    private String remark;

    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

    @ApiModelProperty(value = "app logo url")
    private String appImageUrl;

    @ApiModelProperty(value = "true 开启 false 关闭")
    private Boolean isSwitch;


    private static final long serialVersionUID = 1L;

}