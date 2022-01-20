package com.rotanava.boot.system.module.system.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysAnnouncementService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.constant.AnnDeleteStatus;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.AnnSendStatus;
import com.rotanava.boot.system.api.module.constant.AnnTargetType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysUserLabel;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import com.rotanava.boot.system.api.module.system.dto.AddAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAnnouncementItemPageDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateAnnouncementDTO;
import com.rotanava.boot.system.api.module.system.event.AnnouncementWindowsUnReadNumEvent;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementItemVO;
import com.rotanava.boot.system.module.dao.SysAnnouncementMapper;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserAnnouncementMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.boot.system.module.system.mq.MqTransactionalMessageSender;
import com.rotanava.boot.system.module.system.mq.SysAnnouncementListenter;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-11 10:16
 */
@Service
public class SysAnnouncementServiceImpl implements SysAnnouncementService {
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;
    
    @Autowired
    private SysAnnouncementMapper sysAnnouncementMapper;
    
    @Autowired
    private SysUserAnnouncementMapper sysUserAnnouncementMapper;
    
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    
    @Autowired
    private MqTransactionalMessageSender mqTransactionalmessageSender;
    
    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer addAnnouncement(AddAnnouncementDTO announcementDTO, int userId, AnnCategory annCategory) {
        final SysAnnouncement sysAnnouncement = new SysAnnouncement();
        sysAnnouncement.setAnnTitle(announcementDTO.getAnnTitle());
        sysAnnouncement.setAnnContent(announcementDTO.getAnnContent());
        sysAnnouncement.setAnnMsgAbstract(announcementDTO.getAnnMsgAbstract());
        sysAnnouncement.setAnnStartTime(new Date(announcementDTO.getAnnStartTime()));
        sysAnnouncement.setAnnEndTime(Optional.ofNullable(announcementDTO.getAnnEndTime()).map(Date::new).orElse(null));
        sysAnnouncement.setAnnSenderId(userId);
        sysAnnouncement.setAnnPriority(announcementDTO.getAnnPriority());
        sysAnnouncement.setAnnCategory(annCategory.getType());
        sysAnnouncement.setAnnOpenType(announcementDTO.getAnnOpenType());
        sysAnnouncement.setAnnTarget(announcementDTO.getAnnTarget());
        sysAnnouncement.setAnnDelStatus(AnnDeleteStatus.NOT_DELETED.getStatus());
        sysAnnouncement.setAnnSendStatus(AnnSendStatus.UNPUBLISHED.getStatus());
        sysAnnouncement.setAnnConfigId(announcementDTO.getAnnConfigId());
        
        setUserIds(announcementDTO.getAnnTarget(), sysAnnouncement, announcementDTO.getSysUserIdList(), announcementDTO.getSysDeptIdList());
        
        sysAnnouncement.setCreateBy(userId);
        sysAnnouncement.setCreateTime(new Date());
        sysAnnouncement.setUpdateBy(userId);
        sysAnnouncement.setUpdateTime(new Date());
        
        checkParams(null, sysAnnouncement);
        sysAnnouncementMapper.insert(sysAnnouncement);
        dealMessage(sysAnnouncement);
        
        return sysAnnouncement.getId();
    }
    
    /**
     * 功能: 发送通知/撤销通知
     * 作者: zjt
     * 日期: 2021/3/23 17:35
     * 版本: 1.0
     */
    private void dealMessage(SysAnnouncement sysAnnouncement) {
        //发送通知
        mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementListenter.PUBLISH_MESSAGE_QUEUE,
                JSONUtil.toJsonStr(sysAnnouncement), Math.max(0, sysAnnouncement.getAnnStartTime().getTime() - System.currentTimeMillis()));
        //撤销通知
        if (sysAnnouncement.getAnnEndTime() != null) {
            mqTransactionalmessageSender.insertMqTransactionalMessage(SysAnnouncementListenter.REVOKE_MESSAGE_QUEUE,
                    JSONUtil.toJsonStr(sysAnnouncement), Math.max(0, sysAnnouncement.getAnnEndTime().getTime() - System.currentTimeMillis()));
        }
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateAnnouncement(UpdateAnnouncementDTO announcementDTO, int userId) {
        final Integer sysAnnoId = announcementDTO.getSysAnnoId();
        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);
        if (sysAnnouncement != null) {
            
            if (sysAnnouncement.getAnnSendStatus().equals(AnnSendStatus.PUBLISHED.getStatus())) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_33);
            }
            
            sysAnnouncement.setAnnTitle(announcementDTO.getAnnTitle());
            sysAnnouncement.setAnnMsgAbstract(announcementDTO.getAnnMsgAbstract());
            sysAnnouncement.setAnnContent(announcementDTO.getAnnContent());
            sysAnnouncement.setAnnTarget(announcementDTO.getAnnTarget());
            
            sysAnnouncement.setAnnOpenType(announcementDTO.getAnnOpenType());
            sysAnnouncement.setAnnStartTime(new Date(announcementDTO.getAnnStartTime()));
            if (announcementDTO.getAnnEndTime() == null) {
                sysAnnouncementMapper.updateAnnEndNullTimeById(sysAnnoId);
                sysAnnouncement.setAnnEndTime(null);
            } else {
                sysAnnouncement.setAnnEndTime(new Date(announcementDTO.getAnnEndTime()));
            }
            
            sysAnnouncement.setAnnPriority(announcementDTO.getAnnPriority());
            sysAnnouncement.setUpdateTime(new Date());
            sysAnnouncement.setUpdateBy(userId);
            
            setUserIds(announcementDTO.getAnnTarget(), sysAnnouncement, announcementDTO.getSysUserIdList(), announcementDTO.getSysDeptIdList());
            
            checkParams(sysAnnoId, sysAnnouncement);
            sysAnnouncementMapper.updateById(sysAnnouncement);
            //发送通知
            dealMessage(sysAnnouncement);
        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }
    
    /**
     * 功能: 检查参数
     * 作者: zjt
     * 日期: 2021/3/20 11:58
     * 版本: 1.0
     */
    private void checkParams(Integer sysAnnId, SysAnnouncement sysAnnouncement) {
        
        final Date annStartTime = sysAnnouncement.getAnnStartTime();
        final Date annEndTime = sysAnnouncement.getAnnEndTime();
        
        if (annEndTime != null && annEndTime.getTime() < annStartTime.getTime()) {
            throw new CommonException(new ErrorCode(100100, "通告开始时间应该小于通告结束时间"));
        }
        
    }
    
    /**
     * 功能: 异常就抛出 throwStr
     * 作者: zjt
     * 日期: 2021/3/20 14:16
     * 版本: 1.0
     */
    private void doCheck(Integer sysAnnId, List<Integer> idList, String throwStr) {
        if (sysAnnId != null) {
            idList.remove(sysAnnId);
        }
        
        if (!idList.isEmpty()) {
            throw new CommonException(new ErrorCode(100100, throwStr));
        }
    }
    
    /**
     * 功能: 获取要保存的用户id
     * 作者: zjt
     * 日期: 2021/3/12 11:07
     * 版本: 1.0
     */
    private Collection<Integer> setUserIds(int annTargetType, SysAnnouncement sysAnnouncement, Collection<Integer> sysUserIdList, Collection<Integer> sysDeptIdList) {
        if (annTargetType == AnnTargetType.ALL_USER.getType()) {
            sysUserIdList = sysUserMapper.findId();
        } else if (annTargetType == AnnTargetType.DESIGNATED_DEPARTMENT.getType()) {
            sysUserIdList = sysUserDepartmentMapper.findSysUserIdBySysDepartmentIdIn(sysDeptIdList);
        }
        
        sysAnnouncement.setAnnTarget(annTargetType);
        sysAnnouncement.setAnnDeptIds(JSONUtil.toJsonStr(sysDeptIdList));
        sysAnnouncement.setAnnUserIds(JSONUtil.toJsonStr(sysUserIdList));
        return sysUserIdList;
    }
    
    @Override
    public AnnouncementInfoVO getAnnouncementInfo(int sysAnnoId) {
        final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);
        if (sysAnnouncement != null) {
            final AnnouncementInfoVO announcementInfoVO = new AnnouncementInfoVO();
            announcementInfoVO.setAnnTitle(sysAnnouncement.getAnnTitle());
            announcementInfoVO.setAnnMsgAbstract(sysAnnouncement.getAnnMsgAbstract());
            announcementInfoVO.setAnnContent(sysAnnouncement.getAnnContent());
            announcementInfoVO.setAnnTarget(sysAnnouncement.getAnnTarget());
            announcementInfoVO.setAnnOpenType(sysAnnouncement.getAnnOpenType());
            announcementInfoVO.setAnnStartTime(sysAnnouncement.getAnnStartTime().getTime());
            announcementInfoVO.setAnnEndTime(Optional.ofNullable(sysAnnouncement.getAnnEndTime()).map(Date::getTime).orElse(null));
            announcementInfoVO.setAnnPriority(sysAnnouncement.getAnnPriority());
            announcementInfoVO.setAnnSendStatus(sysAnnouncement.getAnnSendStatus());
            announcementInfoVO.setAnnCategory(sysAnnouncement.getAnnCategory());
            
            announcementInfoVO.setCreateBy(getAnnUserName(sysAnnouncement.getCreateBy()));
            announcementInfoVO.setCreateTime(Optional.ofNullable(sysAnnouncement.getCreateTime()).map(Date::getTime).orElse(null));
            announcementInfoVO.setAnnSender(getAnnUserName(sysAnnouncement.getAnnSenderId()));
            announcementInfoVO.setAnnSendTime(Optional.ofNullable(sysAnnouncement.getAnnSendTime()).map(Date::getTime).orElse(null));
            announcementInfoVO.setUpdateBy(getAnnUserName(sysAnnouncement.getUpdateBy()));
            announcementInfoVO.setUpdateTime(Optional.ofNullable(sysAnnouncement.getCreateTime()).map(Date::getTime).orElse(null));
            announcementInfoVO.setAnnCancel(getAnnUserName(sysAnnouncement.getAnnCancelId()));
            announcementInfoVO.setAnnCancelTime(Optional.ofNullable(sysAnnouncement.getCreateTime()).map(Date::getTime).orElse(null));
            
            final Integer annTarget = sysAnnouncement.getAnnTarget();
            if (AnnTargetType.DESIGNATED_DEPARTMENT.getType() == annTarget) {
                final String deptIds = sysAnnouncement.getAnnDeptIds();
                final List<SysDepartmentLabel> sysDepartmentLabels = JSONUtil.toList(JSONUtil.parseArray(deptIds), Integer.class).stream().map(deptId -> {
                    final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
                    sysDepartmentLabel.setSysDepartmentId(deptId);
                    sysDepartmentLabel.setSysDepartmentName(sysDepartmentMapper.findDeptNameById(deptId));
                    return sysDepartmentLabel;
                }).collect(Collectors.toList());
                announcementInfoVO.setSysDepartmentLabels(sysDepartmentLabels);
            } else if (AnnTargetType.DESIGNATED_USER.getType() == annTarget) {
                final String annUserIds = sysAnnouncement.getAnnUserIds();
                final List<SysUserLabel> sysUserLabels = JSONUtil.toList(JSONUtil.parseArray(annUserIds), Integer.class).stream().map(sysUserId -> {
                    final SysUserLabel sysUserLabel = new SysUserLabel();
                    sysUserLabel.setSysUserId(sysUserId);
                    sysUserLabel.setSysUserName(sysUserMapper.findUserNameById(sysUserId));
                    return sysUserLabel;
                }).collect(Collectors.toList());
                announcementInfoVO.setSysUserLabels(sysUserLabels);
            }
            
            return announcementInfoVO;
        }
        return null;
    }
    
    @Override
    public IPage<AnnouncementItemVO> getAnnouncementItemPage(GetAnnouncementItemPageDTO announcementItemPageDTO) {
        
        final QueryWrapper<SysAnnouncement> queryWrapper = QueryGenerator.initQueryWrapper(announcementItemPageDTO);
        if (announcementItemPageDTO.getAnnCategory() != null) {
            queryWrapper.eq("a.ann_category", announcementItemPageDTO.getAnnCategory());
        }
        
        final IPage<SysAnnouncement> sysAnnouncementIPage = sysAnnouncementMapper.queryPage(queryWrapper, PageUtils.startPage(announcementItemPageDTO));
        
        final List<AnnouncementItemVO> announcementItemVOList = sysAnnouncementIPage.getRecords().stream().map(sysAnnouncement -> {
            final AnnouncementItemVO announcementItemVO = new AnnouncementItemVO();
            announcementItemVO.setSysAnnoId(sysAnnouncement.getId());
            announcementItemVO.setAnnCancelTime(Optional.ofNullable(sysAnnouncement.getAnnCancelTime()).map(Date::getTime).orElse(null));
            announcementItemVO.setAnnSendTime(Optional.ofNullable(sysAnnouncement.getAnnSendTime()).map(Date::getTime).orElse(null));
            announcementItemVO.setCreateTime(Optional.ofNullable(sysAnnouncement.getCreateTime()).map(Date::getTime).orElse(null));
            announcementItemVO.setAnnPriority(sysAnnouncement.getAnnPriority());
            announcementItemVO.setAnnSender(getAnnUserName(sysAnnouncement.getAnnSenderId()));
            announcementItemVO.setAnnSendStatus(sysAnnouncement.getAnnSendStatus());
            announcementItemVO.setAnnTitle(sysAnnouncement.getAnnTitle());
            announcementItemVO.setAnnCategory(sysAnnouncement.getAnnCategory());
            return announcementItemVO;
        }).collect(Collectors.toList());
        
        return PageUtils.conversionIpageRecords(sysAnnouncementIPage, AnnouncementItemVO.class, announcementItemVOList);
    }
    
    /**
     * 功能: 获取人员名称
     * 作者: zjt
     * 日期: 2021/5/28 15:07
     * 版本: 1.0
     */
    private String getAnnUserName(Integer userId) {
        if (userId == null) {
            return "";
        }
        
        if (userId <= 0) {
            return "系统";
        }
        return sysUserService.findUserNameById(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void publishAnnouncement(Collection<Integer> sysAnnoIdList, int userId) {
        for (Integer sysAnnoId : sysAnnoIdList) {
            //先把状态改为 已发布
            final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);
            if (sysAnnouncement != null) {
                sysAnnouncement.setId(sysAnnoId);
                sysAnnouncement.setAnnSendTime(new Date());
                sysAnnouncement.setAnnSendStatus(AnnSendStatus.PUBLISHED.getStatus());
                sysAnnouncement.setUpdateBy(userId);
                sysAnnouncement.setUpdateTime(new Date());
                sysAnnouncementMapper.updateById(sysAnnouncement);
                if (StringUtils.isNotEmpty(sysAnnouncement.getAnnUserIds())) {
                    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            ThreadPoolUtil.execute(() -> {
                                sysAnnouncementSenderService.sendAnnouncementByHasSysAnnoId(SysAnnConfigIdEnum.SYSANNCONFIGID_1, sysAnnoId,
                                        JSONUtil.toList(JSONUtil.parseArray(sysAnnouncement.getAnnUserIds()), Integer.class), AnnPriorityType.getAnnPriorityType(sysAnnouncement.getAnnPriority()));
                            
                            });
                        }
                    });
                }
            }
        }
    }
    
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void revokeAnnouncement(Collection<Integer> sysAnnoIdList, int userId) {
        
        final List<Integer> noticUserIdList = Lists.newArrayList();
        
        for (Integer sysAnnoId : sysAnnoIdList) {
            //要通知的人
            noticUserIdList.addAll(sysUserAnnouncementMapper.findSysUserIdBySysAnnouncementId(sysAnnoId));
            sysUserAnnouncementMapper.deleteBySysAnnouncementId(sysAnnoId);
            final SysAnnouncement sysAnnouncement = sysAnnouncementMapper.selectById(sysAnnoId);
            if (sysAnnouncement != null && !sysAnnouncement.getAnnDelStatus().equals(AnnDeleteStatus.DELETED.getStatus())
                    && !sysAnnouncement.getAnnSendStatus().equals(AnnSendStatus.REVOKED.getStatus())) {
                sysAnnouncement.setAnnSendStatus(AnnSendStatus.REVOKED.getStatus());
                sysAnnouncement.setAnnCancelTime(new Date());
                sysAnnouncement.setAnnCancelId(userId);
                sysAnnouncement.setUpdateBy(userId);
                sysAnnouncement.setUpdateTime(new Date());
                sysAnnouncementMapper.updateById(sysAnnouncement);
            } else {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_35);
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
    public void deleteAnnouncement(Collection<Integer> sysAnnoIdList, int userId) {
        sysAnnouncementMapper.deleteBatchIds(sysAnnoIdList);
        sysUserAnnouncementMapper.deleteBySysAnnouncementIdIn(sysAnnoIdList);
    }
    
}