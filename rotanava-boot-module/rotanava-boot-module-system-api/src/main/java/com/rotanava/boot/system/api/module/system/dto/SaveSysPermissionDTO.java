package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-10 14:51
 */
@Data
public class SaveSysPermissionDTO implements Serializable {

    /**
     * 关联id
     */
    @NotNull
    private Integer sysRoleId;

    @NotNull
    @ApiModelProperty(value = "页面模块类型id")
    private Integer sysPageModuleTypeId;

    @ApiModelProperty(value = "父页面id")
    private Integer sysParentPageId;

    /**
     * 页面id
     */
    private Set<Integer> sysPageIdList;

}