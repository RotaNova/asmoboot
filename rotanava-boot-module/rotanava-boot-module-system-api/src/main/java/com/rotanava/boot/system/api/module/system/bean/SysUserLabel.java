package com.rotanava.boot.system.api.module.system.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-06 14:43
 */
@Data
public class SysUserLabel implements Serializable {

    @ApiModelProperty(value="负责人id")
    private Integer sysUserId;

    @ApiModelProperty(value="负责人名称")
    private String sysUserName;

}