package com.rotanava.boot.system.api.module.constant;

/**
 * 密码强度
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
public enum PasswordStrength {

    //密码强度 1-低 2-中 3-高

    /**
     * 低
     */
    LOW(1, "^(?=.{6,}).*$"),

    /**
     * 中
     */
    DURING(2, "^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$"),

    /**
     * 高
     */
    HIGH(3, "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$");

    /**
     * 类型
     */
    private Integer type;

    /**
     * 编译
     */
    private String compile;

    PasswordStrength(Integer type, String compile) {
        this.type = type;
        this.compile = compile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompile() {
        return compile;
    }

    public void setCompile(String compile) {
        this.compile = compile;
    }

    public static PasswordStrength findByType(Integer type) {

        PasswordStrength[] enums = PasswordStrength.values();
        for (PasswordStrength passwordStrength : enums) {
            if (passwordStrength.getType().equals(type)) {
                return passwordStrength;
            }
        }
        return PasswordStrength.LOW;
    }
}
