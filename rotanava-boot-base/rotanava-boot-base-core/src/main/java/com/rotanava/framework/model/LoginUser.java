package com.rotanava.framework.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 在线用户信息
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser implements Serializable {

	/**
	 * 系统用户标识ID
	 */
	@ApiModelProperty(value = "系统用户标识ID")
	private Integer id;

	/**
	 * 系统用户登录账户名
	 */
	@TableField(value = "user_accout_name")
	@ApiModelProperty(value = "系统用户登录账户名")
	private String userAccountName;

	/**
	 * 系统显示的用户昵称
	 */
	@TableField(value = "user_name")
	@ApiModelProperty(value = "系统显示的用户昵称")
	private String userName;

	/**
	 * 系统账户密码
	 */
	@TableField(value = "user_password")
	@ApiModelProperty(value = "系统账户密码")
	private String userPassword;

	/**
	 * 系统用户密码的加密盐
	 */
	@TableField(value = "user_passwd_salt")
	@ApiModelProperty(value = "系统用户密码的加密盐")
	private String userPasswdSalt;

	/**
	 * 头像桶名
	 */
	@TableField(value = "photo_bucket_name")
	@ApiModelProperty(value = "头像桶名")
	private String photoBucketName;

	/**
	 * 头像文件名
	 */
	@TableField(value = "photo_object_name")
	@ApiModelProperty(value = "头像文件名")
	private String photoObjectName;

	/**
	 * 系统用户头像
	 */
	@TableField(value = "user_photo_url")
	@ApiModelProperty(value = "系统用户头像")
	private String userPhotoUrl;

	/**
	 * 系统用户生日信息
	 */
	@TableField(value = "user_birthday")
	@ApiModelProperty(value = "系统用户生日信息")
	private Date userBirthday;

	/**
	 * 系统用户性别:0-女;1-男;2-不透露
	 */
	@TableField(value = "user_sex")
	@ApiModelProperty(value = "系统用户性别:0-女;1-男;2-不透露")
	private Integer userSex;

	/**
	 * 系统用户邮件
	 */
	@TableField(value = "user_email")
	@ApiModelProperty(value = "系统用户邮件")
	private String userEmail;

	/**
	 * 系统用户联系手机号
	 */
	@TableField(value = "user_phone")
	@ApiModelProperty(value = "系统用户联系手机号")
	private String userPhone;

	/**
	 * 系统用户状态:0-未激活;1-正常:2-冻结:3-过期
	 */
	@TableField(value = "user_status")
	@ApiModelProperty(value = "系统用户状态:0-未激活;1-正常:2-冻结:3-过期")
	private Integer userStatus;

	/**
	 * 系统用户的编号/工号
	 */
	@TableField(value = "user_code")
	@ApiModelProperty(value = "系统用户的编号/工号")
	private String userCode;

	/**
	 * 系统用户的职务/职称
	 */
	@TableField(value = "user_occupation")
	@ApiModelProperty(value = "系统用户的职务/职称")
	private String userOccupation;

	/**
	 * 系统用户创建人ID
	 */
	@TableField(value = "create_by")
	@ApiModelProperty(value = "系统用户创建人ID")
	private Integer createBy;

	/**
	 * 系统用户创建时间
	 */
	@TableField(value = "create_time")
	@ApiModelProperty(value = "系统用户创建时间")
	private Date createTime;

	/**
	 * 系统用户信息更新人ID
	 */
	@TableField(value = "update_by")
	@ApiModelProperty(value = "系统用户信息更新人ID")
	private Integer updateBy;

	/**
	 * 系统用户信息更新时间
	 */
	@TableField(value = "update_time")
	@ApiModelProperty(value = "系统用户信息更新时间")
	private Date updateTime;

	/**
	 * 系统用户的账号身份:0-成员;1-管理员
	 */
	@TableField(value = "user_sysrole")
	@ApiModelProperty(value = "系统用户的账号身份:0-成员;1-管理员")
	private Integer userSysrole;

	/**
	 * 系统用户描述
	 */
	@TableField(value = "user_description")
	@ApiModelProperty(value = "系统用户描述")
	private String userDescription;

	/**
	 * 系统用户地址信息
	 */
	@TableField(value = "user_address")
	@ApiModelProperty(value = "系统用户地址信息")
	private String userAddress;

	/**
	 * 系统用户有效日期
	 */
	@TableField(value = "user_valid_time")
	@ApiModelProperty(value = "系统用户有效日期")
	private Date userValidTime;

	/**
	 * 系统用户当前是否在线:0-离线:1-在线
	 */
	@TableField(value = "user_is_online")
	@ApiModelProperty(value = "系统用户当前是否在线:0-离线:1-在线")
	private Integer userIsOnline;

	/**
	 * 系统用户当前是否被删除:0-已删除;1-未删除
	 */
	@TableField(value = "user_delete_status")
	@ApiModelProperty(value = "系统用户当前是否被删除:0-已删除;1-未删除")
	private Integer userDeleteStatus;

	/**
	 * 是管理
	 */
	private Boolean isAdmin;

	private static final long serialVersionUID = 1L;
}
