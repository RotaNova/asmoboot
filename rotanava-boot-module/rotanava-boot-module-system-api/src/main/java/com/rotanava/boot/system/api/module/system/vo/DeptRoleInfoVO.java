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
public class DeptRoleInfoVO implements Serializable {

    /**
     * 部门角色ID
     */
    @ApiModelProperty(value = "部门角色ID")
    private Integer deptRoleId;

    /**
     * 部门角色名称
     */
    @ApiModelProperty(value = "部门角色名称")
    private String roleName;

    /**
     * 部门角色编码
     */
    @ApiModelProperty(value = "部门角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色所属部门id")
    private Integer deptId;

    @ApiModelProperty(value = "角色所属部门名称")
    private String deptName;

    /**
     * 部门角色描述
     */
    @ApiModelProperty(value = "部门角色描述")
    private String roleDescription;

    @ApiModelProperty(value = "角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门")
    private Integer roleDataScope;

    @ApiModelProperty(value = "选择的部门id 当数据范围为4时候有")
    private List<SysDepartmentLabel> chooseDepartmentList;
}