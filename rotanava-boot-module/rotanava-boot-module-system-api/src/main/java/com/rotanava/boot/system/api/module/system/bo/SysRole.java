package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统角色信息，直接权限分配主体
 */
@Data
public class SysRole {
    
    /**
     * 系统角色ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    private Integer id;

    /**
     * 系统部门ID
     */
    private Integer sysDepartmentId;

    /**
     * 系统角色名称
     */
    private String roleName;

    /**
     * 系统角色编码
     */
    private String roleCode;

    /**
     * 系统角色描述
     */
    private String roleDescription;


    /**
     * 系统角色创建人ID
     */
    private Integer createBy;

    /**
     * 系统角色创建时间
     */
    private Date createTime;

    /**
     * 系统角色信息更新人ID
     */
    private Integer updateBy;

    /**
     * 系统角色信息更新时间
     */
    private Date updateTime;

    /**
     * 角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门
     */
    private Integer roleDataScope;

    /**
     * 角色登录IP过滤方式:0-禁止;1-允许
     */
    private Integer ipFilterType;

    /**
     * 系统角色登录过滤IP名单
     */
    private String ipList;

    @ApiModelProperty(value = "指定部门（本部门及下属部门）")
    private String assignDeptIds;

}