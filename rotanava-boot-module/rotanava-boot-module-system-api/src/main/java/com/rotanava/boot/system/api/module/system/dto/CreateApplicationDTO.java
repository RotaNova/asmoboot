package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 15:06
 */
@Data
public class CreateApplicationDTO implements Serializable {

    @ApiModelProperty(value = "应用名")
    @NotBlank
    private String appName;

    @ApiModelProperty(value = "应用描述")
    private String remark;

}