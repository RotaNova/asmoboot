package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 14:02
 */
@Data
public class ApiBindAppDTO implements Serializable {

    @NotEmpty
    @ApiModelProperty(value = "传openApiId 数组")
    private Collection<Integer> openApiIdList;

    @NotNull
    @ApiModelProperty(value = "openAppId 权限接口id")
    private Integer openAppId;

}