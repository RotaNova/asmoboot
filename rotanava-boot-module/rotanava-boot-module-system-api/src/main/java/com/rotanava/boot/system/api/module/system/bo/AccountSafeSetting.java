package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-AccountSafeSetting")
@Data
@TableName(value = "account_safe_setting")
public class AccountSafeSetting implements Serializable {
    /**
     * 安全设置信息ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "安全设置信息ID")
    private Integer id;

    /**
     * 系统用户标识ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value = "系统用户标识ID")
    private Integer sysUserId;

    /**
     * 手机安全保护类型:0-无:1-验证号码完整性;2-验证验证码
     */
    @TableField(value = "phone_safe_type")
    @ApiModelProperty(value = "手机安全保护类型:0-无:1-验证号码完整性;2-验证验证码")
    private Integer phoneSafeType;

    /**
     * MFA信息
     */
    @TableField(value = "account_mfa")
    @ApiModelProperty(value = "MFA信息")
    private String accountMfa;

    /**
     * 邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码
     */
    @TableField(value = "email_safe_type")
    @ApiModelProperty(value = "邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码")
    private Integer emailSafeType;

    /**
     * 超时退出时间(分钟)
     */
    @TableField(value = "account_time_out_mins")
    @ApiModelProperty(value = "超时退出时间(分钟)")
    private Integer accountTimeOutMins;

    /**
     * 超时退出是否开启:0-关闭;1-开启
     */
    @TableField(value = "account_time_out_on")
    @ApiModelProperty(value = "超时退出是否开启:0-关闭;1-开启")
    private Integer accountTimeOutOn;

    /**
     * 密码过期时间(分钟)
     */
    @TableField(value = "account_pass_out_mins")
    @ApiModelProperty(value = "密码过期时间(分钟)")
    private Integer accountPassOutMins;

    /**
     * 密码过期是否开启:0-关闭;1-开启
     */
    @TableField(value = "account_pass_out_on")
    @ApiModelProperty(value = "密码过期是否开启:0-关闭;1-开启")
    private Integer accountPassOutOn;

    /**
     * 上次密码
     */
    @TableField(value = "account_pre_passwd")
    @ApiModelProperty(value = "上次密码")
    private String accountPrePasswd;

    /**
     * 上次密码修改时间
     */
    @TableField(value = "account_pre_passwd_time")
    @ApiModelProperty(value = "上次密码修改时间")
    private Date accountPrePasswdTime;

    private static final long serialVersionUID = 1L;
}