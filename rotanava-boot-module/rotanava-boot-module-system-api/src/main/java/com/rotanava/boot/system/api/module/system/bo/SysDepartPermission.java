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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysDepartPermission")
@Data
@TableName(value = "sys_depart_permission")
public class SysDepartPermission implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 部门id
     */
    @ApiModelProperty(value="部门id")
    private Integer departId;

    /**
     * 权限id
     */
    @ApiModelProperty(value="权限id")
    private Integer pagePermissionId;

    private static final long serialVersionUID = 1L;
}