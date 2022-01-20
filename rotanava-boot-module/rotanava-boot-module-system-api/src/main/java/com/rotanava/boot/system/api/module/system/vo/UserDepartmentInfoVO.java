package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

/**
 * 用户离开信息签证官
 *
 * @author weiqiangmiao
 * @date 2021/07/09
 */
@Data
public class UserDepartmentInfoVO {

    /**
     * 系统部门编码
     */
    private String deptCode;

    /**
     * '系统部门名称'
     */
    private String deptName;

    /**
     * '系统部门英文名称'
     */
    private String deptNameEn;

    /**
     * 系统部门负责人联系方式,
     */
    private String deptManagerPhone;

    /**
     * '系统部门负责人信息'
     */
    private String deptManager;

    /**
     * '系统用户是否为部门管理员:0-否;1-是'
     */
    private Integer deptManage;

    /**
     * 系统部门有效日期
     */
    private Long deptValidTime;
}
