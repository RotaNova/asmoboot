package com.rotanava.boot.system.api.constant.enums;

/**
 * @description: 系统角色类型
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum RoleDataScopeType {

    //角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门

    /**
     * 0-仅本人
     */
    MYSELF(0),

    /**
     * 1部门
     */
    THIS_DEPARTMENT(1),

    /**
     * 2-本部门及下属部门
     */
    THIS_DEPARTMENT_AND_ITS_SUBORDINATE_DEPTS(2),

    /**
     * 3-全公司
     */
    COMPANY_ALL(3),

    /**
     * 4-指定部门
     */
    CHOOSE_DEPARTMENT(4);


    private int type;

    RoleDataScopeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}