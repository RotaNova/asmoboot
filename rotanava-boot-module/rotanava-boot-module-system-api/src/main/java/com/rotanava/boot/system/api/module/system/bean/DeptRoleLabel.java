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
public class DeptRoleLabel implements Serializable {

    /**
     * 部门角色ID
     */
    @ApiModelProperty(value = "部门角色ID")
    private Integer deptRoleId;

    /**
     * 系统角色名称
     */
    @ApiModelProperty(value = "系统角色名称")
    private String deptRoleName;

    @ApiModelProperty(value = "接口为根据部门id获取部门下的角色下时候有这个字段")
    private Boolean choose;
}