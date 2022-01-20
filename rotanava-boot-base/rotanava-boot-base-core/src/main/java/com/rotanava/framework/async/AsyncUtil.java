package com.rotanava.framework.async;


import com.rotanava.framework.code.ErrorCode;
import jodd.util.ThreadUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 工具类
 * @author: richenLi
 * @create: 2020-11-17 11:50
 **/
@Component
public class AsyncUtil {



//    @Scheduled(cron = "0 0 09,00 * * ?")
    public void clearMsg() {

        Long now = System.currentTimeMillis();
        for (Map.Entry<String, SyncFuture> entry : messageMap.entrySet()) {
            Long beginTime = entry.getValue().getBeginTime();
            Long value = now-beginTime;
            if (value>60000){
                messageMap.remove(entry.getKey());
            }

        }
    }

    //存放同步消息线程 key为msgId，value为线程对象
    private static ConcurrentHashMap<String, SyncFuture> messageMap = new ConcurrentHashMap<>();


    public static SyncFuture creatMessage(String msgId){
        SyncFuture syncFuture  = new SyncFuture();
        creatMessage(msgId,syncFuture);
        return syncFuture;
    }

    /**
     * @description :创建消息回调对象
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public static void creatMessage(String msgId, SyncFuture syncFuture) {
        //后期可以优化做一下限制数量

        messageMap.put(msgId, syncFuture);
    }

    /**
     * @description : 唤醒回调线程
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public static void callMessage(String msgId, Object body) {
        try {
            SyncFuture syncFuture = messageMap.get(msgId);
            if (syncFuture!=null) {
                syncFuture.setResponse(body);
            }
        }finally {
            removeThread(msgId);
        }

    }

    public static void callErrorMessage(String msgId, ErrorCode errorCode) {
        try {
            SyncFuture syncFuture = messageMap.get(msgId);
            if (syncFuture!=null) {
                syncFuture.setErrorResponse(errorCode);
            }
        }finally {
            removeThread(msgId);
        }

    }

    public static void removeThread(String msgId){
        messageMap.remove(msgId);
    }











}
