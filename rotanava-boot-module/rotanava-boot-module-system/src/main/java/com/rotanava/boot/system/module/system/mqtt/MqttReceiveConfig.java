package com.rotanava.boot.system.module.system.mqtt;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rotanava.framework.async.AsyncUtil;
import com.rotanava.framework.async.ThreadPoolUtil;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

@Configuration
@Log4j2
public class MqttReceiveConfig {

    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
    /**
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.producer.clientId}")
    private String producerClientId;

    @Value("${mqtt.consumer.clientId}")
    private String consumerClientId;

    @Autowired
    MqttGateway mqttGateway;

    /**
     * 全局mqtt对象
     */
    private MqttPahoMessageDrivenChannelAdapter adapter;

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{url});
        mqttConnectOptions.setKeepAliveInterval(20);
        return mqttConnectOptions;
    }

    /**
     * MQTT客户端
     *
     * @return {@link MqttPahoClientFactory}
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（生产者）
     *
     * @return {@link MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT消息处理器（生产者）
     *
     * @return {@link MessageHandler}
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                producerClientId + UUID.randomUUID().toString(),
                mqttClientFactory());
        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic(producerDefaultTopic);
        return messageHandler;
    }

    /**
     * MQTT消息订阅绑定（消费者）
     *
     * @return {@link MessageProducer}
     */
    @Bean
    public MessageProducer inbound() {

        // 可以同时消费（订阅）多个Topic
        adapter = new MqttPahoMessageDrivenChannelAdapter(
                consumerClientId + UUID.randomUUID().toString(), mqttClientFactory(),
                "/rotanava/device/deviceInfoReport");


        adapter.setCompletionTimeout(5000);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        //设置转换器，以字节进行传输
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(2);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT信息通道（消费者）
     *
     * @return {@link MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    /**
     * 消息处理
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return message -> {

                ThreadPoolUtil.execute(() -> {
                    try {
                        Object payLoad = message.getPayload();
                        byte[] oArr = (byte[]) payLoad;
                        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                        String messageStr = new String(oArr);
                        log.info("接收到消息，主题是{}  messageStr={}", topic, messageStr);


                        JSONObject json = JSON.parseObject(messageStr);
                        if (json.containsKey("rnMsgId")){

                            AsyncUtil.callMessage(json.getString("rnMsgId"),json);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });

        };
    }


}
