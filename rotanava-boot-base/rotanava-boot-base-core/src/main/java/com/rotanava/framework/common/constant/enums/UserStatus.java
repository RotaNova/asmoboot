package com.rotanava.framework.common.constant.enums;


/**
 * 用户删除状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
public enum UserStatus {

    //系统用户状态:0-未激活;1-正常:2-冻结:3-过期

    /**
     * 0-未激活
     */
    INACTIVATED(0, "未激活"),

    /**
     * 1-正常
     */
    NORMAL(1, "正常"),

    /**
     * 2-冻结
     */
    FREEZE(2, "冻结"),

    /**
     * 3-过期
     */
    EXPIRED(3, "过期");

    private Integer status;
    private String desc;

    UserStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 得到事件desc类型
     *
     * @param status 状态
     * @return {@link String }
     * @author WeiQiangMiao
     * @date 2020-12-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static String getDescByStatus(int status) {
        //获取到枚举
        UserStatus[] values = UserStatus.values();
        //加强for循环进行遍历操作
        for (UserStatus value : values) {
            //如果遍历获取的type和参数type一致
            if (value.getStatus() == status) {
                //返回type对象的desc
                return value.getDesc();
            }
        }
        return null;
    }


    /**
     * 得到结果枚举类型
     *
     * @param type 类型
     * @return {@link UserStatus }
     * @author WeiQiangMiao
     * @date 2020-12-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static UserStatus getEnumByStatus(int type) {
        //获取到枚举
        UserStatus[] values = UserStatus.values();
        for (UserStatus value : values) {
            if (value.getStatus() == type) {
                return value;
            }
        }
        return null;
    }
}