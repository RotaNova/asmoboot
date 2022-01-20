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
public class SysRoleItemVO implements Serializable {
    /**
     * 系统角色ID
     */
    @ApiModelProperty(value = "系统角色ID")
    private Integer sysRoleId;

    @ApiModelProperty(value = "系统角色名称")
    private String sysRoleName;

    /**
     * 系统角色编码
     */
    @ApiModelProperty(value = "系统角色编码")
    private String roleCode;

    /**
     * 系统角色描述
     */
    @ApiModelProperty(value = "系统角色描述")
    private String roleDescription;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}