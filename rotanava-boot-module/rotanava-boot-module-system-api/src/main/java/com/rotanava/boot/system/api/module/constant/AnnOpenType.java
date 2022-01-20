package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 系统通告打开方式:0-跳转;1-新开页
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum AnnOpenType {

    //系统通告打开方式:0-跳转;1-新开页

    /**
     * 0-跳转
     */
    JUMP(0),

    /**
     * 1-新开页
     */
    NEW_PAGE(1);

    private int type;

    AnnOpenType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}