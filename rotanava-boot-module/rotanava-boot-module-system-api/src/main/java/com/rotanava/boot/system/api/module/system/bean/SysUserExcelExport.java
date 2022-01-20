package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 用户导出excel bean
 * @author: jintengzhou
 * @date: 2021-05-19 17:49
 */
@Data
@ExcelTag(tag = "用户导出")
public class SysUserExcelExport implements Serializable {

    @ExcelTag(tag = "用户名", width = 5000)
    private String userName;

    @ExcelTag(tag = "登录账号", width = 5000)
    private String userAccountName;

    @ExcelTag(tag = "用户所属部门", width = 10000)
    private String sysDepartmentIdList;

    @ExcelTag(tag = "账号身份", width = 5000)
    private String userSysrole;

    @ExcelTag(tag = "负责部门", width = 10000)
    private String responsibleDeptList;

    @ExcelTag(tag = "编号", width = 5000)
    private String userCode;

    @ExcelTag(tag = "有效期", width = 5000)
    private String userValidTime;

    @ExcelTag(tag = "状态", width = 5000)
    private String status;

    @ExcelTag(tag = "性别", width = 5000)
    private String userSex;

    @ExcelTag(tag = "系统角色", width = 10000)
    private String sysRoleIdList;

    @ExcelTag(tag = "职务", width = 5000)
    private String userOccupation;

    @ExcelTag(tag = "联系手机", width = 5000)
    private String userPhone;

    @ExcelTag(tag = "电子邮箱", width = 5000)
    private String userSafeEmail;

    @ExcelTag(tag = "家庭地址", width = 5000)
    private String userAddress;

    @ExcelTag(tag = "生日", width = 5000)
    private String userBirthday;
}