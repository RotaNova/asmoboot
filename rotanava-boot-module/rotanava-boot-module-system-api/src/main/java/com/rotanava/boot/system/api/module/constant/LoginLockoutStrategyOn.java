package com.rotanava.boot.system.api.module.constant;


/**
 * 登录锁定策略开关
 *
 * @author WeiQiangMiao
 * @date 2021-03-29
 */
public enum LoginLockoutStrategyOn {

    //账户锁定策略: 0-关闭;1-开启


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

    LoginLockoutStrategyOn(Integer on) {
        this.on = on;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }
}
