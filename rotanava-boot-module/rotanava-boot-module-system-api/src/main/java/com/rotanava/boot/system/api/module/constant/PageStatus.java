package com.rotanava.boot.system.api.module.constant;

/**
 * 页面状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-08
 */
public enum PageStatus {
    //页面资源状态:0-未激活:1-正常;2-过期

    /**
     * 未激活
     */
    inactivated(0,"未激活"),
    /**
     * 正常
     */
    normal(1,"正常"),

    /**
     * 过期的
     */
    Expired(2,"过期的");

    //页面资源删除状态 0-已删除;1-未删除

    private Integer status;

    private String chinese;

    PageStatus(Integer status, String chinese) {
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
        for (PageStatus value : PageStatus.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getStatus();
            }
        }
        return null;
    }

    public static String findStatus(Integer status) {
        for (PageStatus value : PageStatus.values()) {
            if (value.getStatus().equals(status)) {
                return value.getChinese();
            }
        }
        return null;
    }
}
