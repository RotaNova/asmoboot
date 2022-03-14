package com.rotanava.boot.system.websocket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rotanava.boot.system.websocket.api.model.NoticeBean;
import com.rotanava.boot.system.websocket.bean.UserClientBean;
import com.rotanava.boot.system.websocket.config.SocketClientManage;

import com.rotanava.boot.system.websocket.thread.ThreadPoolUtil;
import com.rotanava.face.core.websocket.iface.SocketClient;

import lombok.extern.log4j.Log4j2;

import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 消息通知处理
 * @author: richenLi
 * @create: 2020-09-03 11:14
 **/
@Component
@Log4j2
public class MessageEventHandler {
    public static final String LOGINUSERIDS = "loginUserIds";


    @Autowired
    RemainPageService remainPageService;


    @Autowired
    private RedissonClient redissonClient;


    /**
     * @description : 接收mq传递过来的通知消息，这边处理是否要进行通知
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @RabbitListener(queuesToDeclare = @Queue("BOOT_WEBSOCKET_MESSAGE"))
    public void consumeMessage(NoticeBean noticeBean) {

        log.info("【接收到mq推送消息{}】", noticeBean);


        Long sendDate = noticeBean.getSendTime().getTime();
        Long nowDate = System.currentTimeMillis();
        Long difference = (nowDate - sendDate) / 1000;
        if (difference > 60) {
            //丢弃该数据
            log.info("websocket推送消息时间大于60m，丢弃该信息，noticeBean : {}", noticeBean);
            return;
        }


        asyncSendEvent(noticeBean);


    }



    /**
     * @description : 异步推送消息
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private void asyncSendEvent(NoticeBean noticeBean) {

        ThreadPoolUtil.execute(() -> {
            List<String> allClient = getNoticeUserListByNoticeBean(noticeBean);
            Integer pageId = noticeBean.getModuleId();
            for (String token : allClient) {
                if (!SocketClientManage.containsToken(token)) {
                    log.info("用户不在线，不发送 token :{}", token);
                }
                //判断用户是否在这个页面上，如果moduleId是0的话，是全部推送，否则要以用户是否在页面来决定是否推送
                SocketClient client = SocketClientManage.get(token);
                if (noticeBean.getModuleId() != 0) {
                    if (!remainPageService.inPageByToken(pageId, token)) {
                        log.info("用户不在该页面，不发");
                        continue;
                    }
                    client.send(noticeBean.getTopic(), noticeBean.getData());
                    log.info("推送发送socket消息,内容为{}", noticeBean);
                } else {
                    client.send(noticeBean.getTopic(), noticeBean.getData());
                    log.info("全部推送socket消息,内容为{}", noticeBean);
                }

            }
        });


    }


    /**
     * @description : 根据noticeBean的内容，获取要推送的在线用户名单
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private List<String> getNoticeUserListByNoticeBean(NoticeBean noticeBean) {
        List<String> allClient = new ArrayList<>();
        if (noticeBean.isOnLineUser()) {
            //发送给所有在线的用户
            allClient = SocketClientManage.getAllToken();
        } else {
            for (Integer userId : noticeBean.getUserIdList()) {
                Set<String> userTokens = getLoginUserTokens(userId);
                for (String token : userTokens) {
                    if (SocketClientManage.containsToken(token)) {
                        allClient.add(token);
                    }
                }
            }
        }
        return allClient;
    }

    public static Integer getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asInt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @description : 根据userId，获取到多个token
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private Set<String> getLoginUserTokens(Integer userId) {

        Set<String> userClientById = SocketClientManage.getUserClientById(userId.toString());
        return userClientById;

//        //扫描符合条件的token scan 来扫 要去重
//        RKeys keys = redissonClient.getKeys();
//        String pattern = String.format("%s:%s*", "loginUserIds", userId);
//        Set<String> tokens = Sets.newHashSet();
//
//        Set<String> loginTokens = keys.getKeysStreamByPattern(pattern, Integer.MAX_VALUE).collect(Collectors.toSet());
//        loginTokens.forEach(token -> {
//            tokens.add(token.substring(token.lastIndexOf(":") + 1));
//        });
//        return tokens;
    }


}
