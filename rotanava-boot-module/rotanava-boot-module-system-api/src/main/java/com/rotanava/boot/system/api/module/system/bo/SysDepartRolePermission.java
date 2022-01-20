package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysDepartRolePermission")
@Data
@TableName(value = "sys_depart_role_permission")
public class SysDepartRolePermission implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    @ApiModelProperty(value="角色id")
    private Integer departRoleId;

    /**
     * 页面权限id
     */
    @ApiModelProperty(value="页面权限id")
    private Integer pagePermissionId;

    private static final long serialVersionUID = 1L;
}