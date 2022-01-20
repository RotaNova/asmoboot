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
    * 系统请求信息日志
    */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysRequestLog")
@Data
@TableName(value = "sys_request_log")
public class SysRequestLog implements Serializable {
    /**
     * 系统请求日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="系统请求日志ID")
    private Integer id;

    /**
     * 接口请求方式
     */
    @TableField(value = "request_method")
    @ApiModelProperty(value="接口请求方式")
    private Integer requestMethod;

    /**
     * 接口请求时间
     */
    @TableField(value = "request_time")
    @ApiModelProperty(value="接口请求时间")
    private Date requestTime;

    /**
     * 接口请求IP
     */
    @TableField(value = "request_ip")
    @ApiModelProperty(value="接口请求IP")
    private String requestIp;

    /**
     * 接口请求路径
     */
    @TableField(value = "request_url")
    @ApiModelProperty(value="接口请求路径")
    private String requestUrl;

    /**
     * 接口请求状态
     */
    @TableField(value = "request_stat")
    @ApiModelProperty(value="接口请求状态")
    private Integer requestStat;

    /**
     * 接口请求耗时
     */
    @TableField(value = "request_cost_time")
    @ApiModelProperty(value="接口请求耗时")
    private Integer requestCostTime;

    /**
     * 接口请求人
     */
    @TableField(value = "request_name")
    @ApiModelProperty(value="接口请求人")
    private String requestName;

    /**
     * 接口请求参数
     */
    @TableField(value = "request_param")
    @ApiModelProperty(value="接口请求参数")
    private String requestParam;

    private static final long serialVersionUID = 1L;
}