package com.rotanava.boot.system.api.module.system.event;

import org.springframework.context.ApplicationEvent;

import java.util.Collection;

/**
 * @description: 发送websocket 通知事件
 * @author: jintengzhou
 * @date: 2021-04-12 14:48
 */
public class AnnouncementWindowsUnReadNumEvent extends ApplicationEvent {

    public Collection<Integer> getSysUserIdList() {
        return sysUserIdList;
    }

    private Collection<Integer> sysUserIdList;


    public AnnouncementWindowsUnReadNumEvent(Collection<Integer> sysUserIdList) {
        super(sysUserIdList);
        this.sysUserIdList = sysUserIdList;
    }

}