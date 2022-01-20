package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-26 15:32
 */
@Data
public class GetListSysUserDeptRoleDTO extends BaseDTO {

    @ApiModelProperty("角色id")
    @NotNull
    private Integer deptRoleId;

}