package com.rotanava.boot.system.api.module.constant;


/**
 * 登录状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum LoginStatus {

    //登录的状态:0-失败:1-成功

    /**
     * 0-失败
     */
    FAILURE(0),

    /**
     * 1-成功
     */
    SUCCESS(1);


    private Integer status;

    LoginStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}