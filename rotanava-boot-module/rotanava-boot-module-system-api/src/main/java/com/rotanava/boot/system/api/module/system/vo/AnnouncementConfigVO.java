package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 16:56
 */
@Data
public class AnnouncementConfigVO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    private Integer sysAnnConfigId;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /**
     * 类型名称  按名称进行分组排序
     */
    @ApiModelProperty(value = "类型名称  按名称进行分组排序")
    @Dict(dicCode = "ann_category")
    private Integer type;

    /**
     * 系统通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "系统通知")
    private Integer sysNotice;

    /**
     * 短信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "短信通知")
    private Integer smsNotice;

    /**
     * 微信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "微信通知")
    private Integer wechatNotice;

    /**
     * 邮件通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "邮件通知")
    private Integer emailNotice;

    /**
     * 电话通知 0-不通知 1-通知
     */
    @ApiModelProperty(value = "电话通知")
    private Integer phoneNotice;

    /**
     * 允许用户关闭消息通知  0-允许 1-不允许
     */
    @ApiModelProperty(value = "允许用户关闭消息通知  0-允许 1-不允许")
    private Integer allowCloseNotice;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    @Dict(dicCode = "annTarget")
    private Integer annTarget;

    /**
     * 钉钉机器人 0-不通知 1-通知
     */
    @ApiModelProperty(value="钉钉机器人")
    private Integer dingTalkNotice;

}