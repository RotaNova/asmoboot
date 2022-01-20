package com.rotanava.boot.system.api.module.constant;


/**
 * 上次登录信息提示开关
 *
 * @author WeiQiangMiao
 * @date 2021-03-29
 */
public enum LastLoginInfoPromptOn {

    //上次登录信息提示开关:0-关闭;1-开启


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

    LastLoginInfoPromptOn(Integer on) {
        this.on = on;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }
}
