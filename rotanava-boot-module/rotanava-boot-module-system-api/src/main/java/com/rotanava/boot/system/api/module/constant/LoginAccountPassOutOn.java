package com.rotanava.boot.system.api.module.constant;


/**
 * 登录帐户过期开关
 *
 * @author WeiQiangMiao
 * @date 2021-03-29
 */
public enum LoginAccountPassOutOn {

    //密码过期是否开启:0-关闭;1-开启

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

    LoginAccountPassOutOn(Integer on) {
        this.on = on;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }
}
