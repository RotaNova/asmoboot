package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysDepartment")
@Data
@TableName(value = "sys_department")
public class SysDepartment implements Serializable {
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
     * 系统部门英文名称
     */
    @ApiModelProperty(value = "系统部门英文名称")
    private String deptNameEn;

    /**
     * 系统部门排序号
     */
    @ApiModelProperty(value = "系统部门排序号")
    private Integer deptOrder;

    /**
     * 系统部门描述
     */
    @ApiModelProperty(value = "系统部门描述")
    private String deptDescription;

    /**
     * 系统部门类型:0-一级部门;1-子部门
     */
    @ApiModelProperty(value = "系统部门类型:0-一级部门;1-子部门")
    private Integer deptType;

    /**
     * 系统部门负责人信息 没用这字段
     */
    @ApiModelProperty(value = "系统部门负责人信息")
    private String deptManager;

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
     * 系统部门负责人联系方式
     */
    @ApiModelProperty(value = "系统部门负责人联系方式")
    private String deptManagerPhone;

    /**
     * 系统部门状态:0-未激活;1-正常:2-冻结:3-过期
     */
    @ApiModelProperty(value = "系统部门状态:0-未激活;1-正常:2-冻结:3-过期")
    private Integer deptStatus;

    /**
     * 系统部门有效日期
     */
    @ApiModelProperty(value = "系统部门有效日期")
    private Date deptValidTime;

    /**
     * 系统部门创建人ID
     */
    @ApiModelProperty(value = "系统部门创建人ID")
    private Integer createBy;

    /**
     * 系统部门创建时间
     */
    @ApiModelProperty(value = "系统部门创建时间")
    private Date createTime;

    /**
     * 系统部门信息更新人ID
     */
    @ApiModelProperty(value = "系统部门信息更新人ID")
    private Integer updateBy;

    /**
     * 系统部门信息更新时间
     */
    @ApiModelProperty(value = "系统部门信息更新时间")
    private Date updateTime;

    /**
     * 系统部门删除状态  0删除 1未删除
     */
    @ApiModelProperty(value = "系统部门删除状态  0删除 1未删除")
    private Integer deptDeleteStatus;

    private static final long serialVersionUID = 1L;
}