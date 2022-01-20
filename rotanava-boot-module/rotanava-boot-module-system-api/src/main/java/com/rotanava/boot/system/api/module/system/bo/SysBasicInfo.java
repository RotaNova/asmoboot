package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统的基础信息
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysBasicInfo")
@Data
@TableName(value = "sys_basic_info")
public class SysBasicInfo implements Serializable {
    /**
     * 系统信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "系统信息ID")
    private Integer id;

    /**
     * 系统信息变量名称
     */
    @TableField(value = "sysinfo_name")
    @ApiModelProperty(value = "系统信息变量名称")
    private String sysinfoName;

    /**
     * 系统信息变量编码
     */
    @TableField(value = "sysinfo_code")
    @ApiModelProperty(value = "系统信息变量编码")
    private String sysinfoCode;

    /**
     * 系统信息描述
     */
    @TableField(value = "sysinfo_description")
    @ApiModelProperty(value = "系统信息描述")
    private String sysinfoDescription;

    /**
     * 系统信息变量
     */
    @TableField(value = "sysinfo_value")
    @ApiModelProperty(value = "系统信息变量")
    private String sysinfoValue;

    /**
     * 系统信息创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "系统信息创建时间")
    private Date createTime;

    /**
     * 系统信息更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "系统信息更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}