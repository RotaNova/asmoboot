package com.rotanava.boot.system.api.module.constant;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-06 9:57
 */
public enum EmailSafeType {

    //邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码

    /**
     * 0-无
     */
    NONE(0),

    /**
     * 1-验证邮件完整性
     */
    VERIFY_EMAIL_INTEGRITY(1),

     /**
      * 2-验证验证码
      */
     VERIFICATION_CODE(2);

    private Integer type;

    EmailSafeType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
