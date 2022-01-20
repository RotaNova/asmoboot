package com.rotanava.boot.system.api.module.system.dto.rolemanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-06 16:42
 */
@Data
public class SysRoleAuthorizedUserDTO implements Serializable {

    /**
     * 系统角色ID
     */
    @NotNull(message = "系统角色ID不能为空")
    @ApiModelProperty(value = "系统角色ID")
    private Integer sysRoleId;

    @NotNull(message = "系统用户id")
    @ApiModelProperty(value = "系统用户id ")
    @NotEmpty
    private Collection<Integer> sysUserIdList;
}