package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysDepartRole")
@Data
@TableName(value = "sys_depart_role")
public class SysDepartRole implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 部门角色名称
     */
    @ApiModelProperty(value = "部门角色名称")
    private String departRoleName;

    /**
     * 系统部门ID
     */
    @ApiModelProperty(value = "系统部门ID")
    private Integer sysDepartmentId;

    /**
     * 部门角色编码
     */
    @ApiModelProperty(value = "部门角色编码")
    private String roleCode;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Integer updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    
    @ApiModelProperty(value = "指定部门（本部门及下属部门）")
    private String assignDeptIds;

    private static final long serialVersionUID = 1L;
}