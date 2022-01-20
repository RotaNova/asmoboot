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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-WechatSso")
@Data
@TableName(value = "wechat_sso")
public class WechatSso implements Serializable {
    /**
     * 微信登录信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="微信登录信息ID")
    private Integer id;

    /**
     * 系统用户标识ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value="系统用户标识ID")
    private Integer sysUserId;

    /**
     * 微信OpenID
     */
    @TableField(value = "wc_open_id")
    @ApiModelProperty(value="微信OpenID")
    private String wcOpenId;

    /**
     * 微信UniID
     */
    @TableField(value = "wc_uni_id")
    @ApiModelProperty(value="微信UniID")
    private String wcUniId;

    /**
     * 微信头像
     */
    @TableField(value = "wc_photo_url")
    @ApiModelProperty(value="微信头像")
    private String wcPhotoUrl;

    /**
     * 微信昵称
     */
    @TableField(value = "wc_name")
    @ApiModelProperty(value="微信昵称")
    private String wcName;

    /**
     * 微信手机号
     */
    @TableField(value = "wc_phone")
    @ApiModelProperty(value="微信手机号")
    private String wcPhone;

    /**
     * 微信绑定时间
     */
    @TableField(value = "wc_bind_time")
    @ApiModelProperty(value="微信绑定时间")
    private Date wcBindTime;

    /**
     * 微信绑定状态:0-解绑;1-绑定
     */
    @TableField(value = "wc_bind_status")
    @ApiModelProperty(value="微信绑定状态:0-解绑;1-绑定")
    private Integer wcBindStatus;

    private static final long serialVersionUID = 1L;
}