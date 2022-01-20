package com.rotanava.boot.system.api.module.constant;

/**
 * 用户性
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
public enum UserSex {

    //系统用户性别:0-女;1-男;2-不透露

    /**
     * 0-女
     */
    FEMALE(0, "女"),
    /**
     * 1-男
     */
    MALE(1, "男"),
    /**
     * 2-不透露
     */
    DO_NOT_DISCLOSE(2, "不透露");

    public static String getDescByStatus(int type) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (UserSex value : UserSex.values()) {
            //如果遍历获取的type和参数type一致
            if (value.getSex() == type) {
                //返回type对象的desc
                return value.getName();
            }
        }
        return null;
    }

    public static UserSex getUserSexByName(String name) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (UserSex value : UserSex.values()) {
            //如果遍历获取的type和参数type一致
            if (value.getName().equals(name)) {
                //返回type对象的desc
                return value;
            }
        }
        return null;
    }

    private Integer sex;

    private String name;

    UserSex(Integer sex, String name) {
        this.sex = sex;
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }
}
