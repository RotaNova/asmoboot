package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-17 15:51
 */
@Data
public class RestPasswordDTO implements Serializable {

    @ApiModelProperty("用户id")
    @NotNull(message = "sysUserId为空")
    private Integer sysUserId;

    @ApiModelProperty("密码")
    @NotBlank(message = "缺少密码")
    private String password;


}