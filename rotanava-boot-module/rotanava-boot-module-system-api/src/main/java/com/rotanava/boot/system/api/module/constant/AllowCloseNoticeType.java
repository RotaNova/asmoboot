package com.rotanava.boot.system.api.module.constant;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-02 16:00
 */
public enum AllowCloseNoticeType {

    //允许用户关闭消息通知  0-允许 1-不允许

    /**
     * 0-允许
     */
    ALLOW(1),

    /**
     * 1-不允许
     */
    NOT_ALLOWED(0);

    private int type;

    AllowCloseNoticeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}