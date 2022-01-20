package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 是否全员可见 0-否 1-是
 * @author: jintengzhou
 * @date: 2021-05-13 15:00
 */
public enum VisibleToAllType {

    //是否全员可见 0-否 1-是


    /**
     * 0-否
     */
    NOT_VISIBLE(0,"否"),

    /**
     * 1是
     */
    VISIBLE(1,"是");


    private Integer type;

    private String chinese;

    VisibleToAllType(int type, String chinese) {
        this.type = type;
        this.chinese = chinese;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Integer findType(String chinese) {
        for (VisibleToAllType value : VisibleToAllType.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getType();
            }
        }
        return null;
    }

    public static String findType(Integer type) {
        for (VisibleToAllType value : VisibleToAllType.values()) {
            if (value.getType().equals(type)) {
                return value.getChinese();
            }
        }
        return null;
    }
}
