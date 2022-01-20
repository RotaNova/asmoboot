package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @description: 添加用户dto
 * @author: jintengzhou
 * @date: 2021-03-04 16:42
 */
@Data
public class UpdateSysUserDTO implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    @ApiModelProperty(value = "用户id", required = true)
    private Integer sysUserId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 系统用户生日信息
     */
    @ApiModelProperty(value = "系统用户生日信息")
    private Long userBirthday;

    /**
     * 系统用户性别:0-女;1-男;2-不透露
     */
    @Range(min = 0, max = 2)
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
    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "账号身份:0-成员;1-管理员")
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
    @ApiModelProperty(value = "系统用户有效日期")
    private Long userValidTime;


    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色id")
    private Collection<Integer> sysRoleIdList;

    /**
     * 用户所属部门
     */
    @ApiModelProperty(value = "用户所属部门id", required = true)
    private Collection<Integer> sysDepartmentIdList;


    /**
     * 部门id 如果有传 需要传参数 deptRoleIdList
     */
    @ApiModelProperty(value = "部门id 如果有传 需要传参数 deptRoleIdList")
    private Integer deptId;

    /**
     * 部门角色id
     */
    @ApiModelProperty(value = "部门角色id")
    private Collection<Integer> deptRoleIdList;
}