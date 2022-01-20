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
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */

/**
 * 系统网络参数配置表
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-NetworkSetting")
@Data
@TableName(value = "network_setting")
public class NetworkSetting implements Serializable {
    /**
     * 系统网络配置ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "系统网络配置ID")
    private Integer id;

    /**
     * 系统网络服务名称
     */
    @TableField(value = "ntk_service_name")
    @ApiModelProperty(value = "系统网络服务名称")
    private String ntkServiceName;

    /**
     * 系统网络服务端口
     */
    @TableField(value = "ntk_service_port")
    @ApiModelProperty(value = "系统网络服务端口")
    private Integer ntkServicePort;

    /**
     * 系统网络默认服务端口
     */
    @TableField(value = "ntk_service_default_port")
    @ApiModelProperty(value = "系统网络默认服务端口")
    private Integer ntkServiceDefaultPort;

    /**
     * 网络服务更新人ID
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "网络服务更新人ID")
    private Integer updateBy;

    /**
     * 网络服务信息更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "网络服务信息更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}