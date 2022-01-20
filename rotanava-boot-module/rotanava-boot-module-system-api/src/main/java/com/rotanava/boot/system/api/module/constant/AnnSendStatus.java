package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 系统通告发布状态:0-未发布;1-已发布;2-已撤销
 * @author: jintengzhou
 * @date: 2021-03-11 14:00
 */
public enum AnnSendStatus {

    //系统通告发布状态:0-未发布;1-已发布;2-已撤销

    /**
     * 0-未发布
     */
    UNPUBLISHED(0),

    /**
     * 1-已发布
     */
    PUBLISHED(1),

    /**
     * 2-已撤销
     */
    REVOKED(2);


    private Integer status;

    AnnSendStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
