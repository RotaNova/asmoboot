package com.rotanava.boot.system.api.module.constant;


/**
 * dict状态
 *
 * @author weiqiangmiao
 * @date 2021/11/11
 */
public enum DictStatus {
    //1启用 0不启用

    /**
     * 不启用
     */
    NO(0,"不启用"),


    /**
     * 是
     */
    YES(1,"启用");


    //页面资源删除状态 0-已删除;1-未删除

    private Integer status;

    private String chinese;

    DictStatus(Integer status, String chinese) {
        this.status = status;
        this.chinese = chinese;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Integer findStatus(String chinese) {
        for (DictStatus value : DictStatus.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getStatus();
            }
        }
        return null;
    }

    public static String findStatus(Integer status) {
        for (DictStatus value : DictStatus.values()) {
            if (value.getStatus().equals(status)) {
                return value.getChinese();
            }
        }
        return null;
    }
}
