package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 14:01
 */

/**
 * 系统用户列表
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysUser")
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    /**
     * 系统用户标识ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "系统用户标识ID")
    private Integer id;

    /**
     * 系统用户登录账户名
     */
    @ApiModelProperty(value = "系统用户登录账户名")
    private String userAccountName;

    /**
     * 系统显示的用户昵称
     */
    @ApiModelProperty(value = "系统显示的用户昵称")
    private String userName;

    /**
     * 系统账户密码
     */
    @ApiModelProperty(value = "系统账户密码")
    private String userPassword;

    /**
     * 系统用户密码的加密盐
     */
    @ApiModelProperty(value = "系统用户密码的加密盐")
    private String userPasswdSalt;

    /**
     * 头像桶名
     */
    @ApiModelProperty(value = "头像桶名")
    private String photoBucketName;

    /**
     * 头像文件名
     */
    @ApiModelProperty(value = "头像文件名")
    private String photoObjectName;

    /**
     * 系统用户头像
     */
    @ApiModelProperty(value = "系统用户头像")
    private String userPhotoUrl;

    /**
     * 系统用户生日信息
     */
    @ApiModelProperty(value = "系统用户生日信息")
    private Date userBirthday;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @ApiModelProperty(value = "系统用户性别:0-女;1-男;2-不透露")
    private Integer userSex;

    /**
     * 系统用户邮件
     */
    @ApiModelProperty(value = "系统用户邮件")
//    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userEmail;

    /**
     * 系统用户联系手机号
     */
    @ApiModelProperty(value = "系统用户联系手机号")
//    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userPhone;

    /**
     * 系统用户绑定的手机号
     */
    @ApiModelProperty(value = "系统用户绑定的手机号")
//    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userSafePhone;

    /**
     * 系统用户绑定的邮箱
     */
    @ApiModelProperty(value = "系统用户绑定的邮箱")
    private String userSafeEmail;

    /**
     * 系统用户状态:0-未激活;1-正常:2-冻结:3-过期
     */
    @ApiModelProperty(value = "系统用户状态:0-未激活;1-正常:2-冻结:3-过期")
    private Integer userStatus;

    /**
     * 之前的系统用户状态:0-未激活;1-正常:2-冻结:3-过期
     */
    @ApiModelProperty(value = "系统用户状态:0-未激活;1-正常:2-冻结:3-过期")
    private Integer befUserStatus;

    /**
     * 系统用户的编号/工号
     */
    @ApiModelProperty(value = "系统用户的编号/工号")
    private String userCode;

    /**
     * 系统用户的职务/职称
     */
    @ApiModelProperty(value = "系统用户的职务/职称")
    private String userOccupation;

    /**
     * 系统用户创建人ID
     */
    @ApiModelProperty(value = "系统用户创建人ID")
    private Integer createBy;

    /**
     * 系统用户创建时间
     */
    @ApiModelProperty(value = "系统用户创建时间")
    private Date createTime;

    /**
     * 系统用户信息更新人ID
     */
    @ApiModelProperty(value = "系统用户信息更新人ID")
    private Integer updateBy;

    /**
     * 系统用户信息更新时间
     */
    @ApiModelProperty(value = "系统用户信息更新时间")
    private Date updateTime;

    /**
     * 系统用户的账号身份:0-成员;1-管理员
     */
    @ApiModelProperty(value = "系统用户的账号身份:0-成员;1-管理员")
    private Integer userSysrole;

    /**
     * 系统用户描述
     */
    @ApiModelProperty(value = "系统用户描述")
    private String userDescription;

    /**
     * 系统用户地址信息
     */
    @ApiModelProperty(value = "系统用户地址信息")
    private String userAddress;

    /**
     * 系统用户有效日期
     */
    @ApiModelProperty(value = "系统用户有效日期")
    private Date userValidTime;

    /**
     * 系统用户当前是否在线:0-离线:1-在线
     */
    @ApiModelProperty(value = "系统用户当前是否在线:0-离线:1-在线")
    private Integer userIsOnline;

    /**
     * 系统用户当前是否被删除:0-已删除;1-未删除
     */
    @ApiModelProperty(value = "系统用户当前是否被删除:0-已删除;1-未删除")
    private Integer userDeleteStatus;

    private static final long serialVersionUID = 1L;
}