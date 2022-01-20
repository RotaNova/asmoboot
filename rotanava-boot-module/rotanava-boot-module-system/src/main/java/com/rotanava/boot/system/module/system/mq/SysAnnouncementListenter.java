package com.rotanava.boot.system.module.system.mq;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysAnnouncementService;
import com.rotanava.boot.system.api.module.constant.AnnSendStatus;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import com.rotanava.boot.system.module.dao.SysAnnouncementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-23 16:48
 */
@Slf4j
@Component
public class SysAnnouncementListenter {

    /**
     * 发布消息队列
     */
    public static final String PUBLISH_MESSAGE_QUEUE = "publish_message_queue";

    /**
     * 撤销消息队列
     */
    public static final String REVOKE_MESSAGE_QUEUE = "revoke_message_queue";

    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;

    @Autowired
    private SysAnnouncementService sysAnnouncementService;

    /**
     * 功能: 监听消息发布
     * 作者: zjt
     * 日期: 2021/3/23 16:50
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(PUBLISH_MESSAGE_QUEUE))
    public void publishMessageQueueListenter(String message) {
        final SysAnnouncement oldSysAnnouncement = JSONUtil.toBean(message, SysAnnouncement.class);
        final Integer id = oldSysAnnouncement.getId();
        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(id);

        //如果是未发布
        if (sysAnnouncement != null) {
            sysAnnouncementService.publishAnnouncement(Lists.newArrayList(sysAnnouncement.getId()), sysAnnouncement.getUpdateBy());
        }
    }

    /**
     * 功能: 监听消息撤销
     * 作者: zjt
     * 日期: 2021/3/23 16:50
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(REVOKE_MESSAGE_QUEUE))
    public void revokeMessageQueueListenter(String message) {
        final SysAnnouncement oldSysAnnouncement = JSONUtil.toBean(message, SysAnnouncement.class);
        final Integer id = oldSysAnnouncement.getId();
        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(id);

        //如果是未发布
        if (sysAnnouncement != null && sysAnnouncement.getAnnSendStatus().equals(AnnSendStatus.PUBLISHED.getStatus())) {
            sysAnnouncementService.revokeAnnouncement(Lists.newArrayList(sysAnnouncement.getId()), sysAnnouncement.getUpdateBy());
        }
    }

}