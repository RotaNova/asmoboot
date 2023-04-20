package com.rotanava.boot.system.api.module.constant;

/**
 * mqtt集
 *
 * @author weiqiangmiao
 * @date 2022/05/18
 */
public enum LdapEnum {
    //


    ldap_address("ldap_address", "LDAP地址", 6, "ldap://192.168.0.166:389",  "LDAP地址"),
    ldap_bind_dn("ldap_bind_dn", "绑定DN", 6, "cn=admin,dc=rotanova,dc=top", "绑定DN"),
    ldap_password("ldap_password", "密码", 6, "ldap123", "密码"),
    ldap_user_ou("ldap_user_ou", "用户OU", 6, "ou=test,dc=rotanova,dc=top|cn=Users,dc=rotanova,dc=top", "用户OU"),
    ldap_user_filter("ldap_user_filter", "用户过滤器", 6, "(uid=%(user)s)", "用户过滤器"),
    ldap_attribute_mapping("ldap_attribute_mapping", "LDAP属性映射", 6, "{}", "LDAP属性映射"),
    ldap_enable_auth("ldap_enable_auth", "启用LDAP认证", 6,  "0", "启用LDAP认证: 0=关闭; 1-开启")
    

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

    LdapEnum(String sysServiceCode, String sysServiceName, Integer sysServiceType, String sysServiceDefaultValue, String sysServiceDescription) {
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

    public static LdapEnum getEnumByCode(String code) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (LdapEnum mqttSet : LdapEnum.values()) {
            //如果遍历获取的type和参数type一致
            if (mqttSet.getSysServiceCode().equals(code)) {
                //返回type对象的desc
                return mqttSet;
            }
        }
        return null;
    }
}
