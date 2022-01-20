package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysUserDepartment")
@Data
@TableName(value = "sys_user_department")
public class SysUserDepartment implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 系统用户标识ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value="系统用户标识ID")
    private Integer sysUserId;

    /**
     * 系统部门ID
     */
    @TableField(value = "sys_department_id")
    @ApiModelProperty(value="系统部门ID")
    private Integer sysDepartmentId;

    /**
     * 系统用户是否为部门管理员:0-否;1-是
     */
    @TableField(value = "dept_manage")
    @ApiModelProperty(value="系统用户是否为部门管理员:0-否;1-是")
    private Integer deptManage;

    private static final long serialVersionUID = 1L;
}