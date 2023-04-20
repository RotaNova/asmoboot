package com.rotanava.boot.system.module.system.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.rotanava.boot.system.api.DingTalkRobotService;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysAnnouncementService;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.constant.AnnDeleteStatus;
import com.rotanava.boot.system.api.module.constant.AnnOpenType;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.AnnReadFlag;
import com.rotanava.boot.system.api.module.constant.AnnSendStatus;
import com.rotanava.boot.system.api.module.constant.AnnTargetType;
import com.rotanava.boot.system.api.module.constant.NoticeType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncementConfig;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncement;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncementConfig;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.SendAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.SendDingTalkDTO;
import com.rotanava.boot.system.api.module.system.dto.SendSysAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.event.AnnouncementWindowsUnReadNumEvent;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementInfoVO;
import com.rotanava.boot.system.module.dao.*;
import com.rotanava.boot.system.module.system.mq.MqTransactionalMessageSender;
import com.rotanava.boot.system.module.system.mq.SysAnnouncementSendListenter;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.boot.system.util.MailUtil;
import com.rotanava.framework.util.socket.PcMessageUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-02 16:48
 */
@Service
@DubboService
@Transactional(rollbackFor = Throwable.class)
@Log4j2
public class SysAnnouncementSenderServiceImpl implements SysAnnouncementSenderService {

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    @Lazy
    private SysAnnouncementMapper sysAnnouncementMapper;

    @Autowired
    private SysUserAnnouncementMapper sysUserAnnouncementMapper;

    @Autowired
    private SysAnnouncementConfigMapper sysAnnouncementConfigMapper;

    @Autowired
    private SysUserAnnouncementConfigMapper sysUserAnnouncementConfigMapper;

    @Autowired
    @Lazy
    private SysUserAnnouncementService sysUserAnnouncementService;

    @Autowired
    @Lazy
    private SysAnnouncementService sysAnnouncementService;


    @Autowired
    private MqTransactionalMessageSender mqTransactionalmessageSender;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private PcMessageUtil pcMessageUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DingTalkRobotService dingTalkRobotService;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void sendAnnouncement(SysAnnConfigIdEnum sysAnnConfigIdEnum, String title, String abstractContent, String content, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType) {
        sendAnnouncement(sysAnnConfigIdEnum.getSysAnnConfigId(), null, title, abstractContent, content, sysUserIds, annPriorityType);
    }

    @Override
    public void sendAnnouncementAsync(SysAnnConfigIdEnum sysAnnConfigIdEnum, String title, String abstractContent, String content, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType) {
        ThreadPoolUtil.execute(() -> {
            sendAnnouncement(sysAnnConfigIdEnum.getSysAnnConfigId(), null, title, abstractContent, content, sysUserIds, annPriorityType);
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void sendAnnouncement(Integer sysAnnConfigId, String title, String abstractContent, String content, AnnPriorityType annPriorityType) {
        ThreadPoolUtil.execute(() -> sendAnnouncement(sysAnnConfigId, null, title, abstractContent, content, null, annPriorityType));

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void sendAnnouncementByHasSysAnnoId(Integer sysAnnConfigId, Integer sysAnnoId, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType) {
        sendAnnouncement(sysAnnConfigId, sysAnnoId, null, null, null, sysUserIds, annPriorityType);
    }



    /**
     * 功能: 发送消息
     * 作者: zjt
     * 日期: 2021/4/6 10:50
     * 版本: 1.0
     */
    private void sendAnnouncement(Integer sysAnnConfigId, Integer sysAnnoId, String title, String abstractContent, String content, Collection<Integer> sysUserIds, AnnPriorityType annPriorityType) {
        final SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(sysAnnConfigId);
        if (sysAnnouncementConfig == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }

        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);

        final AnnCategory annCategory = AnnCategory.getByValue(sysAnnouncementConfig.getType());
        if (annCategory == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }

        final Integer smsNotice = sysAnnouncementConfig.getSmsNotice();
        final Integer sysNotice = sysAnnouncementConfig.getSysNotice();
        final Integer emailNotice = sysAnnouncementConfig.getEmailNotice();
        final Integer phoneNotice = sysAnnouncementConfig.getPhoneNotice();
        final Integer wechatNotice = sysAnnouncementConfig.getWechatNotice();
        final Integer dingTalkNotice = sysAnnouncementConfig.getDingTalkNotice();





        //如果需要发送系统通知 并且有传消息通知id
        if ((sysNotice == NoticeType.NOTICE.getType() || (sysUserIds == null)) && sysAnnoId == null) {
            final AddAnnouncementDTO announcementDTO = new AddAnnouncementDTO();
            announcementDTO.setAnnTitle(title);
            announcementDTO.setAnnContent(content);
            announcementDTO.setAnnMsgAbstract(abstractContent);
            announcementDTO.setAnnOpenType(AnnOpenType.JUMP.getType());
            announcementDTO.setAnnPriority(annPriorityType.getType());
            announcementDTO.setAnnStartTime(System.currentTimeMillis());

            if (sysAnnConfigId <= 3) {
                announcementDTO.setAnnTarget(AnnTargetType.DESIGNATED_USER.getType());
                announcementDTO.setSysUserIdList(sysUserIds);
            } else {
                announcementDTO.setAnnTarget(sysAnnouncementConfig.getAnnTarget());
                announcementDTO.setSysUserIdList(JSON.parseArray(sysAnnouncementConfig.getAnnUserIds(), Integer.class));
                announcementDTO.setSysDeptIdList(JSON.parseArray(sysAnnouncementConfig.getAnnDeptIds(), Integer.class));
            }

            announcementDTO.setAnnConfigId(sysAnnConfigId);
            //默认走这边的发送人都是系统
            sysAnnoId = sysAnnouncementService.addAnnouncement(announcementDTO, -1, annCategory);
            return;
        }

        if (sysAnnoId != null) {
            SysAnnouncement sysAnnouncement1 = sysAnnouncementService.getSysAnnouncement(sysAnnoId);
            title = sysAnnouncement1.getAnnTitle();
            abstractContent = sysAnnouncement1.getAnnMsgAbstract();
            content = sysAnnouncement1.getAnnContent();
            sysUserIds = JSONArray.parseArray(sysAnnouncement1.getAnnUserIds()).toJavaList(Integer.class);
        }

        if (sysAnnoId == null && sysUserIds ==null){
            List<Integer> userIDLIst = JSONArray.parseArray(sysAnnouncementConfig.getAnnUserIds()).toJavaList(Integer.class);
            List<Integer> deptIDLIst = JSONArray.parseArray(sysAnnouncementConfig.getAnnDeptIds()).toJavaList(Integer.class);
            sysUserIds = setUserIds(sysAnnouncementConfig.getAnnTarget(), userIDLIst, deptIDLIst);
        }


        log.info("sysAnnouncementConfig = {}",sysAnnouncementConfig);

        log.info("dingTalkNotice = {}",dingTalkNotice);
        //钉钉机器人
        if (dingTalkNotice == NoticeType.NOTICE.getType()) {

            final SendDingTalkDTO sendDingTalkDTO = new SendDingTalkDTO();
            sendDingTalkDTO.setAnnCategory(annCategory);
            sendDingTalkDTO.setTitle(title);
            sendDingTalkDTO.setAbstractContent(abstractContent);
            sendDingTalkDTO.setContent(content);
            sendDingTalkDTO.setSysAnnConfigId(sysAnnouncementConfig.getId());
            String jsonStr1 = JSONUtil.toJsonStr(sendDingTalkDTO);
            log.info("钉钉推送已经开启，本条消息推送{}",jsonStr1);
            mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_DINGTALK, jsonStr1);
//            dingTalkRobotService.senDdingTalk(sendDingTalkDTO);
        }


        for (Integer sysUserId : sysUserIds) {
            final SysUserAnnouncementConfig sysUserAnnouncementConfig = sysUserAnnouncementConfigMapper.findAllBySysUserIdAndSysAnnConfigId(sysUserId, sysAnnConfigId);
            final Integer userStatus = sysUserMapper.findUserStatusById(sysUserId);
            if (userStatus != null && userStatus.equals(UserStatus.NORMAL.getStatus()) && sysUserAnnouncementConfig == null) {
                final SendAnnouncementDTO sendAnnouncementDTO = new SendAnnouncementDTO();
                sendAnnouncementDTO.setSysUserId(sysUserId);
                sendAnnouncementDTO.setAnnCategory(annCategory);
                sendAnnouncementDTO.setTitle(title);
                sendAnnouncementDTO.setAbstractContent(abstractContent);
                sendAnnouncementDTO.setContent(content);
                final String jsonStr = JSONUtil.toJsonStr(sendAnnouncementDTO);
                //发送短信通知
                if (smsNotice == NoticeType.NOTICE.getType()) {
                    mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_SMS, jsonStr);
                }
                //发送系统通知
                if (sysNotice == NoticeType.NOTICE.getType()) {
                    final SendSysAnnouncementDTO sendSysAnnouncementDTO = new SendSysAnnouncementDTO();


                    sendSysAnnouncementDTO.setSysAnnoId(sysAnnoId);
                    sendSysAnnouncementDTO.setSysUserId(sysUserId);
                    //系统通知要为同步
                    sendSys(sendSysAnnouncementDTO);
//                    mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_SYS, JSONUtil.toJsonStr(sendSysAnnouncementDTO));
                }
                //发送邮箱通知
                if (emailNotice == NoticeType.NOTICE.getType()) {
                    mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_EMAIL, jsonStr);
                }
                //发送电话通知
                if (phoneNotice == NoticeType.NOTICE.getType()) {
                    mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_PHONE, jsonStr);
                }
                //发送微信通知
                if (wechatNotice == NoticeType.NOTICE.getType()) {
                    mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementSendListenter.SEND_WECHAT, jsonStr);
                }



            }
        }
    }


    /**
     * 功能: 获取要保存的用户id
     * 作者: richenli
     * 日期: 2022/3/14 11:07
     * 版本: 1.0
     */
    private Collection<Integer> setUserIds(int annTargetType, Collection<Integer> sysUserIdList, Collection<Integer> sysDeptIdList) {
        if (annTargetType == AnnTargetType.ALL_USER.getType()) {
            sysUserIdList = sysUserMapper.findId();
        } else if (annTargetType == AnnTargetType.DESIGNATED_DEPARTMENT.getType()) {
            sysUserIdList = sysUserDepartmentMapper.findSysUserIdBySysDepartmentIdIn(sysDeptIdList);
        }

//        sysAnnouncement.setAnnTarget(annTargetType);
//        sysAnnouncement.setAnnDeptIds(JSONUtil.toJsonStr(sysDeptIdList));
//        sysAnnouncement.setAnnUserIds(JSONUtil.toJsonStr(sysUserIdList));
        return sysUserIdList;
    }


    /**
     * 功能: 发送短信
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @Override
    public void sendSms(SendAnnouncementDTO sendSysAnnouncementDTO) {
        final String title = sendSysAnnouncementDTO.getTitle();
        final String abstractContent = sendSysAnnouncementDTO.getAbstractContent();
        final String content = sendSysAnnouncementDTO.getContent();


//        SendMsgUtil.send(CommonConstant.PHONE_TEMPLATE_ID, sysUser.getUserSafePhone(), CacheConstant.)
    }

    /**
     * 功能: 发送系统通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @Override
    public void sendSys(SendSysAnnouncementDTO sendSysAnnouncementDTO) {
        final Integer userId = sendSysAnnouncementDTO.getSysUserId();
        final Integer sysAnnoId = sendSysAnnouncementDTO.getSysAnnoId();

        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);
        if (sysAnnouncement != null && !sysAnnouncement.getAnnDelStatus().equals(AnnDeleteStatus.DELETED.getStatus())
                && !sysAnnouncement.getAnnSendStatus().equals(AnnSendStatus.PUBLISHED.getStatus())) {
            sysAnnouncement.setAnnSendStatus(AnnSendStatus.PUBLISHED.getStatus());
            sysAnnouncement.setAnnSendTime(new Date());
            sysAnnouncement.setAnnSenderId(userId);
            sysAnnouncement.setUpdateBy(userId);
            sysAnnouncement.setUpdateTime(new Date());
            sysAnnouncementMapper.updateById(sysAnnouncement);
        }

        final List<Integer> noticUserIdList = JSONUtil.toList(JSONUtil.parseArray(sysAnnouncement.getAnnUserIds()), Integer.class);
        for (Integer sendUserId : noticUserIdList) {
            SysUserAnnouncement sysUserAnnouncement = sysUserAnnouncementMapper.findBySysUserIdAndSysAnnouncementId(sendUserId, sysAnnoId);
            if (sysUserAnnouncement == null) {
                sysUserAnnouncement = new SysUserAnnouncement();
                sysUserAnnouncement.setSysAnnouncementId(sysAnnoId);
                sysUserAnnouncement.setSysUserId(sendUserId);
                sysUserAnnouncement.setAnnReadFlag(AnnReadFlag.UNREAD.getType());
                sysUserAnnouncementMapper.insert(sysUserAnnouncement);
            }
        }


        //websocket 通知前端
        ThreadPoolUtil.execute(() -> {
            applicationContext.publishEvent(new AnnouncementWindowsUnReadNumEvent(noticUserIdList));
        });
    }

    /**
     * 功能: 发送邮箱通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @Override
    public void sendEmail(SendAnnouncementDTO sendSysAnnouncementDTO) throws MessagingException {
        final Integer sysUserId = sendSysAnnouncementDTO.getSysUserId();
        final AnnCategory annCategory = sendSysAnnouncementDTO.getAnnCategory();
        final SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (sysUser != null && StringUtils.isNotBlank(sysUser.getUserSafeEmail())) {
            final String title = String.format("【新航科技】 %s,%s", AnnCategory.getName(annCategory.getType()), sendSysAnnouncementDTO.getTitle());
            final String content = String.format("标题:%s <br>摘要:%s <br> 内容:<br>%s", sendSysAnnouncementDTO.getTitle(),
                    sendSysAnnouncementDTO.getAbstractContent(), sendSysAnnouncementDTO.getContent());
            mailUtil.sendEmail(sysUser.getUserSafeEmail(), title, content);
        }
    }

    /**
     * 功能: 发送电话通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @Override
    public void sendPhone(SendAnnouncementDTO sendSysAnnouncementDTO) {

    }

    /**
     * 功能: 发送微信通知
     * 作者: zjt
     * 日期: 2021/4/2 17:39
     * 版本: 1.0
     */
    @Override
    public void sendWechat(SendAnnouncementDTO sendSysAnnouncementDTO) {

    }

}
