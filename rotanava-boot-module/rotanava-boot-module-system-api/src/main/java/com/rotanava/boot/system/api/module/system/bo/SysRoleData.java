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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysRoleData")
@Data
@TableName(value = "sys_role_data")
public class SysRoleData implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 数据集ID
     */
    @TableField(value = "data_set_id")
    @ApiModelProperty(value="数据集ID")
    private Integer dataSetId;

    /**
     * 系统角色ID
     */
    @TableField(value = "sys_role_id")
    @ApiModelProperty(value="系统角色ID")
    private Integer sysRoleId;

    private static final long serialVersionUID = 1L;
}