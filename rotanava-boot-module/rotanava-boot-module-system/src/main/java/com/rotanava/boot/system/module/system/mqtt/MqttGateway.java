package com.rotanava.boot.system.module.system.mqtt;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = MqttReceiveConfig.CHANNEL_NAME_OUT)
public interface MqttGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] data);
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}
