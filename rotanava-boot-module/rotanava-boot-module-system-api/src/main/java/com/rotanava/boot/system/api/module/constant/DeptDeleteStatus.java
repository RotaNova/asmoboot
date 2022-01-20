package com.rotanava.boot.system.api.module.constant;


/**
 * 系统部门删除状态  0删除 1未删除
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum DeptDeleteStatus {

    //系统部门删除状态  0删除 1未删除

    /**
     * 0删除
     */
    DELETE(0),

    /**
     * 1未删除
     */
    NOT_DELETED(1);


    private Integer status;

    DeptDeleteStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}