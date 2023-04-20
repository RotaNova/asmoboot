package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.DingTalkRobotService;
import com.rotanava.boot.system.api.SysAnnouncementConfigService;
import com.rotanava.boot.system.api.module.constant.AllowCloseNoticeType;
import com.rotanava.boot.system.api.module.constant.AnnCategory;
import com.rotanava.boot.system.api.module.constant.AnnTargetType;
import com.rotanava.boot.system.api.module.constant.NoticeType;
import com.rotanava.boot.system.api.module.system.bean.SysAnnouncementTreeItem;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysUserLabel;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncementConfig;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncementConfig;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementConfigVO;
import com.rotanava.boot.system.api.module.system.vo.AnnouncementReceiveConfigVO;
import com.rotanava.boot.system.module.dao.SysAnnouncementConfigMapper;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserAnnouncementConfigMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 17:41
 */
@Slf4j
@Service
@DubboService
@Transactional(rollbackFor = Throwable.class)
public class SysAnnouncementConfigServiceImpl implements SysAnnouncementConfigService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysAnnouncementConfigMapper sysAnnouncementConfigMapper;

    @Autowired
    private SysUserAnnouncementConfigMapper sysUserAnnouncementConfigMapper;

    @Autowired
    private DingTalkRobotService dingTalkRobotService;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<AnnouncementConfigVO> getAnnouncementConfigList(BaseDTO baseDTO) {
        final List<SysAnnouncementConfig> sysAnnouncementConfigList = sysAnnouncementConfigMapper.selectList(QueryGenerator.initQueryWrapper(baseDTO));

        final List<AnnouncementConfigVO> announcementConfigList = Lists.newArrayList();

        for (SysAnnouncementConfig sysAnnouncementConfig : sysAnnouncementConfigList) {
            final AnnouncementConfigVO announcementConfigVO = new AnnouncementConfigVO();
            final Integer sysAnnouncementConfigId = sysAnnouncementConfig.getId();
            announcementConfigVO.setSysAnnConfigId(sysAnnouncementConfigId);
            announcementConfigVO.setType(sysAnnouncementConfig.getType());
            announcementConfigVO.setConfigName(sysAnnouncementConfig.getConfigName());
            announcementConfigVO.setEmailNotice(sysAnnouncementConfig.getEmailNotice());
            announcementConfigVO.setPhoneNotice(sysAnnouncementConfig.getPhoneNotice());
            announcementConfigVO.setSmsNotice(sysAnnouncementConfig.getSmsNotice());
            announcementConfigVO.setSysNotice(sysAnnouncementConfig.getSysNotice());
            announcementConfigVO.setWechatNotice(sysAnnouncementConfig.getWechatNotice());
            announcementConfigVO.setAllowCloseNotice(sysAnnouncementConfig.getAllowCloseNotice());
            announcementConfigVO.setDingTalkNotice(sysAnnouncementConfig.getDingTalkNotice());
            if (sysAnnouncementConfigId > 3) {
                announcementConfigVO.setAnnTarget(sysAnnouncementConfig.getAnnTarget());
            }
            announcementConfigList.add(announcementConfigVO);
        }

        return announcementConfigList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Tree<String>> getAnnouncementTree() {
        final List<SysAnnouncementConfig> sysAnnouncementConfigList = sysAnnouncementConfigMapper.selectList(null);

        final List<SysAnnouncementTreeItem> sysAnnouncementTreeItemList = Lists.newArrayList();

        for (AnnCategory annCategory : AnnCategory.values()) {
            final SysAnnouncementTreeItem sysAnnouncementTreeItem = new SysAnnouncementTreeItem();
            sysAnnouncementTreeItem.setId(annCategory.getType() + "-type");
            sysAnnouncementTreeItem.setConfigName(annCategory.getName());
            sysAnnouncementTreeItem.setPid("0");
            sysAnnouncementTreeItemList.add(sysAnnouncementTreeItem);
        }


        for (SysAnnouncementConfig sysAnnouncementConfig : sysAnnouncementConfigList) {
            if (sysAnnouncementConfig.getId() > 3) {
                final SysAnnouncementTreeItem sysAnnouncementTreeItem = new SysAnnouncementTreeItem();
                sysAnnouncementTreeItem.setId(Convert.toStr(sysAnnouncementConfig.getId()));
                sysAnnouncementTreeItem.setConfigName(sysAnnouncementConfig.getConfigName());
                sysAnnouncementTreeItem.setPid(sysAnnouncementConfig.getType() + "-type");
                sysAnnouncementTreeItemList.add(sysAnnouncementTreeItem);
            }
        }


        final List<Tree<String>> treeList = TreeUtil.build(sysAnnouncementTreeItemList, "0", (sysAnnouncementConfig, treeNode) -> {
            treeNode.setId(Convert.toStr(sysAnnouncementConfig.getId()));
            treeNode.setParentId(sysAnnouncementConfig.getPid());
            treeNode.put("sysAnnConfigId", sysAnnouncementConfig.getId().replaceAll("-type", ""));
            treeNode.setName(sysAnnouncementConfig.getConfigName());
        });

        //去掉没子节点的
        int size = treeList.size();
        int removeTime = 0;
        for (int i = 0; i < size; i++) {
            final List<Tree<String>> children = treeList.get(i - removeTime).getChildren();
            if (CollectionUtil.isEmpty(children)) {
                treeList.remove(treeList.get(i - removeTime));
                removeTime++;
            }
        }

        log.info("获取获取通知配置树形列表 treeList={}", treeList);
        return treeList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveAnnouncementConfig(List<SaveAnnouncementConfigDTO> setAnnouncementConfigDTOList) {
        for (SaveAnnouncementConfigDTO saveAnnouncementConfigDTO : setAnnouncementConfigDTOList) {
            final SysAnnouncementConfig sysAnnouncementConfig = new SysAnnouncementConfig();
            BeanUtils.copyProperties(saveAnnouncementConfigDTO, sysAnnouncementConfig);
            sysAnnouncementConfig.setId(saveAnnouncementConfigDTO.getSysAnnConfigId());
            sysAnnouncementConfigMapper.updateById(sysAnnouncementConfig);

            if (saveAnnouncementConfigDTO.getAllowCloseNotice() == AllowCloseNoticeType.NOT_ALLOWED.getType()) {
                sysUserAnnouncementConfigMapper.deleteBySysAnnConfigId(saveAnnouncementConfigDTO.getSysAnnConfigId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<AnnouncementReceiveConfigVO> getAnnouncementReceiveConfigList(BaseDTO baseDTO, int userId) {

        //查数据
        final List<SysAnnouncementConfig> sysAnnouncementConfigList = sysAnnouncementConfigMapper.selectList(QueryGenerator.initQueryWrapper(baseDTO));
        final List<Integer> sysAnnConfigIdList = sysUserAnnouncementConfigMapper.findSysAnnConfigIdBySysUserId(userId);

        final List<AnnouncementReceiveConfigVO> announcementReceiveConfigVOList = Lists.newArrayList();

        for (SysAnnouncementConfig sysAnnouncementConfig : sysAnnouncementConfigList) {
            final AnnouncementReceiveConfigVO announcementReceiveConfigVO = new AnnouncementReceiveConfigVO();
            final Integer sysAnnouncementConfigId = sysAnnouncementConfig.getId();
            announcementReceiveConfigVO.setSysAnnConfigId(sysAnnouncementConfigId);
            announcementReceiveConfigVO.setNoticeSwitch(sysAnnConfigIdList.contains(sysAnnouncementConfigId)
                    ? NoticeType.NO_NOTICE.getType() : NoticeType.NOTICE.getType());
            announcementReceiveConfigVO.setType(sysAnnouncementConfig.getType());
            announcementReceiveConfigVO.setConfigName(sysAnnouncementConfig.getConfigName());
            announcementReceiveConfigVOList.add(announcementReceiveConfigVO);
        }

        return announcementReceiveConfigVOList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveAnnouncementReceiveConfig(List<SaveAnnouncementReceiveConfigDTO> saveAnnouncementReceiveConfigDTOList, int userId) {
        for (SaveAnnouncementReceiveConfigDTO saveAnnouncementReceiveConfigDTO : saveAnnouncementReceiveConfigDTOList) {
            final Integer announcementConfigId = saveAnnouncementReceiveConfigDTO.getSysAnnConfigId();
            final Integer noticeSwitch = saveAnnouncementReceiveConfigDTO.getNoticeSwitch();

            final SysUserAnnouncementConfig sysUserAnnouncementConfig = sysUserAnnouncementConfigMapper.findAllBySysUserIdAndSysAnnConfigId(userId, announcementConfigId);

            if (sysUserAnnouncementConfig != null) {
                if (noticeSwitch == NoticeType.NOTICE.getType()) {
                    sysUserAnnouncementConfigMapper.deleteById(sysUserAnnouncementConfig.getId());
                }
            } else {
                if (noticeSwitch == NoticeType.NO_NOTICE.getType()) {
                    final SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(announcementConfigId);
                    final Integer allowCloseNotice = sysAnnouncementConfig.getAllowCloseNotice();

                    if (allowCloseNotice == AllowCloseNoticeType.NOT_ALLOWED.getType()) {
                        throw new CommonException(new ErrorCode(100100, String.format("%s 该通知不予许关闭", sysAnnouncementConfig.getConfigName())));
                    }

                    final SysUserAnnouncementConfig sysUserAnnouncementConfigInsert = new SysUserAnnouncementConfig();
                    sysUserAnnouncementConfigInsert.setSysUserId(userId);
                    sysUserAnnouncementConfigInsert.setSysAnnConfigId(announcementConfigId);
                    sysUserAnnouncementConfigMapper.insert(sysUserAnnouncementConfigInsert);
                }
            }

        }
    }

    @Override
    public void resetReceiveConfig(Integer currentReqUserId) {
        sysUserAnnouncementConfigMapper.deleteBySysUserId(currentReqUserId);
    }

    @Override
    public void deleteAnnouncementConfig(Set<Integer> sysAnnConfigIdList) {
        if (CollectionUtil.containsAny(sysAnnConfigIdList, Lists.newArrayList(1, 2, 3))) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_69);
        }

        if (CollectionUtil.isNotEmpty(sysAnnConfigIdList)) {
            List<SysAnnouncementConfig> sysAnnouncementConfigs = sysAnnouncementConfigMapper.selectBatchIds(sysAnnConfigIdList);
            for (SysAnnouncementConfig sysAnnouncementConfig : sysAnnouncementConfigs) {
                String annDingTalkIds = sysAnnouncementConfig.getAnnDingTalkIds();
                if (!StringUtils.isEmpty(annDingTalkIds) && JSONUtil.isJsonArray(annDingTalkIds)) {
                    List<Integer> dingTalkIds = JSONUtil.parseArray(annDingTalkIds).toList(Integer.class);
                    for (Integer dingTalkId : dingTalkIds) {
                        DeleteDingTalkRobotDTO deleteDingTalkRobotDTO = new DeleteDingTalkRobotDTO();
                        deleteDingTalkRobotDTO.setSysAnnConfigId(sysAnnouncementConfig.getId());
                        deleteDingTalkRobotDTO.setDingTalkRobotId(dingTalkId);
                        dingTalkRobotService.deleteDingTalkRobot(deleteDingTalkRobotDTO);
                    }
                }
            }

            sysAnnouncementConfigMapper.deleteBatchIds(sysAnnConfigIdList);

        }
    }

    @Override
    public void editAnnouncementConfig(EditAnnouncementConfigDTO announcementConfigDTO) {
        final Integer sysAnnConfigId = announcementConfigDTO.getSysAnnConfigId();
        if (sysAnnConfigId == 1 || sysAnnConfigId == 2 || sysAnnConfigId == 3) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_42);
        }

        final SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(sysAnnConfigId);
        if (sysAnnouncementConfig == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            final Integer id = sysAnnouncementConfigMapper.findIdByConfigName(announcementConfigDTO.getConfigName());
            if (id == null || id.equals(sysAnnConfigId)) {

                sysAnnouncementConfig.setConfigName(announcementConfigDTO.getConfigName());
                sysAnnouncementConfig.setType(announcementConfigDTO.getType());

                sysAnnouncementConfig.setAnnTarget(announcementConfigDTO.getAnnTarget());
                sysAnnouncementConfig.setAnnUserIds(JSONUtil.toJsonStr(announcementConfigDTO.getSysUserIdList()));
                sysAnnouncementConfig.setAnnDeptIds(JSONUtil.toJsonStr(announcementConfigDTO.getSysDeptIdList()));
                sysAnnouncementConfigMapper.updateById(sysAnnouncementConfig);
            } else {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_41);
            }
        }

    }

    @Override
    public void addAnnouncementConfig(AddAnnouncementConfigDTO addAnnouncementConfigDTO) {
        final SysAnnouncementConfig sysAnnouncementConfig = new SysAnnouncementConfig();
        int allow = 0;
        sysAnnouncementConfig.setAllowCloseNotice(allow);

        if (sysAnnouncementConfigMapper.countByConfigName(addAnnouncementConfigDTO.getConfigName()) <= 0) {
            sysAnnouncementConfig.setConfigName(addAnnouncementConfigDTO.getConfigName());
            sysAnnouncementConfig.setEmailNotice(allow);
            sysAnnouncementConfig.setPhoneNotice(allow);
            sysAnnouncementConfig.setSmsNotice(allow);
            sysAnnouncementConfig.setSysNotice(allow);
            sysAnnouncementConfig.setType(addAnnouncementConfigDTO.getType());
            sysAnnouncementConfig.setWechatNotice(allow);
            sysAnnouncementConfig.setDingTalkNotice(allow);

            sysAnnouncementConfig.setAnnTarget(addAnnouncementConfigDTO.getAnnTarget());
            sysAnnouncementConfig.setAnnUserIds(JSONUtil.toJsonStr(addAnnouncementConfigDTO.getSysUserIdList()));
            sysAnnouncementConfig.setAnnDeptIds(JSONUtil.toJsonStr(addAnnouncementConfigDTO.getSysDeptIdList()));

            sysAnnouncementConfigMapper.insert(sysAnnouncementConfig);
        } else {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_41);
        }

    }

    @Override
    public AnnouncementConfigInfoVO getAnnouncementConfigInfoVO(Integer sysAnnConfigId) {
        final SysAnnouncementConfig sysAnnouncementConfig = sysAnnouncementConfigMapper.selectById(sysAnnConfigId);
        if (sysAnnouncementConfig == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        } else {
            final AnnouncementConfigInfoVO announcementConfigInfoVO = new AnnouncementConfigInfoVO();
            final Integer annTarget = sysAnnouncementConfig.getAnnTarget();
            announcementConfigInfoVO.setSysAnnConfigId(sysAnnouncementConfig.getId());
            announcementConfigInfoVO.setConfigName(sysAnnouncementConfig.getConfigName());
            announcementConfigInfoVO.setAnnTarget(annTarget);
            announcementConfigInfoVO.setType(sysAnnouncementConfig.getType());

            if (AnnTargetType.DESIGNATED_DEPARTMENT.getType() == annTarget) {
                final String deptIds = sysAnnouncementConfig.getAnnDeptIds();
                final List<SysDepartmentLabel> sysDepartmentLabels = JSONUtil.toList(JSONUtil.parseArray(deptIds), Integer.class).stream().map(deptId -> {
                    final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
                    sysDepartmentLabel.setSysDepartmentId(deptId);
                    sysDepartmentLabel.setSysDepartmentName(sysDepartmentMapper.findDeptNameById(deptId));
                    return sysDepartmentLabel;
                }).collect(Collectors.toList());
                announcementConfigInfoVO.setSysDepartmentLabels(sysDepartmentLabels);
            } else if (AnnTargetType.DESIGNATED_USER.getType() == annTarget) {
                final String annUserIds = sysAnnouncementConfig.getAnnUserIds();
                final List<SysUserLabel> sysUserLabels = JSONUtil.toList(JSONUtil.parseArray(annUserIds), Integer.class).stream().map(sysUserId -> {
                    final SysUserLabel sysUserLabel = new SysUserLabel();
                    sysUserLabel.setSysUserId(sysUserId);
                    sysUserLabel.setSysUserName(sysUserMapper.findUserNameById(sysUserId));
                    return sysUserLabel;
                }).collect(Collectors.toList());
                announcementConfigInfoVO.setSysUserLabels(sysUserLabels);
            }
            return announcementConfigInfoVO;
        }
    }

}