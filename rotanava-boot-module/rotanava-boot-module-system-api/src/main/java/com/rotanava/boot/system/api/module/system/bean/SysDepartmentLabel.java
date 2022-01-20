package com.rotanava.boot.system.api.module.system.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 10:25
 */
@Data
public class SysDepartmentLabel implements Serializable {

    /**
     * 系统部门ID
     */
    @ApiModelProperty(value="系统部门ID")
    private Integer sysDepartmentId;

    /**
     * 系统部门名称
     */
    @ApiModelProperty(value="系统部门名称")
    private String sysDepartmentName;
}