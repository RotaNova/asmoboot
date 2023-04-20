package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * mqtt dto
 *
 * @author weiqiangmiao
 * @date 2022/05/12
 */
@Data
public class MqttSetDTO implements Serializable {

    /**
     * mqtt启用
     */
    @NotNull(message = "mqtt启用不能为空")
    @Range(min = 0, max = 1)
    private Integer mqttEnable;

    /**
     * mqtt服务id
     */
    @NotBlank(message = "mqtt服务id不能为空")
    private String mqttServiceId;

    /**
     * mqtt服务地址
     */
    @NotBlank(message = "mqtt服务地址不能为空")
    private String mqttServiceAddress;

    /**
     * mqtt主机端口
     */
    @NotNull(message = "mqtt主机端口不能为空")
    private Integer mqttHostPort;

    /**
     * mqtt账号
     */
    @NotBlank(message = "mqtt账号不能为空")
    private String mqttAccount;

    /**
     * mqtt密码
     */
//    @NotBlank(message = "mqtt密码不能为空")
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
    @Range(min = 0, max = 1)
    private Integer eventImageEnable;

    /**
     * 背景图启用
     */
    @Range(min = 0, max = 1)
    private Integer backgroundImageEnable;

    /**
     * topic
     */
    @NotBlank(message = "topic不能为空")
    private String topic;

    /**
     * 断网续传
     */
    @NotNull(message = "断网续传不能为空")
    @Range(min = 0, max = 1)
    private Integer disconnectionEnable;

    /**
     * 断网消息留存天数
     */
    @NotNull(message = "断网消息留存不能为空")
    private Integer disconnectionDays;





}
