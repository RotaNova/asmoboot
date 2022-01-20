package com.rotanava.boot.system.api.module.constant;


/**
 * 系统部门状态:0-未激活;1-正常:2-冻结:3-过期
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum DeptStatus {

    //系统部门状态:0-未激活;1-正常:2-冻结:3-过期

    /**
     * 0-失败
     */
    INACTIVATED(0),

    /**
     * 1-正常
     */
    NORMAL(1),

    /**
     * 2-冻结
     */
    FREEZE(2),

    /**
     * 3-过期
     */
    EXPIRED(3);


    private Integer status;

    DeptStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}