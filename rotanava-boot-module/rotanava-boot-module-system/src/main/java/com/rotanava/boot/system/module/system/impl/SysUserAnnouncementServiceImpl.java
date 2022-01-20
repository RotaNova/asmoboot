package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rotanava.boot.system.api.SysUserAnnouncementService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.AnnReadFlag;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncement;
import com.rotanava.boot.system.api.module.system.event.AnnouncementWindowsUnReadNumEvent;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserAnnouncementItemVO;
import com.rotanava.boot.system.module.dao.SysAnnouncementMapper;
import com.rotanava.boot.system.module.dao.SysUserAnnouncementMapper;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.SearchRuleDTO;
import com.rotanava.framework.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-15 18:01
 */
@Slf4j
@Validated
@Service
public class SysUserAnnouncementServiceImpl implements SysUserAnnouncementService {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;
    
    @Autowired
    private SysUserAnnouncementMapper sysUserAnnouncementMapper;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserAnnouncementInfoVO getUserAnnouncementInfo(int sysUserAnnouncementId) {
        final UserAnnouncementInfoVO userAnnouncementInfoVO = new UserAnnouncementInfoVO();
        final SysUserAnnouncement sysUserAnnouncement = sysUserAnnouncementMapper.selectById(sysUserAnnouncementId);
        if (sysUserAnnouncement != null) {
            final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysUserAnnouncement.getSysAnnouncementId());
            if (sysAnnouncement != null) {
                userAnnouncementInfoVO.setSysUserAnnouncementId(sysUserAnnouncement.getId());
                userAnnouncementInfoVO.setAnnCategory(sysAnnouncement.getAnnCategory());
                userAnnouncementInfoVO.setAnnMsgAbstract(sysAnnouncement.getAnnMsgAbstract());
                userAnnouncementInfoVO.setAnnTitle(sysAnnouncement.getAnnTitle());
                userAnnouncementInfoVO.setAnnContent(sysAnnouncement.getAnnContent());
                userAnnouncementInfoVO.setAnnSender(sysUserService.findUserNameById(sysAnnouncement.getAnnSenderId()));
                userAnnouncementInfoVO.setAnnSendTime(Optional.ofNullable(sysAnnouncement.getAnnSendTime()).map(Date::getTime).orElse(null));
                userAnnouncementInfoVO.setAnnReadFlag(sysUserAnnouncement.getAnnReadFlag());
                return userAnnouncementInfoVO;
            }
        }
        return null;
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public IPage<UserAnnouncementItemVO> getUserAnnouncementItemPage(BaseDTO baseDTO, int userId, Integer annCategory, Integer annReadFlag) {
        //发布人系统要转意下
        final List<SearchRuleDTO> queryRuleList = Lists.newCopyOnWriteArrayList(baseDTO.getQueryRuleList() == null ? Lists.newArrayList() : baseDTO.getQueryRuleList());
        AtomicBoolean hasSystemSearch = new AtomicBoolean(false);
        queryRuleList.removeIf(searchRuleDTO -> {
            if ("系统".equals(searchRuleDTO.getValue()) && "user_name".equals(searchRuleDTO.getFiled())) {
                hasSystemSearch.set(true);
                return true;
            }
            return false;
        });
        baseDTO.setQueryRuleList(queryRuleList);
        
        final QueryWrapper<SysUserAnnouncement> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        
        if (hasSystemSearch.get()) {
            queryWrapper.eq("b.ann_sender_id", -1);
        }
        
        
        queryWrapper.eq("a.sys_user_id", userId);
        
        if (annCategory != null) {
            queryWrapper.eq("b.ann_category", annCategory);
        }
        
        if (annReadFlag != null) {
            queryWrapper.eq("a.ann_read_flag", annReadFlag);
        }
        
        queryWrapper.orderBy(true, true, "a.ann_read_flag");
        queryWrapper.orderBy(true, false, "b.ann_send_time");
        queryWrapper.orderBy(true, false, "b.ann_priority");
        
        final IPage<SysUserAnnouncement> queryAnnouncementList = sysUserAnnouncementMapper.queryAnnouncementList(queryWrapper, PageUtils.startPage(baseDTO));
        
        final List<UserAnnouncementItemVO> announcementItem = queryAnnouncementList.getRecords().stream().map(sysUserAnnouncement -> {
            final UserAnnouncementItemVO userAnnouncementItemVO = new UserAnnouncementItemVO();
            userAnnouncementItemVO.setAnnReadFlag(sysUserAnnouncement.getAnnReadFlag());
            final Integer sysAnnouncementId = sysUserAnnouncement.getSysAnnouncementId();
            final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnouncementId);
            if (sysAnnouncement != null) {
                userAnnouncementItemVO.setAnnMsgAbstract(sysAnnouncement.getAnnMsgAbstract());
                userAnnouncementItemVO.setAnnSender(sysUserService.findUserNameById(sysAnnouncement.getAnnSenderId()));
                userAnnouncementItemVO.setAnnSendTime(sysAnnouncement.getAnnSendTime().getTime());
                userAnnouncementItemVO.setAnnTitle(sysAnnouncement.getAnnTitle());
                userAnnouncementItemVO.setSysUserAnnouncementId(sysUserAnnouncement.getId());
                userAnnouncementItemVO.setAnnCategory(sysAnnouncement.getAnnCategory());
            }
            return userAnnouncementItemVO;
        }).collect(Collectors.toList());
        
        return PageUtils.conversionIpageRecords(queryAnnouncementList, UserAnnouncementItemVO.class, announcementItem);
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void readAnnouncement(Collection<Integer> sysUserAnnouncementIdList, int userId) {
        final List<Integer> noticUserIdList = Lists.newArrayList();
        for (Integer sysUserAnnouncementId : sysUserAnnouncementIdList) {
            final SysUserAnnouncement sysUserAnnouncement = sysUserAnnouncementMapper.selectById(sysUserAnnouncementId);
            if (sysUserAnnouncement != null) {
                if (AnnReadFlag.UNREAD.getType() == sysUserAnnouncement.getAnnReadFlag()) {
                    sysUserAnnouncement.setAnnReadFlag(AnnReadFlag.HAVE_READ.getType());
                    sysUserAnnouncement.setAnnReadTime(new Date());
                    noticUserIdList.add(sysUserAnnouncement.getSysUserId());
                    sysUserAnnouncementMapper.updateById(sysUserAnnouncement);
                }
            } else {
                throw new CommonException(DBErrorCode.DB_ERROR_00);
            }
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                //websocket 通知前端
                ThreadPoolUtil.execute(() -> {
                    applicationContext.publishEvent(new AnnouncementWindowsUnReadNumEvent(noticUserIdList));
                });
            }
        });
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void readAllAnnouncement(int userId) {
        final Set<Integer> noticUserIdList = Sets.newHashSet();
        final List<Integer> sysAnnoIdList = sysUserAnnouncementMapper.findSysAnnouncementIdBySysUserId(userId);
        if (!CollectionUtils.isEmpty(sysAnnoIdList)) {
            sysUserAnnouncementMapper.updateAnnReadFlagAndAnnReadTimeBySysUserId(AnnReadFlag.HAVE_READ.getType(), new Date(), userId);
            
            noticUserIdList.addAll(sysUserAnnouncementMapper.findSysUserIdBySysAnnouncementIdIn(sysAnnoIdList));
            
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    //websocket 通知前端
                    ThreadPoolUtil.execute(() -> {
                        applicationContext.publishEvent(new AnnouncementWindowsUnReadNumEvent(noticUserIdList));
                    });
                }
            });
            
        }
    }
    
}