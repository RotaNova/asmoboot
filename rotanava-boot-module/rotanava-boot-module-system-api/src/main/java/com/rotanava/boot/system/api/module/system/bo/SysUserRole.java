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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysUserRole")
@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 系统用户标识ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value="系统用户标识ID")
    private Integer sysUserId;

    /**
     * 系统角色ID
     */
    @TableField(value = "sys_role_id")
    @ApiModelProperty(value="系统角色ID")
    private Integer sysRoleId;

    private static final long serialVersionUID = 1L;
}