package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-12 11:09
 */
@Data
public class AnnouncementItemVO implements Serializable {

    /**
     * 系统通告编号
     */
    @ApiModelProperty(value = "系统通告编号")
    private Integer sysAnnoId;

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    @Dict(dicCode = "ann_category")
    private Integer annCategory;

    /**
     * 系统通告标题
     */
    @ApiModelProperty(value = "系统通告标题")
    private String annTitle;

    /**
     * 系统通告发布人
     */
    @ApiModelProperty(value = "系统通告发布人")
    private String annSender;

    /**
     * 系统通告优先级
     */
    @ApiModelProperty(value = "系统通告优先级")
    @Dict(dicCode = "annPriority")
    private Integer annPriority;

    /**
     * 系统通告发布状态:0-未发布;1-已发布;2-已撤销
     */
    @ApiModelProperty(value = "系统通告发布状态:0-未发布;1-已发布;2-已撤销")
    @Dict(dicCode = "ann_send_status")
    private Integer annSendStatus;

    /**
     * 系统通告发布时间
     */
    @ApiModelProperty(value = "系统通告发布时间")
    private Long annSendTime;

    /**
     * 系统通告撤销时间
     */
    @ApiModelProperty(value = "系统通告撤销时间")
    private Long annCancelTime;

    /**
     * 系统通告创建时间
     */
    @ApiModelProperty(value = "系统通告创建时间")
    private Long createTime;

}