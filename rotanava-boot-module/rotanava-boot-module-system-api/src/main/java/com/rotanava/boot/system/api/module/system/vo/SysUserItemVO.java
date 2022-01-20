package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 18:14
 */
@Data
public class SysUserItemVO implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer sysUserId;

    /**
     * 登陆账号
     */
    @ApiModelProperty(value = "登陆账号")
    private String userAccountName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 账号身份:0-成员;1-管理员
     */
    @ApiModelProperty(value = "账号身份:0-成员;1-管理员")
    @Dict(dicCode = "userSysrole")
    private Integer userSysrole;

    /**
     * 系统用户的编号/工号
     */
    @ApiModelProperty(value = "系统用户的编号")
    private String userCode;

    /**
     * 用户所属系统部门
     */
    @ApiModelProperty(value = "用户所属系统部门")
    private String sysDepartmentStr;


    /**
     * 用户系统角色
     */
    @ApiModelProperty(value = "用户系统角色")
    private String sysRoleStr;

    /**
     * 用户部门角色
     */
    @ApiModelProperty(value = "用户部门角色")
    private Map<Integer,String> deptRoleStrMap;

    /**
     * 该用户部门角色
     */
    @ApiModelProperty(value = "用户部门角色")
    private String deptRoleStr;

    /**
     * 系统用户状态:0-未激活;1-正常:2-冻结:3-过期
     */
    @ApiModelProperty(value = "系统用户状态:0-未激活;1-正常:2-冻结:3-过期")
    private Integer userStatus;

    @ApiModelProperty(value = "性别 0-女;1-男;2-不透露")
    @Dict(dicCode = "sex")
    private Integer userSex;

    @ApiModelProperty(value = "系统用户联系手机号")
    private String userPhone;

    //只在getShareListSysUser和 GetShareFaceGroupListSysUserDTO 有效 代表是否分享了该人
    private Boolean shareThisUser;
}