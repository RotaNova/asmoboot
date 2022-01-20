package com.rotanava.boot.system.api.module.system.dto.rolemanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 编辑角色
 * @author: jintengzhou
 * @date: 2021-03-03 18:13
 */
@Data
public class BaseUpdateRoleDTO implements Serializable {

    /**
     * 系统角色名称
     */
    @ApiModelProperty(value = "系统角色名称")
    private String roleName;

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

}