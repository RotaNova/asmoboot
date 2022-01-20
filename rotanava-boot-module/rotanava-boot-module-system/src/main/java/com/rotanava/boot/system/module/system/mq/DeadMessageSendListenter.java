package com.rotanava.boot.system.module.system.mq;

import cn.hutool.core.convert.Convert;
import com.rotanava.framework.config.rabbitmq.RabbitmqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 发送消息
 * @author: jintengzhou
 * @date: 2021-03-23 16:11
 */
@Slf4j
@Component
public class DeadMessageSendListenter {

    @Autowired
    private MqTransactionalMessageSender mqTransactionalmessageSender;
    

    /**
     * 功能: 监听死信队列中的数据
     * 作者: zjt
     * 日期: 2021/3/23 16:15
     * 版本: 1.0
     */
    @RabbitListener(queues = {RabbitmqConfig.DEAD_LETTER_QUEUE})
    public void handleMessage(Message message) {
        final MessageProperties messageProperties = message.getMessageProperties();
        final String exchange = Convert.toStr(messageProperties.getHeader(RabbitmqConfig.EXCHANGE));
        final String routingKey = Convert.toStr(messageProperties.getHeader(RabbitmqConfig.ROUTING_KEY));
        final String msgBody = Convert.toStr(messageProperties.getHeader(RabbitmqConfig.MSG_BODY));
        mqTransactionalmessageSender.insertMqTransactionalMessage(exchange, routingKey, msgBody, null);
    }
}