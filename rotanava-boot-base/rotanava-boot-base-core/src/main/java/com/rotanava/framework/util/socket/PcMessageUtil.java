package com.rotanava.framework.util.socket;

import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.websocket.api.model.NoticeBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @description: pc端推送websocket工具类
 * @author: richenLi
 * @create: 2020-09-03 15:27
 **/
@Component
public class PcMessageUtil {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public static final String MESSAGE_QUEUE = "BOOT_WEBSOCKET_MESSAGE";

    /**
     * @description : 投递消息到mq
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private void sendMessage(NoticeBean noticeBean) {
        noticeBean.setSendTime(new Date());
        noticeBean.setData(JSONObject.toJSON(noticeBean.getData()));
        //发送mq消息
        rabbitTemplate.convertAndSend(MESSAGE_QUEUE, noticeBean);
    }


    /**
     * @description : 指定用户发送消息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByUserId(Integer userId, String topic, Object data, Integer moduleId) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.addUser(userId);
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(moduleId);
        noticeBean.setOnLineUser(false);
        sendMessage(noticeBean);
    }


    /**
     * @description : 指定用户发送消息 全部页面推送
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByUserId(Integer userId, String topic, Object data) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.addUser(userId);
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(0);
        sendMessage(noticeBean);
    }

    /**
     * @description : 指定多个用户发送消息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByUserList(List<Integer> userList, String topic, Object data) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setUserIdList(userList);
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(0);
        sendMessage(noticeBean);
    }

    /**
     * @description : 指定多个用户发送消息 全部页面推送
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByUserList(List<Integer> userList, String topic, Object data, Integer moduleId) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setUserIdList(userList);
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(moduleId);
        sendMessage(noticeBean);
    }

    /**
     * @description : 向所有在线的用户发消息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByOnLineUser(String topic, Object data, Integer moduleId) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(moduleId);
        noticeBean.setOnLineUser(true);
        sendMessage(noticeBean);
    }


    /**
     * @description : 向所有在线的用户发消息，全部页面推送
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public void sendMessageByOnLineUser(String topic, Object data) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setTopic(topic);
        noticeBean.setData(data);
        noticeBean.setModuleId(0);
        noticeBean.setOnLineUser(true);
        sendMessage(noticeBean);
    }


}
