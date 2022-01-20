package com.rotanava.boot.system.api.module.constant;

/**
 * 登录访问类型
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
public enum LoginAccessType {

    //登录鉴权方式：0-匿名;1-账号密码;2-验证码

    /**
     * 0-匿名
     */
    ANONYMOUS(0),

    /**
     * 1-账号密码
     */
    ACCOUNT_PASSWORD(1),

    /**
     * 2-验证码
     */
    VERIFICATION_CODE(2);

    private Integer type;

    LoginAccessType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
