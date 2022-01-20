package com.rotanava.boot.system.api.module.system.dto.rolemanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 11:38
 */
@Data
public class AddDeptRoleDTO extends BaseAddRole {

    @NotNull(message = "角色所属部门Id不能为空")
    @ApiModelProperty(value = "角色所属部门Id", required = true)
    private Integer deptId;

    @ApiModelProperty(value = "选择的部门id")
    private List<Integer> chooseDepartmentIdList;
}