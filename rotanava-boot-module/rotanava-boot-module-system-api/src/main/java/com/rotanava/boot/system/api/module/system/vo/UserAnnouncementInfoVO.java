package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-15 18:02
 */
@Data
public class UserAnnouncementInfoVO implements Serializable {

    /**
     * 用户通告id
     */
    @ApiModelProperty(value = "用户通告id")
    private Integer sysUserAnnouncementId;

    /**
     * 系统通告标题
     */
    @ApiModelProperty(value = "系统通告标题")
    private String annTitle;

    /**
     * 系统通告摘要信息
     */
    @ApiModelProperty(value = "系统通告摘要信息")
    private String annMsgAbstract;

    /**
     * 系统通告内容
     */
    @ApiModelProperty(value = "系统通告内容")
    private String annContent;

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    @Dict(dicCode = "ann_category")
    private Integer annCategory;

    /**
     * 系统通告发布时间
     */
    @ApiModelProperty(value = "系统通告发布时间")
    private Long annSendTime;

    /**
     * 系统通告发布人
     */
    @ApiModelProperty(value = "系统通告发布人")
    private String annSender;

    @ApiModelProperty(value = "系统通告阅读状态:0-未读;1-已读")
    private Integer annReadFlag;
}