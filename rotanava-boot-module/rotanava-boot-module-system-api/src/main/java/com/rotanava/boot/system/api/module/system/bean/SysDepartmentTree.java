package com.rotanava.boot.system.api.module.system.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-25 18:15
 */
@Data
public class SysDepartmentTree implements Serializable {
    /**
     * 系统部门ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "系统部门ID")
    private Integer id;

    /**
     * 系统部门编码
     */
    @ApiModelProperty(value = "系统部门编码")
    private String deptCode;

    /**
     * 系统父级部门ID
     */
    @ApiModelProperty(value = "系统父级部门ID")
    private Integer parentDeptId;

    /**
     * 系统部门名称
     */
    @ApiModelProperty(value = "系统部门名称")
    private String deptName;

    /**
     * 系统部门排序号
     */
    @ApiModelProperty(value = "系统部门排序号")
    private Integer deptOrder;

    private Boolean isAdmin;


    private static final long serialVersionUID = 1L;
}