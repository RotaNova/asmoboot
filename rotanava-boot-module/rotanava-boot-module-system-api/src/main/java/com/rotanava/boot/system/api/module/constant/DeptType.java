package com.rotanava.boot.system.api.module.constant;


/**
 * 系统部门类型:0-一级部门;1-子部门
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum DeptType {

    //系统部门类型:0-一级部门;1-子部门

    /**
     * 0-一级部门
     */
    FIRST_LEVEL_DEPARTMENT(0),

    /**
     * 1-子部门
     */
    SUB_DEPARTMENT(1);


    DeptType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer type;

}