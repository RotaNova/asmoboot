package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-05-19 15:42
 */
@Data
public class GetListDeptUserAddDTO extends BaseDTO {

    @ApiModelProperty("部门id")
    @NotNull
    private Integer deptId;

}