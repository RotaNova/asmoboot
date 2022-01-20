package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
/**
    * 系统服务配置信息
    */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysServiceSetting")
@Data
@TableName(value = "sys_service_setting")
public class SysServiceSetting implements Serializable {
    /**
     * 系统服务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="系统服务ID")
    private Integer id;

    /**
     * 系统服务编码
     */
    @TableField(value = "sys_service_code")
    @ApiModelProperty(value="系统服务编码")
    private String sysServiceCode;

    /**
     * 系统服务名称
     */
    @TableField(value = "sys_service_name")
    @ApiModelProperty(value="系统服务名称")
    private String sysServiceName;

    /**
     * 系统服务类型
     */
    @TableField(value = "sys_service_type")
    @ApiModelProperty(value="系统服务类型")
    private Integer sysServiceType;

    /**
     * 系统服务变量
     */
    @TableField(value = "sys_service_value")
    @ApiModelProperty(value="系统服务变量")
    private String sysServiceValue;

    /**
     * 系统服务变量默认值
     */
    @TableField(value = "sys_service_default_value")
    @ApiModelProperty(value="系统服务变量默认值")
    private String sysServiceDefaultValue;

    /**
     * 系统服务描述
     */
    @TableField(value = "sys_service_description")
    @ApiModelProperty(value="系统服务描述")
    private String sysServiceDescription;

    /**
     * 系统服务信息更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="系统服务信息更新人")
    private Integer updateBy;

    /**
     * 系统服务信息更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="系统服务信息更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;


    public Integer getIntegerValue(){
        return Integer.parseInt(sysServiceValue);
    }
}