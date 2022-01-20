package com.rotanava.boot.system.api.module.system.dto.departmentmanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-06 11:40
 */
@Data
public class UpdateDepartmentDTO implements Serializable {

    /**
     * 系统部门名称
     */
    @NotBlank
    @ApiModelProperty(value = "系统部门名称")
    private String deptName;

    @NotNull
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

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
     * 系统部门有效日期
     */
    @ApiModelProperty(value = "系统部门有效日期")
    private Long deptValidTime;

    /**
     * 系统部门排序号
     */
    @ApiModelProperty(value = "系统部门排序号")
    @NotNull
    private Integer deptOrder;


    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人")
    private List<Integer> sysUserIdList;

}