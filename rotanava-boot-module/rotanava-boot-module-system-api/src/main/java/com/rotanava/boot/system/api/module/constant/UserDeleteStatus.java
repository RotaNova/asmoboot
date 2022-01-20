package com.rotanava.boot.system.api.module.constant;


/**
 * 用户删除状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum UserDeleteStatus {

    //系统用户当前是否被删除:0-已删除;1-未删除

    /**
     * 0-已删除
     */
    DELETED(0),

    /**
     * 1-未删除
     */
    NOT_DELETED(1);


    private Integer status;

    UserDeleteStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}