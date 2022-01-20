package com.rotanava.boot.system.module.system.task;


import cn.hutool.core.convert.Convert;
import com.rotanava.boot.system.api.module.system.bo.MqTransactionalMessage;
import com.rotanava.boot.system.module.dao.MqTransactionalMessageMapper;
import com.rotanava.framework.config.rabbitmq.RabbitmqConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-22 15:49
 */
@Slf4j
@Component
public class MqTransactionalTask implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MqTransactionalMessageMapper mqTransactionalMessageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    static class MqTransactionalStatus {
        //0为未发送 1为发送
        public static final int NOT_SEND = 0;
        public static final int SEND = 1;
    }

    /**
     * 功能: 删除已经发送mq表里的数据
     * 作者: zjt
     * 日期: 2021/4/13 10:02
     * 版本: 1.0
     */
    @XxlJob(value = "deleteMqTransactionalTask")
    public ReturnT<String> deleteMqTransactionalTask(String data) throws Exception {
        mqTransactionalMessageMapper.deleteByStatus(MqTransactionalStatus.SEND);
        return ReturnT.SUCCESS;
    }

    /**
     * 功能: 发送本地mq表里的数据到mq
     * 作者: zjt
     * 日期: 2021/3/22 17:55
     * 版本: 1.0
     */
    @XxlJob(value = "MqTransactionalTask")
    public ReturnT<String> mqTransactionalTask(String data) throws Exception {

        //0为未发送 1为发送
        final List<MqTransactionalMessage> mqTransactionalMessageList = mqTransactionalMessageMapper.findAllByStatus(MqTransactionalStatus.NOT_SEND);

        for (MqTransactionalMessage mqTransactionalMessage : mqTransactionalMessageList) {
            final Integer mqTransactionalMessageId = mqTransactionalMessage.getId();
            // 注册回调函数
            this.rabbitTemplate.setConfirmCallback(this);
            this.rabbitTemplate.setMandatory(true);

            final String exchange = mqTransactionalMessage.getExchange();
            final String routingkey = mqTransactionalMessage.getRoutingkey();
            final String msgBody = mqTransactionalMessage.getMsgBody();
            final Long delayTime = mqTransactionalMessage.getDelayTime();

            final CorrelationData correlationData = new CorrelationData();
            correlationData.setId(Convert.toStr(mqTransactionalMessageId));

            //如果还没发送就发送
            log.info("开始发送本地消息表里面的数据 mqTransactionalMessage={}", mqTransactionalMessage);

            if (delayTime != null && delayTime > 0) {
                this.rabbitTemplate.convertAndSend(RabbitmqConfig.DELAY_EXCHANGE, RabbitmqConfig.DELAY_ROUTINGKEY, msgBody, message -> {
                    final MessageProperties messageProperties = message.getMessageProperties();
                    //注意这里时间要是字符串形式
                    messageProperties.setExpiration(Convert.toStr(delayTime));
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(RabbitmqConfig.EXCHANGE, mqTransactionalMessage.getExchange());
                    messageProperties.setHeader(RabbitmqConfig.ROUTING_KEY, mqTransactionalMessage.getRoutingkey());
                    messageProperties.setHeader(RabbitmqConfig.MSG_BODY, msgBody);
                    return message;
                }, correlationData);
            } else {
                this.rabbitTemplate.convertAndSend(exchange, routingkey, msgBody, correlationData);
            }
        }

        return ReturnT.SUCCESS;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData == null) {
            return;
        }

        // 消息发送成功了
        if (ack) {
            // 更改消息表的状态为已发送
            String mqTransactionalMessageId = correlationData.getId();

            final MqTransactionalMessage mqTransactionalMessage = new MqTransactionalMessage();
            mqTransactionalMessage.setId(Convert.toInt(mqTransactionalMessageId));
            //将消息状态改为1 已发送
            mqTransactionalMessage.setStatus(1);
            mqTransactionalMessageMapper.updateById(mqTransactionalMessage);
        } else {
            log.error("mq本地事务表确认失败 ack={} correlationData={} cause={}", ack, correlationData, cause);
        }
    }

}