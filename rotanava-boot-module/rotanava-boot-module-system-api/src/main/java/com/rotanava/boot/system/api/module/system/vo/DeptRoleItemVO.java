package com.rotanava.boot.system.api.module.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 角色列表
 * @author: jintengzhou
 * @date: 2021-03-03 17:55
 */
@Data
public class DeptRoleItemVO implements Serializable {
    /**
     * 部门角色ID
     */
    @ApiModelProperty(value = "部门角色ID")
    private Integer deptRoleId;

    @ApiModelProperty(value = "部门角色名称")
    private String deptRoleName;

    /**
     * 部门角色编码
     */
    @ApiModelProperty(value = "部门角色编码")
    private String roleCode;

    /**
     * 部门角色描述
     */
    @ApiModelProperty(value = "部门角色描述")
    private String roleDescription;

    @ApiModelProperty(value = "角色所属部门名称")
    private String roleDeptName;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}