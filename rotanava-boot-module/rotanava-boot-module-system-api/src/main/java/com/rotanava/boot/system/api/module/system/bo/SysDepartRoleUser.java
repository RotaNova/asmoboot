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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysDepartRoleUser")
@Data
@TableName(value = "sys_depart_role_user")
public class SysDepartRoleUser implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 用户d
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value="用户d")
    private Integer sysUserId;

    /**
     * 部门角色id
     */
    @TableField(value = "depart_role_id")
    @ApiModelProperty(value="部门角色id")
    private Integer departRoleId;

    private static final long serialVersionUID = 1L;
}