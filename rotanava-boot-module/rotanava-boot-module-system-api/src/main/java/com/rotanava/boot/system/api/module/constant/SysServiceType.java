package com.rotanava.boot.system.api.module.constant;

/**
 * 系统服务类型
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
public enum  SysServiceType {

    /**
     * 安全管理
     */
    SECURITY_MANAGE(1),

    /**
     * ldap
     */
    LDAP(6),

    /**
     * mqtt集
     */
    MQTT_SET(8);

    private Integer type;

    SysServiceType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
