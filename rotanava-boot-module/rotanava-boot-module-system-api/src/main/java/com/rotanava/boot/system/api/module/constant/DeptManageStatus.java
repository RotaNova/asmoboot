package com.rotanava.boot.system.api.module.constant;


/**
 * 系统部门状态:0-未激活;1-正常:2-冻结:3-过期
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum DeptManageStatus {

    //系统用户是否为部门管理员:0-否;1-是

    /**
     * 0-不是管理员
     */
    NOT_ADMINISTRATOR(0),

    /**
     * 1-是
     */
    ADMINISTRATOR(1);


    private Integer status;

    DeptManageStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    //获取枚举实例
    public static DeptManageStatus fromValue(Integer value) {
        for (DeptManageStatus val : values()) {
            if (val.getStatus().equals(value)) {
                return val;
            }
        }
        throw new IllegalArgumentException();
    }

}