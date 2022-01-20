package com.rotanava.boot.system.api.module.constant;

/**
 * 页面资源类型
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
public enum PageType {

    //页面资源类型：0-一级菜单;1-子菜单;2-接口权限

    /**
     * 0-一级菜单
     */
    A_MENU(0,"一级菜单"),

    /**
     * 1-子菜单
     */
    SUBMENU(1,"子菜单"),

    /**
     * 2-接口权限
     */
    INTERFACE_PERMISSIONS(2,"接口权限");

    private Integer type;

    private String chinese;

    PageType(Integer type, String chinese) {
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
        for (PageType value : PageType.values()) {
            if (value.getChinese().equals(chinese)) {
                return value.getType();
            }
        }
        return null;
    }

    public static String findType(Integer type) {
        for (PageType value : PageType.values()) {
            if (value.getType().equals(type)) {
                return value.getChinese();
            }
        }
        return null;
    }
}
