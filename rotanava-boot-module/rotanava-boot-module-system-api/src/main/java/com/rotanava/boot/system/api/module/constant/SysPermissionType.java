package com.rotanava.boot.system.api.module.constant;

/**
 * 系统权限类型
 *
 * @author zjt
 * @date 2021-03-10
 */
public enum SysPermissionType {

    //系统权限类型：0-系统角色;1-部门角色;2-部门

    /**
     * 0-系统角色
     */
    SYSTEM_ROLE(0),

    /**
     * 1-部门角色
     */
    DEPT_ROLE(1),

    /**
     * 2-部门
     */
    DEPT(2);

    private Integer type;

    SysPermissionType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
