package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 15:38
 */
@Data
public class UpdateApplicationDTO implements Serializable {

    @ApiModelProperty(value = "应用名id")
    @NotNull
    private Integer openAppId;

    @ApiModelProperty(value = "应用名")
    @NotBlank
    private String appName;

    @ApiModelProperty(value = "应用描述")
    private String remark;

    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

}