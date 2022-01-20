package com.rotanava.framework.config.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

/**
 * @description: 异常重试次数消耗完后的恢复器
 * @author: jintengzhou
 * @date: 2020-12-17 11:47
 */
@Log4j2
public class ExceptionMessageRecoverer extends RepublishMessageRecoverer {

    private RabbitTemplate rabbitTemplate;

    private RabbitAdmin rabbitAdmin;

    /**
     * 异常消息重试失败发送的queue
     */
    private String errorRoutingKey;

    public ExceptionMessageRecoverer(RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin, String errorRoutingKey) {
        super(rabbitTemplate, "", errorRoutingKey);
        this.rabbitTemplate = rabbitTemplate;
        this.errorRoutingKey = errorRoutingKey;
        rabbitAdmin.declareQueue(new Queue(errorRoutingKey));
    }

    @Override
    public void recover(Message message, Throwable cause) {
        log.info("该mq消息处理失败超过重试次数 message={} cause={} 转发到 {}队列", message, cause, errorRoutingKey);
        log.error("mq消息处理失败异常", cause);
        super.recover(message, cause);
    }

}