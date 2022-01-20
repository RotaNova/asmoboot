package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 消息通知配置枚举
 * @author: jintengzhou
 * @date: 2021-04-02 16:44
 */
public enum SysAnnConfigIdEnum {

    /**
     * 通知消息
     */
    SYSANNCONFIGID_1(1),

    /**
     * 系统消息
     */
    SYSANNCONFIGID_2(2),

    /**
     * 告警消息
     */
    SYSANNCONFIGID_3(3)
    ;

    /**
     * 消息通知配置  sys_announcement_config表的id
     */
    private final Integer sysAnnConfigId;

    SysAnnConfigIdEnum(Integer sysAnnConfigId) {
        this.sysAnnConfigId = sysAnnConfigId;
    }

    public Integer getSysAnnConfigId() {
        return sysAnnConfigId;
    }


}
