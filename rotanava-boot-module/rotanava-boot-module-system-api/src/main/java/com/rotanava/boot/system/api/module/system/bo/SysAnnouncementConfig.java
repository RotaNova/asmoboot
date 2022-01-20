package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-04-01 16:59
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-SysAnnouncementConfig")
@Data
@TableName(value = "sys_announcement_config")
public class SysAnnouncementConfig implements Serializable {
    
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 配置名称
     */
    @ApiModelProperty(value="配置名称")
    private String configName;

    /**
     * 类型 0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value="0-通知消息;1-系统消息;2-告警消息")
    private Integer type;

    /**
     * 系统通知 0-不通知 1-通知
     */
    @ApiModelProperty(value="系统通知")
    private Integer sysNotice;

    /**
     * 短信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value="短信通知")
    private Integer smsNotice;

    /**
     * 微信通知 0-不通知 1-通知
     */
    @ApiModelProperty(value="微信通知")
    private Integer wechatNotice;

    /**
     * 邮件通知 0-不通知 1-通知
     */
    @ApiModelProperty(value="邮件通知")
    private Integer emailNotice;

    /**
     * 电话通知  0-不通知 1-通知
     */
    @ApiModelProperty(value="电话通知")
    private Integer phoneNotice;

    /**
     * 允许用户关闭消息通知  0-允许 1-不允许
     */
    @ApiModelProperty(value="允许用户关闭消息通知  0-允许 1-不允许")
    private Integer allowCloseNotice;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    private Integer annTarget;

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

    private static final long serialVersionUID = 1L;
}