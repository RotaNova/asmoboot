package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum NoticeType {

    //系统通告对象类型:0-不通知;1-通知

    /**
     * 0 不通知
     */
    NO_NOTICE(0),

    /**
     * 1 通知
     */
    NOTICE(1);

    private int type;

    NoticeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}