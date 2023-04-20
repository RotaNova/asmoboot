package com.rotanava.boot.system.api.module.constant;

/**
 * mqtt集
 *
 * @author weiqiangmiao
 * @date 2022/05/18
 */
public enum SecurityEnum {
    //


    password_strength("password_strength", "password_strength", 1,"1", "密码强度:1-低;2-中;3-高"),
    account_pass_out_mins("account_pass_out_mins", "密码过期", 1, " 30", "密码过期时间(天)"),
    account_pass_out_on("account_pass_out_on", "密码过期是否开启", 1,  "0", "密码过期是否开启:0-关闭;1-开启"),
    account_lockout_strategy_on("account_lockout_strategy_on", "账户锁定策略是否开启", 1, "0", "账户锁定策略: 0-关闭;1-开启"),
    account_lockout_strategy_frequency("account_lockout_strategy_frequency", "账户锁定策略设置次数", 1,  "5", "账户锁定策略设置次数"),
    account_lockout_strategy_mins("account_lockout_strategy_mins", "账户锁定策略时间", 1, "30", "账户锁定策略时间(分钟)"),
    admin_login_ip_filtering_on("admin_login_ip_filtering_on", "管理员登录IP过滤是否开启", 1, "0", "管理员登录IP过滤是否开启：0-禁止;1-允许"),
    admin_login_ip_filtering("admin_login_ip_filtering", "管理员登录IP过滤", 1,  " ", "管理员登录IP过滤"),
    member_login_ip_filtering_on("member_login_ip_filtering_on", "成员登录IP过滤是否开启", 1,  "0", "成员登录IP过滤是否开启：0-禁止;1-允许"),
    member_login_ip_filtering("member_login_ip_filtering", "成员登录IP过滤", 1, " ", "成员登录IP过滤"),
    last_login_info_prompt_on("last_login_info_prompt_on", "上次登录信息提示开关", 1, "1",  "上次登录信息提示开关:0-关闭;1-开启"),
    single_sign_on("single_sign_on", "单点登录开关", 1, "0", "单点登录开关:0-关闭;1-踢下线;2-禁止登录"),
    pass_mandatory_modification_on("pass_mandatory_modification_on", "初始密码强制修改开关", 1,"0", "初始密码强制修改开关:0-关闭;1-开启"),
    single_login_valid_time("single_login_valid_time", "单次登录有效时间", 1, "5", "单次登录有效时间"),


    ;
    /**
     * 系统服务代码
     */
    private String sysServiceCode;

    /**
     * 系统服务名称
     */
    private String sysServiceName;

    /**
     * 系统服务类型
     */
    private Integer sysServiceType;

    /**
     * 系统服务变量默认值
     */
    private String sysServiceDefaultValue;

    /**
     * 系统服务描述
     */
    private String sysServiceDescription;

    SecurityEnum(String sysServiceCode, String sysServiceName, Integer sysServiceType, String sysServiceDefaultValue, String sysServiceDescription) {
        this.sysServiceCode = sysServiceCode;
        this.sysServiceName = sysServiceName;
        this.sysServiceType = sysServiceType;
        this.sysServiceDefaultValue = sysServiceDefaultValue;
        this.sysServiceDescription = sysServiceDescription;
    }

    public String getSysServiceCode() {
        return sysServiceCode;
    }

    public void setSysServiceCode(String sysServiceCode) {
        this.sysServiceCode = sysServiceCode;
    }

    public String getSysServiceName() {
        return sysServiceName;
    }

    public void setSysServiceName(String sysServiceName) {
        this.sysServiceName = sysServiceName;
    }

    public Integer getSysServiceType() {
        return sysServiceType;
    }

    public void setSysServiceType(Integer sysServiceType) {
        this.sysServiceType = sysServiceType;
    }

    public String getSysServiceDefaultValue() {
        return sysServiceDefaultValue;
    }

    public void setSysServiceDefaultValue(String sysServiceDefaultValue) {
        this.sysServiceDefaultValue = sysServiceDefaultValue;
    }

    public String getSysServiceDescription() {
        return sysServiceDescription;
    }

    public void setSysServiceDescription(String sysServiceDescription) {
        this.sysServiceDescription = sysServiceDescription;
    }

    public static SecurityEnum getEnumByCode(String code) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (SecurityEnum mqttSet : SecurityEnum.values()) {
            //如果遍历获取的type和参数type一致
            if (mqttSet.getSysServiceCode().equals(code)) {
                //返回type对象的desc
                return mqttSet;
            }
        }
        return null;
    }
}
