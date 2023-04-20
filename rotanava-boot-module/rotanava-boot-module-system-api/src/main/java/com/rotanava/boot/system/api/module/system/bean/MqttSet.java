package com.rotanava.boot.system.api.module.system.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * mqtt集
 *
 * @author weiqiangmiao
 * @date 2022/05/12
 */
@Data
public class MqttSet implements Serializable {

    /**
     * mqtt启用
     */
    private Integer mqttEnable;

    /**
     * mqtt服务id
     */
    private String mqttServiceId;

    /**
     * mqtt服务地址
     */
    private String mqttServiceAddress;

    /**
     * mqtt主机端口
     */
    private Integer mqttHostPort;

    /**
     * mqtt账号
     */
    private String mqttAccount;

    /**
     * mqtt密码
     */
    private String mqttPassword;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息类型
     */
    private String eventType;

    /**
     * 事件图启用
     */
    private Integer eventImageEnable;

    /**
     * 背景图启用
     */
    private Integer backgroundImageEnable;

    /**
     * topic
     */
    private String topic;

    /**
     * 断网续传
     */
    private Integer disconnectionEnable;

    /**
     * 断网消息留存天数
     */
    private Integer disconnectionDays;




}
