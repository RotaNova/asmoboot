package com.rotanava.boot.system.api.module.constant;


/**
 * 是跳
 *
 * @author weiqiangmiao
 * @date 2021/10/14
 */
public enum IsJump {

    //是否跳转外部应用 0-否 1-是

    /**
     * 0-否
     */
    NO(0,"否"),

    /**
     * 1-是
     */
    YES(1,"是"),
    ;


    private Integer jump;


    private String chinese;

    IsJump(Integer jump, String chinese) {
        this.jump = jump;
        this.chinese = chinese;
    }

    public Integer getJump() {
        return jump;
    }

    public void setJump(Integer jump) {
        this.jump = jump;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Integer findJump(String chinese) {
        for (IsJump value : IsJump.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getJump();
            }
        }
        return null;
    }

    public static String findJump(Integer jump) {
        for (IsJump value : IsJump.values()) {
            if (value.getJump().equals(jump)) {
                return value.getChinese();
            }
        }
        return null;
    }
}

