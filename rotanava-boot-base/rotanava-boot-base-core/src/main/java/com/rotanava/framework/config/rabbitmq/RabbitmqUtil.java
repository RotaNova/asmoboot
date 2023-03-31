package com.rotanava.framework.config.rabbitmq;

import cn.hutool.core.convert.Convert;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-08-27 16:01
 */
@Component
public class RabbitmqUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 功能: 发送延迟消息
     * 作者: zjt
     * 日期: 2021/8/27 16:03
     * 版本: 1.0
     */
    public void sendDelayMessage(long delayTime, String exchange, String routingkey, Object msgBody) {
        //
        if (delayTime > 0) {
            this.rabbitTemplate.convertAndSend(RabbitmqConfig.DELAY_EXCHANGE, RabbitmqConfig.DELAY_ROUTINGKEY, msgBody, message -> {
                final MessageProperties messageProperties = message.getMessageProperties();
                //注意这里时间要是字符串形式
//                messageProperties.setDelay((int) delayTime);
                messageProperties.setExpiration(Convert.toStr(delayTime));
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(RabbitmqConfig.EXCHANGE, exchange);
                messageProperties.setHeader(RabbitmqConfig.ROUTING_KEY, routingkey);
                messageProperties.setHeader(RabbitmqConfig.MSG_BODY, msgBody);
                return message;
            });
        } else {
            this.rabbitTemplate.convertAndSend(exchange, routingkey, msgBody);
        }
    }

}