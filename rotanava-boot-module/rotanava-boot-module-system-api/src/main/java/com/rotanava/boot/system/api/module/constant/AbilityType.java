package com.rotanava.boot.system.api.module.constant;


/**
 * 页面类型
 *
 * @author weiqiangmiao
 * @date 2021/10/14
 */
public enum AbilityType {

    //页面类型 0增 1删 2改 3查

    /**
     * 0增
     */
    ADD(0,"增"),

    /**
     * 1删
     */
    DELETE(1,"删"),
    /**
     * 2改
     */
    UPDATE(2,"改"),
    /**
     * 3查
     */
    GET(3,"查");

    private Integer type;

    private String chinese;

    AbilityType(Integer type, String chinese) {
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
        for (AbilityType value : AbilityType.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getType();
            }
        }
        return null;
    }

    public static String findType(Integer type) {
        for (AbilityType value : AbilityType.values()) {
            if (value.getType().equals(type)) {
                return value.getChinese();
            }
        }
        return null;
    }
}
