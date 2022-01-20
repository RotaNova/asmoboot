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
 * 系统通告发送清单
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysAnnouncement")
@Data
@TableName(value = "sys_announcement")
public class SysAnnouncement implements Serializable {
    /**
     * 系统通告编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "系统通告编号")
    private Integer id;

    /**
     * 系统通告标题
     */
    @TableField(value = "ann_title")
    @ApiModelProperty(value = "系统通告标题")
    private String annTitle;

    /**
     * 系统通告内容
     */
    @TableField(value = "ann_content")
    @ApiModelProperty(value = "系统通告内容")
    private String annContent;

    /**
     * 系统通告起始时间
     */
    @TableField(value = "ann_start_time")
    @ApiModelProperty(value = "系统通告起始时间")
    private Date annStartTime;

    /**
     * 系统通告结束时间
     */
    @TableField(value = "ann_end_time")
    @ApiModelProperty(value = "系统通告结束时间")
    private Date annEndTime;

    /**
     * 系统通告发布人ID
     */
    @TableField(value = "ann_sender_id")
    @ApiModelProperty(value = "系统通告发布人ID")
    private Integer annSenderId;

    /**
     * 系统通告优先级
     */
    @TableField(value = "ann_priority")
    @ApiModelProperty(value = "系统通告优先级")
    private Integer annPriority;

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @TableField(value = "ann_category")
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    private Integer annCategory;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @TableField(value = "ann_target")
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    private Integer annTarget;

    /**
     * 系统通告发布时间
     */
    @TableField(value = "ann_send_time")
    @ApiModelProperty(value = "系统通告发布时间")
    private Date annSendTime;

    /**
     * 系统通告发布状态:0-未发布;1-已发布;2-已撤销
     */
    @TableField(value = "ann_send_status")
    @ApiModelProperty(value = "系统通告发布状态:0-未发布;1-已发布;2-已撤销")
    private Integer annSendStatus;

    /**
     * 系统通告撤销时间
     */
    @TableField(value = "ann_cancel_time")
    @ApiModelProperty(value = "系统通告撤销时间")
    private Date annCancelTime;

    /**
     * 系统通告撤销人id
     */
    @ApiModelProperty(value = "系统通告撤销人id")
    private Integer annCancelId;

    /**
     * 系统通告删除状态:0-已删除:1-未删除
     */
    @TableField(value = "ann_del_status")
    @ApiModelProperty(value = "系统通告删除状态:0-已删除:1-未删除")
    private Integer annDelStatus;

    /**
     * 系统通告业务类型
     */
    @TableField(value = "ann_bus_type")
    @ApiModelProperty(value = "系统通告业务类型")
    private Integer annBusType;

    /**
     * 系统通告业务ID
     */
    @TableField(value = "ann_bus_id")
    @ApiModelProperty(value = "系统通告业务ID")
    private Integer annBusId;

    /**
     * 系统通告打开方式:0-跳转;1-新开页
     */
    @TableField(value = "ann_open_type")
    @ApiModelProperty(value = "系统通告打开方式:0-跳转;1-新开页")
    private Integer annOpenType;

    /**
     * 系统通告组件路由
     */
    @TableField(value = "ann_open_page")
    @ApiModelProperty(value = "系统通告组件路由")
    private String annOpenPage;

    /**
     * 系统通告创建人ID
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "系统通告创建人ID")
    private Integer createBy;

    /**
     * 系统通告创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "系统通告创建时间")
    private Date createTime;

    /**
     * 系统通告更新人ID
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "系统通告更新人ID")
    private Integer updateBy;

    /**
     * 系统通告更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "系统通告更新时间")
    private Date updateTime;

    /**
     * 系统通告通知用户名单
     */
    @ApiModelProperty(value = "系统通告通知用户名单")
    private String annUserIds;

    /**
     * 系统通告通知部门名单
     */
    @ApiModelProperty(value = "系统通告通知部门名单")
    private String annDeptIds;

    /**
     * 系统通告摘要信息
     */
    @TableField(value = "ann_msg_abstract")
    @ApiModelProperty(value = "系统通告摘要信息")
    private String annMsgAbstract;

    /**
     * sys_announcement_config id
     */
    @TableField(value = "ann_config_Id")
    private Integer annConfigId;

    private static final long serialVersionUID = 1L;
}