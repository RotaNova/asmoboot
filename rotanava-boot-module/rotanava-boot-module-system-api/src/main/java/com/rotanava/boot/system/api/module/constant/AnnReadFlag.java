package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 系统通告阅读状态:0-未读;1-已读
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum AnnReadFlag {

    //系统通告阅读状态:0-未读;1-已读

    /**
     * 0-未读
     */
    UNREAD(0),

    /**
     * 1-已读
     */
    HAVE_READ(1);

    private int type;

    AnnReadFlag(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}