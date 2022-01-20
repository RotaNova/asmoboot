package com.rotanava.boot.system.api.module.constant;

import com.rotanava.framework.util.StringUtil;

/**
 * @description: 系统通告类型:0-通知消息;1-系统消息;2-告警消息
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum AnnCategory {

    //系统通告类型:0-通知消息;1-系统消息;2-告警消息

    /**
     * 0-通知消息
     */
    NOTIFICATION_MESSAGE(0, "通知消息"),

    /**
     * 1-系统消息
     */
    SYSTEM_INFORMATION(1, "系统消息"),

    /**
     * 2-告警消息
     */
    WARNING_MESSAGE(2, "告警消息");


    public static AnnCategory getByValue(int value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return null;
        }
        for (AnnCategory val : values()) {
            if (value == val.getType()) {
                return val;
            }
        }
        return null;
    }

    public static String getName(int value) {
        final AnnCategory annCategory = getByValue(value);
        if (annCategory == null) {
            return "";
        }

        return annCategory.getName();
    }



    private int type;

    private String name;

    AnnCategory(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}