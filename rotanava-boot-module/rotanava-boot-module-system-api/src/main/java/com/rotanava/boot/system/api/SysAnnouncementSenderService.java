package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.system.dto.SendAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.SendSysAnnouncementDTO;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @description: 消息发送工具
 * @author: jintengzhou
 * @date: 2021-04-02 16:42
 */
@Validated
public interface SysAnnouncementSenderService {

    /**
     * 功能: 发送消息给指定的人
     * 作者: zjt
     * 日期: 2021/4/2 16:47
     * 版本: 1.0
     */
    void sendAnnouncement(SysAnnConfigIdEnum sysAnnConfigIdEnum, @NotBlank @Size(max = 66) String title,
                          @Size(max = 66) String abstractContent, String content, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType);

    /**
     * 功能: 发送消息给指定的人
     * 作者: zjt
     * 日期: 2021/4/2 16:47
     * 版本: 1.0
     */
    void sendAnnouncementAsync(SysAnnConfigIdEnum sysAnnConfigIdEnum, @NotBlank @Size(max = 66) String title,
                          @Size(max = 66) String abstractContent, String content, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType);

    /**
     * 功能: 发送消息 根据消息配置发送给相关的人
     * 作者: zjt
     * 日期: 2021/4/2 16:47
     * 版本: 1.0
     */
    void sendAnnouncement(Integer sysAnnConfigId, @NotBlank @Size(max = 66) String title,
                          @Size(max = 66) String abstractContent, String content, AnnPriorityType annPriorityType);


    /**
     * 功能: 发送消息 关联已经有的消息
     * 作者: zjt
     * 日期: 2021/4/2 16:47
     * 版本: 1.0
     */
    void sendAnnouncementByHasSysAnnoId(SysAnnConfigIdEnum sysAnnConfigIdEnum, Integer sysAnnoId, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType);


    /**
     * 功能: 发送短信
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    void sendSms(@Validated SendAnnouncementDTO sysAnnConfigIdEnum);

    /**
     * 功能: 发送系统通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    void sendSys(@Validated SendSysAnnouncementDTO sendSysAnnouncementDTO);

    /**
     * 功能: 发送邮箱通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    void sendEmail(@Validated SendAnnouncementDTO sysAnnConfigIdEnum) throws MessagingException;

    /**
     * 功能: 发送电话通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    void sendPhone(@Validated SendAnnouncementDTO sysAnnConfigIdEnum);

    /**
     * 功能: 发送微信通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    void sendWechat(@Validated SendAnnouncementDTO sysAnnConfigIdEnum);
}