package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 保存消息配置dto
 * @author: jintengzhou
 * @date: 2021-04-01 17:31
 */
@Data
public class SaveAnnouncementConfigDTO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    @NotNull
    private Integer sysAnnConfigId;

    /**
     * 系统通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "系统通知")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer sysNotice;

    /**
     * 短信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "短信通知")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer smsNotice;

    /**
     * 微信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "微信通知")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer wechatNotice;

    /**
     * 邮件通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "邮件通知")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer emailNotice;

    /**
     * 电话通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "电话通知")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer phoneNotice;

    /**
     * 允许用户关闭消息通知 0-允许 1-不允许
     */
    @ApiModelProperty(value = "允许用户关闭消息通知  0-不允许 1-允许")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer allowCloseNotice;

}