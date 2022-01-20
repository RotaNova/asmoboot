package com.rotanava.boot.system.api.module.constant;


/**
 * 是否自动生成
 *
 * @author weiqiangmiao
 * @date 2021/10/14
 */
public enum IsAuto {

    //是否自动生成 0-否 1-是

    /**
     * 0-否
     */
    NO(0,"否"),

    /**
     * 1-是
     */
    YES(1,"是"),
    ;


    private Integer auto;


    private String chinese;

    IsAuto(Integer auto, String chinese) {
        this.auto = auto;
        this.chinese = chinese;
    }

    public Integer getAuto() {
        return auto;
    }

    public void setAuto(Integer auto) {
        this.auto = auto;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Integer findAuto(String chinese) {
        for (IsAuto value : IsAuto.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getAuto();
            }
        }
        return null;
    }

    public static String findAuto(Integer auto) {
        for (IsAuto value : IsAuto.values()) {
            if (value.getAuto().equals(auto)) {
                return value.getChinese();
            }
        }
        return null;
    }
}

