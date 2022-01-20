package com.rotanava.boot.system.api.module.constant;

/**
 * 初始密码强制修改开关
 *
 * @author WeiQiangMiao
 * @date 2021-03-26
 */
public enum PassMandatoryModificationOn {

    //初始密码强制修改开关:0-关闭;1-开启

    /**
     * 关闭
     */
    SHUT_DOWN(0),

    /**
     * 开启
     */
    TURN_ON(1)
    ;

    private Integer on;

    PassMandatoryModificationOn(Integer on) {
        this.on = on;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }
}
