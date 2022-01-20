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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysRolePermission")
@Data
@TableName(value = "sys_role_permission")
public class SysRolePermission implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 系统角色ID
     */
    @TableField(value = "sys_role_id")
    @ApiModelProperty(value="系统角色ID")
    private Integer sysRoleId;

    /**
     * 页面资源ID
     */
    @TableField(value = "page_id")
    @ApiModelProperty(value="页面资源ID")
    private Integer pageId;

    private static final long serialVersionUID = 1L;
}