package com.rotanava.boot.system.api.module.constant;


/**
 * dict类型
 *
 * @author weiqiangmiao
 * @date 2021/11/11
 */
public enum DictType {

    //是否全员可见 0-否 1-是


    /**
     * 字典
     */
    DICTIONARY("字典"),

    /**
     * 数据
     */
    DATA("数据");


    private String type;

    DictType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String findType(String type) {
        for (DictType value : DictType.values()) {
            if (value.getType().equals(type)) {
                return value.getType();
            }
        }
        return null;
    }
}
