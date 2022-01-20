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
public class DeptRoleAuthorizedUserDTO implements Serializable {

    /**
     * 部门角色ID
     */
    @NotNull(message = "部门角色ID不能为空")
    @ApiModelProperty(value = "部门角色ID")
    private Integer deptRoleId;

    @NotNull(message = "部门用户id")
    @ApiModelProperty(value = "部门用户id ")
    @NotEmpty
    private Collection<Integer> sysUserIdList;
}