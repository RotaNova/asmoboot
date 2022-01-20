package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.boot.system.api.module.system.group.Department;
import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-25 14:06
 */
@Data
public class ListDeptRoleItemDTO extends BaseDTO {

    @ApiModelProperty(value = "部门id")
    @NotNull(groups = {Department.class})
    private Integer deptId;


}