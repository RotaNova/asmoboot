package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysReport")
@Data
@TableName(value = "sys_report")
public class SysReport implements Serializable {
    /**
     * 报告ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value="报告ID")
    private Integer id;

    /**
     * 报告编码
     */
    @TableField(value = "report_code")
    @ApiModelProperty(value="报告编码")
    private String reportCode;

    /**
     * 报告名称
     */
    @TableField(value = "report_tile")
    @ApiModelProperty(value="报告名称")
    private String reportTile;

    /**
     * 报告人
     */
    @TableField(value = "reporter")
    @ApiModelProperty(value="报告人")
    private String reporter;

    /**
     * 报告内容
     */
    @TableField(value = "report_body")
    @ApiModelProperty(value="报告内容")
    private String reportBody;

    /**
     * 报告人联系方式
     */
    @TableField(value = "report_phone")
    @ApiModelProperty(value="报告人联系方式")
    private String reportPhone;

    /**
     * 报告类型:0-问题反馈;1-改进意见
     */
    @TableField(value = "report_type")
    @ApiModelProperty(value="报告类型:0-问题反馈;1-改进意见")
    private Integer reportType;

    /**
     * 报告日期
     */
    @TableField(value = "report_time")
    @ApiModelProperty(value="报告日期")
    private Date reportTime;

    /**
     * 报告地址
     */
    @TableField(value = "report_address")
    @ApiModelProperty(value="报告地址")
    private String reportAddress;

    /**
     * 报告状态:0-待受理;1-待确认;2-已处理;3-驳回;4-撤销
     */
    @TableField(value = "report_stat")
    @ApiModelProperty(value="报告状态:0-待受理;1-待确认;2-已处理;3-驳回;4-撤销")
    private Integer reportStat;

    /**
     * 报告创建人ID
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="报告创建人ID")
    private Integer createBy;

    /**
     * 报告创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="报告创建时间")
    private Date createTime;

    /**
     * 报告信息更新人ID
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="报告信息更新人ID")
    private Integer updateBy;

    /**
     * 报告信息更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="报告信息更新时间")
    private Date updateTime;

    /**
     * 回复内容
     */
    @TableField(value = "reply_body")
    @ApiModelProperty(value="回复内容")
    private String replyBody;

    private static final long serialVersionUID = 1L;
}