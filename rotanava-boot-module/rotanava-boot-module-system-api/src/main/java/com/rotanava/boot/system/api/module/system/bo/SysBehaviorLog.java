package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rotanava.framework.common.aspect.annotation.Dict;
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
    * 记录系统操作行为的日志表
    */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysBehaviorLog")
@Data
@TableName(value = "sys_behavior_log")
public class SysBehaviorLog implements Serializable {
    /**
     * 系统日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="系统日志ID")
    private Integer id;

    /**
     * 系统行为类型:0-基本信息;1-业务操作
     */
    @TableField(value = "syslog_behavior_type")
    @ApiModelProperty(value="系统行为类型:0-基本信息;1-业务操作")
    private Integer syslogBehaviorType;

    /**
     * 系统日志类型:0-登录日志;1-操作日志;2-定时日志
     */
    @TableField(value = "syslog_type")
    @ApiModelProperty(value="系统日志类型:0-登录日志;1-操作日志;2-定时日志")
    @Dict(dicCode = "syslog_type")
    private Integer syslogType;

    /**
     * 系统日志记录时间
     */
    @TableField(value = "syslog_time")
    @ApiModelProperty(value="系统日志记录时间")
    private Date syslogTime;

    /**
     * 系统日志记录内容
     */
    @TableField(value = "syslog_content")
    @ApiModelProperty(value="系统日志记录内容")
    private String syslogContent;

    /**
     * 操作人名称
     */
    @TableField(value = "operate_by")
    @ApiModelProperty(value="操作人名称")
    private String operateBy;

    /**
     * 操作人IP地址
     */
    @TableField(value = "operate_ip")
    @ApiModelProperty(value="操作人IP地址")
    private String operateIp;

    /**
     * 操作类型:0-查询:1-修改;2-增加;3-删除
     */
    @TableField(value = "operate_type")
    @ApiModelProperty(value="操作类型:0-查询:1-修改;2-增加;3-删除")
    @Dict(dicCode = "log_operate_type")
    private Integer operateType;

    /**
     * 操作耗时(ms)
     */
    @TableField(value = "operate_cost_time")
    @ApiModelProperty(value="操作耗时(ms)")
    private String operateCostTime;

    /**
     * 操作时间
     */
    @TableField(value = "operate_create_time")
    @ApiModelProperty(value="操作时间")
    private Date operateCreateTime;

    /**
     * 操作请求java方法
     */
    @TableField(value = "operate_method")
    @ApiModelProperty(value="操作请求java方法")
    private String operateMethod;

    /**
     * 操作请求路径
     */
    @TableField(value = "operate_url")
    @ApiModelProperty(value="操作请求路径")
    private String operateUrl;

    /**
     * 操作请求参数
     */
    @TableField(value = "operate_param")
    @ApiModelProperty(value="操作请求参数")
    private String operateParam;

    /**
     * 操作请求类型
     */
    @TableField(value = "operate_request_type")
    @ApiModelProperty(value="操作请求类型")
    @Dict(dicCode = "api_method")
    private Integer operateRequestType;

    private static final long serialVersionUID = 1L;
}