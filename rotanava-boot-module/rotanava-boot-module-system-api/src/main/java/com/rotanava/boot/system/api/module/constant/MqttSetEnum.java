package com.rotanava.boot.system.api.module.constant;

/**
 * mqtt集
 *
 * @author weiqiangmiao
 * @date 2022/05/18
 */
public enum MqttSetEnum {
    //

    mqtt_enable("mqtt_enable", "mqtt启用 0-关闭 1-启用", 8, "0", "mqtt启用 0-关闭 1-启用"),
    mqtt_service_id("mqtt_service_id", "mqtt服务id", 8, "rotanava_event", "mqtt服务id"),
    mqtt_service_address("mqtt_service_address", "mqtt服务地址", 8, "ws://db.api.rotanova.top", "mqtt服务地址"),
    mqtt_host_port("mqtt_host_port", "mqtt主机端口", 8, "8083", "mqtt主机端口"),
    mqtt_account("mqtt_account", "mqtt账号", 8, "admin", "mqtt账号"),
    mqtt_password("mqtt_password", "mqtt密码", 8, "RN2020.", "mqtt密码"),
    message_type("message_type", "消息类型", 8, "[]", "消息类型"),
    event_type("event_type", "消息类型", 8, "[]", "消息类型"),
    event_image_enable("event_image_enable", "事件图启用 0-关闭 1-启用", 8, "0", "事件图启用 0-关闭 1-启用"),
    background_image_enable("background_image_enable", "背景图启用 0-关闭 1-启用", 8, "0", "背景图启用 0-关闭 1-启用"),
    topic("topic", "topic", 8, "protection", "topic"),
    disconnection_enable("disconnection_enable", "断网续传", 8, "0", "断网续传"),
    disconnection_days("disconnection_days", "断网消息留存天数", 8, "1", "断网消息留存天数");

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

    MqttSetEnum(String sysServiceCode, String sysServiceName, Integer sysServiceType, String sysServiceDefaultValue, String sysServiceDescription) {
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

    public static MqttSetEnum getEnumByCode(String code) {
        //获取到枚举
        //加强for循环进行遍历操作
        for (MqttSetEnum mqttSet : MqttSetEnum.values()) {
            //如果遍历获取的type和参数type一致
            if (mqttSet.getSysServiceCode().equals(code)) {
                //返回type对象的desc
                return mqttSet;
            }
        }
        return null;
    }
}
