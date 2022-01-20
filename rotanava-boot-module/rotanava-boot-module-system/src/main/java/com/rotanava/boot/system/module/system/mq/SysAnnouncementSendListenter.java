package com.rotanava.boot.system.module.system.mq;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.module.constant.AnnReadFlag;
import com.rotanava.boot.system.api.module.system.dto.SendAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.event.AnnouncementWindowsUnReadNumEvent;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.util.socket.PcMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-23 16:11
 */
@Slf4j
@Component
public class SysAnnouncementSendListenter {

    public static final String SEND_SMS = "sendSms";
    public static final String SEND_SYS = "sendSys";
    public static final String SEND_EMAIL = "sendEmail";
    public static final String SEND_PHONE = "sendPhone";
    public static final String SEND_WECHAT = "sendWechat";

    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    @Autowired
    private SysUserAnnouncementService sysUserAnnouncementService;

    @Autowired
    private PcMessageUtil pcMessageUtil;

    /**
     * 功能: 监听是否需要发送websocket事件
     * 作者: zjt
     * 日期: 2021/4/12 14:51
     * 版本: 1.0
     */
    @EventListener
    public void handleAnnouncementWindowsUnReadNumEvent(AnnouncementWindowsUnReadNumEvent announcementWindowsUnReadNumEvent) {
        for (Integer sysUserId : announcementWindowsUnReadNumEvent.getSysUserIdList()) {
            //websocket 通知前端
            //计算未读的总条数
            final BaseDTO baseDTO = new BaseDTO();
            baseDTO.setPageNum(1);
            baseDTO.setPageSize(1);
            final IPage<UserAnnouncementItemVO> userAnnouncementItemPage =
                    sysUserAnnouncementService.getUserAnnouncementItemPage(baseDTO, sysUserId, null, AnnReadFlag.UNREAD.getType());
            pcMessageUtil.sendMessageByUserId(sysUserId, "announcementWindowsUnReadNum", userAnnouncementItemPage.getTotal());
        }
    }

    /**
     * 功能: 发送短信
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(SEND_SMS))
    public void sendSms(String message) {
        final SendAnnouncementDTO sendAnnouncementDTO = JSONUtil.toBean(message, SendAnnouncementDTO.class);
        sysAnnouncementSenderService.sendSms(sendAnnouncementDTO);
    }

//    /**
//     * 功能: 发送系统通知
//     * 作者: zjt
//     * 日期: 2021/4/2 17:39
//     * 版本: 1.0
//     */
//    @RabbitListener(queuesToDeclare = @Queue(SEND_SYS))
//    public void sendSys(String message) {
//        final SendSysAnnouncementDTO sendSysAnnouncementDTO = JSONUtil.toBean(message, SendSysAnnouncementDTO.class);
//        sysAnnouncementSenderService.sendSys(sendSysAnnouncementDTO);
//    }

    /**
     * 功能: 发送邮箱通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(SEND_EMAIL))
    public void sendEmail(String message) throws MessagingException {
        final SendAnnouncementDTO sendAnnouncementDTO = JSONUtil.toBean(message, SendAnnouncementDTO.class);
        sysAnnouncementSenderService.sendEmail(sendAnnouncementDTO);
    }

    /**
     * 功能: 发送电话通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(SEND_PHONE))
    public void sendPhone(String message) {
        final SendAnnouncementDTO sendAnnouncementDTO = JSONUtil.toBean(message, SendAnnouncementDTO.class);
        sysAnnouncementSenderService.sendPhone(sendAnnouncementDTO);
    }

    /**
     * 功能: 发送微信通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(SEND_WECHAT))
    public void sendWechat(String message) {
        final SendAnnouncementDTO sendAnnouncementDTO = JSONUtil.toBean(message, SendAnnouncementDTO.class);
        sysAnnouncementSenderService.sendWechat(sendAnnouncementDTO);
    }
}