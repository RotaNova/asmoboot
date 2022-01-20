package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum AnnTargetType {

    //系统通告对象类型:0-全体用户;1-指定用户;2-指定部门

    /**
     * 0-全体用户
     */
    ALL_USER(0),

    /**
     * 1 指定用户
     */
    DESIGNATED_USER(1),

    /**
     * 2-指定部门
     */
    DESIGNATED_DEPARTMENT(2);

    private int type;

    AnnTargetType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}