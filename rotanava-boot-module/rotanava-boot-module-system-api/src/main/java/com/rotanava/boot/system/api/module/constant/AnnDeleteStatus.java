package com.rotanava.boot.system.api.module.constant;


/**
 * 系统通告删除状态:0-已删除:1-未删除
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum AnnDeleteStatus {

    //系统通告删除状态:0-已删除:1-未删除

    /**
     * 0-已删除
     */
    DELETED(0),

    /**
     * 1-未删除
     */
    NOT_DELETED(1);


    private Integer status;

    AnnDeleteStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}