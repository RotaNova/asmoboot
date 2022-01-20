package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 15:46
 */
@Data
public class SysRoleInfoVO implements Serializable {

    /**
     * 系统角色ID
     */
    @ApiModelProperty(value = "系统角色ID")
    private Integer sysRoleId;

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


    /**
     * 角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门
     */
    @ApiModelProperty(value = "角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门")
    private Integer roleDataScope;

    @ApiModelProperty(value = "选择的部门id 当数据范围为4时候有")
    private List<SysDepartmentLabel> chooseDepartmentList;
}