package com.rotanava.framework.common.constant.enums;

import com.rotanava.framework.common.constant.CommonConstant;

/**
 * 单点登录开关
 *
 * @author WeiQiangMiao
 * @date 2021-05-28
 */
public enum SingleSignOn {

    //单点登录开关:0-关闭;1-踢下线;2-禁止登录

    /**
     * 关闭
     */
    SHUT_DOWN(0,CommonConstant.PREFIX_USER_TOKEN),

    /**
     * 踢下线
     */
    OFFLINE(1,CommonConstant.PREFIX_ONLINE_USER_TOKEN),


    /**
     * 禁止登录
     */
    PROHIBIT(2, CommonConstant.PREFIX_ONLINE_USER_PROHIBIT_TOKEN)

    ;

    private Integer on;

    private String cache;

    SingleSignOn(Integer on, String cache) {
        this.on = on;
        this.cache = cache;
    }

    public Integer getOn() {
        return on;
    }

    public void setOn(Integer on) {
        this.on = on;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public static SingleSignOn findByOn(Integer on) {

        SingleSignOn[] enums = SingleSignOn.values();
        for (SingleSignOn singleSignOn : enums) {
            if (singleSignOn.getOn().equals(on)) {
                return singleSignOn;
            }
        }
        return SingleSignOn.SHUT_DOWN;
    }
}
