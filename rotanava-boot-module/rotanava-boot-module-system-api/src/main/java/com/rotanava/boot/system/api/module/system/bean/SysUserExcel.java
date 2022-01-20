package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户导入excel bean
 * @author: jintengzhou
 * @date: 2021-05-19 17:49
 */
@Data
@ExcelTag(tag = "用户导入")
public class SysUserExcel implements Serializable {

    @ExcelTag(tag = "用户名（必填）", index = 0, width = 5000)
    private String userName;

    @ExcelTag(tag = "登录账号（必填）", index = 1, width = 5000)
    private String userAccountName;

    @ExcelTag(tag = "密码（非必填，默认与账号同名）", index = 2, width = 5000)
    private String userPassword;

    @ExcelTag(tag = "用户所属部门（多个部门时用英文;区分）", index = 3, width = 10000)
    private String sysDepartmentIdList;

    @ExcelTag(tag = "账号身份（管理员或成员，默认成员）", index = 4, width = 5000)
    private String userSysrole;

    @ExcelTag(tag = "负责部门（账号身份为管理员时才可填写，只可填自己所属的部门,多个部门时用英文;区分）", index = 5, width = 10000)
    private String responsibleDeptList;

    @ExcelTag(tag = "编号（必填）", index = 6, width = 5000)
    private String userCode;

    @ExcelTag(tag = "有效期", index = 7, width = 5000)
    private String userValidTime;

    @ExcelTag(tag = "性别（未填，则为不透露）", index = 8, width = 5000)
    private String userSex;

    @ExcelTag(tag = "系统角色", index = 9, width = 10000)
    private String sysRoleIdList;

    @ExcelTag(tag = "职务", index = 10, width = 5000)
    private String userOccupation;

    @ExcelTag(tag = "联系手机", index = 11, width = 5000)
    private String userPhone;

    @ExcelTag(tag = "电子邮箱", index = 12, width = 5000)
    private String userSafeEmail;

    @ExcelTag(tag = "家庭地址", index = 13, width = 5000)
    private String userAddress;

    @ExcelTag(tag = "生日", index = 14, width = 5000)
    private String userBirthday;

    @ExcelTag(tag = "导入失败原因", index = 15, width = 20000)
    private String failReason;
}