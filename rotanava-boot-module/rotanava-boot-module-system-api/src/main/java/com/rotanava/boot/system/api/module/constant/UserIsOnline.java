package com.rotanava.boot.system.api.module.constant;

/**
 * 用户在线
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum UserIsOnline {

    //系统用户当前是否在线:0-离线:1-在线

    /**
     * 0-离线
     */
    OFFLINE(0),

    /**
     * 1-在线
     */
    ONLINE(1);


    private Integer status;

    UserIsOnline(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
