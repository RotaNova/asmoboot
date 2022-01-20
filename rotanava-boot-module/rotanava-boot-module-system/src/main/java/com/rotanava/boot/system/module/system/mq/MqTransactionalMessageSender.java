package com.rotanava.boot.system.module.system.mq;

import com.rotanava.boot.system.api.module.system.bo.MqTransactionalMessage;
import com.rotanava.boot.system.module.dao.MqTransactionalMessageMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @description: mq本地事务表 会定时扫描改表 取出未发送的消息进行消费
 * @author: jintengzhou
 * @date: 2021-03-22 11:51
 */
@Log4j2
@Service
@Transactional(rollbackFor = Throwable.class)
public class MqTransactionalMessageSender {

    @Autowired
    private MqTransactionalMessageMapper mqTransactionalMessageMapper;

    /**
     * 功能: 投递消息
     * 作者: zjt
     * 日期: 2021/3/22 14:56
     * 版本: 1.0
     */
    public void insertMqTransactionalMessage(String routingKey, String msgBody) {
        insertMqTransactionalMessage("", routingKey, msgBody, null);
    }

    /**
     * 功能: 投递延迟消息  delayTime小于0 直接发
     * 作者: zjt
     * 日期: 2021/3/22 14:56
     * 版本: 1.0
     */
    public void insertMqTransactionalMessage(String queue, String msgBody, long delayTime) {
        insertMqTransactionalMessage("", queue, msgBody, delayTime);
    }

    public void insertMqTransactionalMessage(String exchange, String routingKey, String msgBody, Long delayTime) {
        final MqTransactionalMessage mqTransactionalMessage = new MqTransactionalMessage();
        mqTransactionalMessage.setCreateTime(new Date());
        mqTransactionalMessage.setExchange(StringUtils.isBlank(exchange) ? "" : exchange);
        mqTransactionalMessage.setMsgBody(msgBody);
        mqTransactionalMessage.setRoutingkey(routingKey);
        mqTransactionalMessage.setDelayTime(delayTime);
        //0为未发送 1为发送
        mqTransactionalMessage.setStatus(0);
        mqTransactionalMessageMapper.insert(mqTransactionalMessage);
    }


}