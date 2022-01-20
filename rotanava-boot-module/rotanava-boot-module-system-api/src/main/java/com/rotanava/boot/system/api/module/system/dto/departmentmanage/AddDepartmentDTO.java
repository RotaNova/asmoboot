package com.rotanava.boot.system.api.module.system.dto.departmentmanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-06 11:40
 */
@Data
public class AddDepartmentDTO implements Serializable {

    /**
     * 系统部门类型:0-一级部门;1-子部门
     */
    @NotNull
    @ApiModelProperty(value = "系统部门类型:0-一级部门;1-子部门")
    private Integer deptType;

    /**
     * 系统部门名称
     */
    @NotBlank
    @ApiModelProperty(value = "系统部门名称")
    private String deptName;

    /**
     * 系统部门英文名称
     */
    @ApiModelProperty(value = "系统部门英文名称")
    private String deptNameEn;

    /**
     * 系统部门编码
     */
    @ApiModelProperty(value = "系统部门编码")
    @NotNull
    private String deptCode;

    /**
     * 系统父级部门ID
     */
    @ApiModelProperty(value = "系统父级部门ID")
    private Integer parentDeptId;

    /**
     * 系统部门有效日期
     */
    @ApiModelProperty(value = "系统部门有效日期")
    private Long deptValidTime;

    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人")
    private List<Integer> sysUserIdList;

    /**
     * 系统部门描述
     */
    @ApiModelProperty(value = "系统部门描述")
    private String deptDescription;

    /**
     * 系统部门负责人联系方式
     */
    @ApiModelProperty(value = "系统部门负责人联系方式")
    private String deptManagerPhone;

    /**
     * 系统部门地址信息
     */
    @ApiModelProperty(value = "系统部门地址信息")
    private String deptAddress;

    /**
     * 系统部门传真号码
     */
    @ApiModelProperty(value = "系统部门传真号码")
    private String deptFax;

    /**
     * 系统部门排序号
     */
    @ApiModelProperty(value = "系统部门排序号")
    @NotNull
    private Integer deptOrder;


//    /**
//     * 系统部门负责人信息
//     */
//    @ApiModelProperty(value = "系统部门负责人信息")
//    private String deptManager;


}