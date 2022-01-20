package com.rotanava.boot.system.api.module.constant;

/**
 * 用户系统角色
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
public enum UserSysRole {

    //系统用户的账号身份:0-成员;1-管理员

    /**
     * 0-成员
     */
    MEMBER(0, "成员"),

    /**
     * 1-管理员
     */
    ADMINISTRATOR(1, "管理员");


    public static String getDescByStatus(int type) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (UserSysRole value : UserSysRole.values()) {
            //如果遍历获取的type和参数type一致
            if (value.getType() == type) {
                //返回type对象的desc
                return value.getName();
            }
        }
        return null;
    }

    private Integer type;

    private String name;

    UserSysRole(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
