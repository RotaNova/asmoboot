package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 优先级别
 * @author: jintengzhou
 * @date: 2021-04-06 11:53
 */
public enum AnnPriorityType {

    //优先级别:1-低 2-中 3-高 4-非常高

    /**
     * 1-低
     */
    LOW(1),

    /**
     * 2-中
     */
    MIDDLE(2),

    /**
     * 3-高
     */
    HIGH(3),

    /**
     * 4-非常高
     */
    VERY_HIGH(4);

    private int type;

    AnnPriorityType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    /**
     * 根据code找枚举
     */
    public static AnnPriorityType getAnnPriorityType(int annPriorityType) {
        for (AnnPriorityType e : AnnPriorityType.values()) {
            if (e.getType() == annPriorityType) {
                return e;
            }
        }
        return null;
    }
}