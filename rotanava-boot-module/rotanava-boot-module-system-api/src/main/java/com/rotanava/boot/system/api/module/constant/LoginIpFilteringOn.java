package com.rotanava.boot.system.api.module.constant;

/**
 * 登录ip过滤开关
 *
 * @author WeiQiangMiao
 * @date 2021-03-26
 */
public enum LoginIpFilteringOn {

    //登录IP过滤是否开启：0-禁止;1-允许

    /**
     * 禁止
     */
    PROHIBIT(0),

    /**
     * 允许
     */
    ALLOW(1)
    ;

    private Integer on;

    LoginIpFilteringOn(Integer on) {
        this.on = on;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }
}
