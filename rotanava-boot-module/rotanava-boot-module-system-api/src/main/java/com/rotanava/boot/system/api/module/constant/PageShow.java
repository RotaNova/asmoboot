package com.rotanava.boot.system.api.module.constant;


/**
 * 页面显示
 *
 * @author weiqiangmiao
 * @date 2021/10/14
 */
public enum PageShow {

    //页面资源是否可见权限设置:0-不可见:1-可见

    /**
     * 0-不可见
     */
    A_MENU(0,"不可见"),

    /**
     * 1-可见
     */
    SUBMENU(1,"可见"),
    ;


    private Integer show;


    private String chinese;

    PageShow(Integer show, String chinese) {
        this.show = show;
        this.chinese = chinese;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Integer findPageShow(String chinese) {
        for (PageShow value : PageShow.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getShow();
            }
        }
        return null;
    }
    public static String findPageShow(Integer show) {
        for (PageShow value : PageShow.values()) {
            if (value.getShow().equals(show)) {
                return value.getChinese();
            }
        }
        return null;
    }

}

