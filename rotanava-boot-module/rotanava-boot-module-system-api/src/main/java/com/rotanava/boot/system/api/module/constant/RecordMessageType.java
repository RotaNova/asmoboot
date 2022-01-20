package com.rotanava.boot.system.api.module.constant;


/**
 * 页面类型
 *
 * @author weiqiangmiao
 * @date 2021/10/14
 */
public enum RecordMessageType {

    /**
     * 加载中
     */
    LOADING(0, "加载中"),

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    ERROR(2, "失败");

    private Integer type;

    private String name;

    public Integer getType() {
        return type;
    }


    public String getName() {
        return name;
    }

    RecordMessageType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }


}
