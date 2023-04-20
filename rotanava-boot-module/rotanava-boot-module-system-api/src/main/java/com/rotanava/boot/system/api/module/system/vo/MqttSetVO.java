package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * mqtt集
 *
 * @author weiqiangmiao
 * @date 2022/05/12
 */
@Data
public class MqttSetVO implements Serializable {

    /**
     * mqtt启用
     */
    @Dict(dicCode = "mqtt_enable")
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
    private List<Integer> messageType;

    /**
     * 消息类型
     */
    private List<String> eventType;

    /**
     * 事件图启用
     */
    @Dict(dicCode = "mqtt_enable")
    private Integer eventImageEnable;

    /**
     * 背景图启用
     */
    @Dict(dicCode = "mqtt_enable")
    private Integer backgroundImageEnable;

    /**
     * topic
     */
    private String topic;

    /**
     * 断网续传
     */
    @Dict(dicCode = "mqtt_enable")
    private Integer disconnectionEnable;

    /**
     * 断网消息留存天数
     */
    private Integer disconnectionDays;

    /**
     * 连接状态
     */
    @Dict(dicCode = "connection_status")
    private Integer connectionStatus;




}
