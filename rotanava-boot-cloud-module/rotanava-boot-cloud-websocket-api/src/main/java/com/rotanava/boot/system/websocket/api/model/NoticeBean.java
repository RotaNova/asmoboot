package com.rotanava.boot.system.websocket.api.model;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: mq传递消息通知对象
 * @author: richenLi
 * @create: 2020-09-03 15:07
 **/
@Data
public class NoticeBean implements Serializable {

    //指定要接收的用户列表
    private List<Integer> userIdList;

    //在线的用户全部推送 ，如果该字段为true，userIdList字段没有作用
    private boolean onLineUser;

    //消息来源，用于权限验证 如果userIdList的某个用户不具有该模块权限，则不推送
    private Integer moduleId;

    //推送给客户端的数据包,该对象一定要具有序列化
    private Object data;

    //主题名
    private String topic;

    //发送时间
    private Date sendTime;


    public void setUserIdList(List<Integer> userIdList){
        if (this.userIdList!=null){
            userIdList.addAll(userIdList);
        }else {
            this.userIdList=userIdList;
        }
    }

    /**
     * @description : 添加用户
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    public synchronized void addUser(Integer userId) {
        if (userIdList == null) {
            this.userIdList = new ArrayList<>();
        }
        userIdList.add(userId);
    }

}
