package com.rotanava.boot.system.api.module.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rotanava.boot.system.api.module.system.bean.DeptRoleLabel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用户详情
 * @author: jintengzhou
 * @date: 2021-03-04 16:42
 */
@Data
public class SysUserInfoVO implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer sysUserId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "系统用户登录账户名")
    private String userAccountName;

    /**
     * 系统用户生日信息
     */
    @ApiModelProperty(value = "系统用户生日信息")
    private Long userBirthday;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @ApiModelProperty(value = "系统用户性别:0-女;1-男;2-不透露")
    private Integer userSex;

    /**
     * 系统用户邮件
     */
    @ApiModelProperty(value = "系统用户邮件")
    private String userEmail;

    /**
     * 系统用户联系手机号
     */
    @ApiModelProperty(value = "系统用户联系手机号")
    private String userPhone;


    /**
     * 系统用户的职务/职称
     */
    @ApiModelProperty(value = "系统用户的职务")
    private String userOccupation;

    /**
     * 账号身份:0-成员;1-管理员
     */
    @ApiModelProperty(value = "账号身份:0-成员;1-管理员")
    private Integer userSysrole;

    @ApiModelProperty(value = "系统用户的编号/工号")
    private String userCode;

    /**
     * 系统用户地址信息
     */
    @ApiModelProperty(value = "系统用户地址信息")
    private String userAddress;

    /**
     * 系统用户有效日期
     */
    @ApiModelProperty(value = "系统用户有效日期")
    private Long userValidTime;

    /**
     * 用户系统角色
     */
    @ApiModelProperty(value = "用户系统角色")
    private List<SysRoleLabel> sysRoleLabelList;

    /**
     * 用户部门角色 有deptId 才计算
     */
    @ApiModelProperty(value = "用户部门角色")
    private List<DeptRoleLabel> deptRoleLabelList;

    /**
     * 用户所属部门
     */
    @ApiModelProperty(value = "用户所属系统部门")
    private List<SysDepartmentLabel> sysDepartmentLabelList;

    /**
     * 负责部门
     */
    @ApiModelProperty(value = "负责部门")
    private List<SysDepartmentLabel> responsibleDeptLabelList;

    /**
     * 密码强度:1-低;2-中;3-高
     */
    @ApiModelProperty(value = "密码强度:1-低;2-中;3-高")
    private Integer passwordStrength;

    @ApiModelProperty(value = "头像url")
    private String avatarUrl;

    @ApiModelProperty(value = "是否绑定了手机")
    private Boolean bindPhoneFlag;

    @ApiModelProperty(value = "是否验证了邮箱")
    private Boolean verificationEmailFlag;
}