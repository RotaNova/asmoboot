package com.rotanava.boot.system.api.module.constant;

/**
 * 状态 1正常 2锁定
 *
 * @author zjt
 * @date 2021-03-08
 */
public enum OpenApiStatus {

    //1-正常;
    NORMAL(1),

    //2-未删除
    LOCK(2);


    private Integer status;

    OpenApiStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
