package com.rotanava.boot.system.api.module.system.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 10:25
 */
@Data
public class SysRoleLabel implements Serializable {

    /**
     * 系统角色ID
     */
    @ApiModelProperty(value="系统角色ID")
    private Integer sysRoleId;

    /**
     * 系统角色名称
     */
    @ApiModelProperty(value="系统角色名称")
    private String sysRoleName;
}