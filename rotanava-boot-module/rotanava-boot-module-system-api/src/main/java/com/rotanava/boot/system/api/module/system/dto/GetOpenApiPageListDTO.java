package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-22 15:33
 */
@Data
public class GetOpenApiPageListDTO extends BaseDTO {

    @ApiModelProperty(value = "应用名id")
    private Integer openAppId;

}