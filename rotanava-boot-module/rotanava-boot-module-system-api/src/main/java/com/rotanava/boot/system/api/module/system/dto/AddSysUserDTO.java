package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 添加用户dto
 * @author: jintengzhou
 * @date: 2021-03-04 16:42
 */
@Data
public class AddSysUserDTO implements Serializable {
    /**
     * 登陆账号
     */
    @NotBlank
    @ApiModelProperty(value = "登陆账号", required = true)
    private String userAccountName;

    /**
     * 用户名
     */
    @NotBlank
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    /**
     * 系统账户密码
     */
    @NotBlank
    @ApiModelProperty(value = "系统账户密码", required = true)
    private String userPassword;

    /**
     * 系统用户生日信息
     */
    @ApiModelProperty(value = "系统用户生日信息")
    private Long userBirthday;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @NotNull
    @Range(min = 0, max = 2)
    @ApiModelProperty(value = "系统用户性别:0-女;1-男;2-不透露", required = true)
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
     * 系统用户的编号/工号
     */
    @NotBlank
    @ApiModelProperty(value = "系统用户的编号", required = true)
    private String userCode;

    /**
     * 系统用户的职务/职称
     */
    @ApiModelProperty(value = "系统用户的职务")
    private String userOccupation;


    /**
     * 账号身份:0-成员;1-管理员
     */
    @NotNull
    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "账号身份:0-成员;1-管理员", required = true)
    private Integer userSysrole;

    @ApiModelProperty(value = "负责部门 userSysrole为1管理员时候必填")
    private List<Integer> responsibleDeptList;

    /**
     * 系统用户地址信息
     */
    @ApiModelProperty(value = "系统用户地址信息")
    private String userAddress;

    /**
     * 系统用户有效日期
     */
    @ApiModelProperty(value = "系统用户有效日期", required = true)
    private Long userValidTime;


    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private List<Integer> sysRoleIdList;

    /**
     * 用户所属部门
     */
    @ApiModelProperty(value = "用户所属部门", required = true)
    private List<Integer> sysDepartmentIdList;
}