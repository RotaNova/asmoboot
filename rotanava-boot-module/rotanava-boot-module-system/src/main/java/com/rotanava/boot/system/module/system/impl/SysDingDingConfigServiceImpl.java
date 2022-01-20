package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.response.OapiRoleListResponse;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysDingDingConfigService;
import com.rotanava.boot.system.api.SysRoleManagementService;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.constant.enums.RoleDataScopeType;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.DeptType;
import com.rotanava.boot.system.api.module.constant.RecordMessageType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.constant.UserSex;
import com.rotanava.boot.system.api.module.constant.UserSysRole;
import com.rotanava.boot.system.api.module.system.bean.RecordMessage;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentTree;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysRole;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.SysDingDingConfigDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.AddDepartmentDTO;
import com.rotanava.boot.system.api.module.system.dto.departmentmanage.UpdateDepartmentDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.AddSysRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.rolemanage.UpdateSysRoleDTO;
import com.rotanava.boot.system.api.module.system.vo.SysDingDingConfigVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserInfoVO;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysRoleMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.dingding.sdk.DepartmentSDK;
import com.rotanava.dingding.sdk.RoleSDK;
import com.rotanava.dingding.sdk.UserSDK;
import com.rotanava.dingding.service.DingTalkService;
import com.rotanava.framework.async.ThreadPoolUtil;
import com.rotanava.framework.code.BaseException;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.socket.PcMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-16 16:44
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class SysDingDingConfigServiceImpl implements SysDingDingConfigService {
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private SysRoleManagementService sysRoleManagementService;
    
    @Autowired
    private SysDepartmentService sysDepartmentService;
    
    @Autowired
    private DingTalkService dingTalkService;
    
    @Autowired
    private DepartmentSDK departmentSDK;
    
    @Autowired
    private RoleSDK roleSDK;
    
    @Autowired
    private UserSDK userSDK;
    
    @Autowired
    private PcMessageUtil pcMessageUtil;
    
    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;
    
    @Autowired
    @Lazy
    private SysDingDingConfigService sysDingDingConfigService;
    
    @Autowired
    private SysServiceSettingService sysServiceSettingService;
    
    /**
     * 记录钉钉录入的错误消息
     */
    private static final Map<String, List<RecordMessage>> RECORD_MESSAGE_MAP = Maps.newConcurrentMap();
    
    /**
     * 前缀
     */
    public static final String PRE_STR = "sys_dd_";
    
    public static final String AESKEY = PRE_STR + "aesKey";
    public static final String AGENTID = PRE_STR + "agentId";
    public static final String APPKEY = PRE_STR + "appKey";
    public static final String APPSECRET = PRE_STR + "appSecret";
    public static final String REQADDRESS = PRE_STR + "reqAddress";
    public static final String TOKEN = PRE_STR + "token";
    
    public static final String DING_DING_PRE_NAME = "[钉钉]";
    public static final String DING_DING_PRE_CODE = "[DD]";
    
    @Override
    public void saveConfig(SysDingDingConfigDTO sysDingDingConfigDTO, Integer currentReqUserId) {
        final String agentId = sysDingDingConfigDTO.getAgentId();
        final String appKey = sysDingDingConfigDTO.getAppKey();
        final String appSecret = sysDingDingConfigDTO.getAppSecret();
        
        final String aesKey = sysDingDingConfigDTO.getAesKey();
        final String reqAddress = sysDingDingConfigDTO.getReqAddress();
        final String token = sysDingDingConfigDTO.getToken();
        
        if (!(StringUtils.isAllBlank(aesKey, reqAddress, token) || StringUtils.isNoneBlank(aesKey, reqAddress, token))) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_401);
        }
        
        
        sysServiceSettingService.saveDingDingServiceSetting(AESKEY, "平台配置钉钉加密 aes_key", aesKey, aesKey, "平台配置钉钉加密 aes_key");
        sysServiceSettingService.saveDingDingServiceSetting(AGENTID, "平台配置钉钉agentId", agentId, agentId, "平台配置钉钉agentId");
        sysServiceSettingService.saveDingDingServiceSetting(APPKEY, "平台配置钉钉appKey", appKey, appKey, "平台配置钉钉appKey");
        sysServiceSettingService.saveDingDingServiceSetting(APPSECRET, "平台配置钉钉appSecret", appSecret, appSecret, "平台配置钉钉appSecret");
        sysServiceSettingService.saveDingDingServiceSetting(REQADDRESS, "平台配置钉钉请求网址,接收事件订阅的url，必须是公网可以访问的url地址.", reqAddress, reqAddress, "平台配置钉钉请求网址,接收事件订阅的url，必须是公网可以访问的url地址.");
        sysServiceSettingService.saveDingDingServiceSetting(TOKEN, "平台配置钉钉签名 token", token, token, "平台配置钉钉签名 token");
        
        //websoket通知前端
        ThreadPoolUtil.execute(() -> {
            sysDingDingConfigService.syncDingDingUserData(sysDingDingConfigDTO.getUid(), currentReqUserId);
        });
        
    }
    
    @Override
    public SysDingDingConfigVO getSysDingDingConfigVO() {
        final SysDingDingConfigVO sysDingDingConfigVO = new SysDingDingConfigVO();
    
        final List<SysServiceSetting> sysServiceSettings = sysServiceSettingService.getSysServiceSetting(Lists.newArrayList(AESKEY, AGENTID, APPKEY, APPSECRET, REQADDRESS, TOKEN));
        final Map<String, SysServiceSetting> serverConfigMap = sysServiceSettings
                .stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, Function.identity()));
        
        sysDingDingConfigVO.setAesKey(serverConfigMap.getOrDefault(AESKEY, new SysServiceSetting()).getSysServiceValue());
        sysDingDingConfigVO.setAgentId(serverConfigMap.getOrDefault(AGENTID, new SysServiceSetting()).getSysServiceValue());
        sysDingDingConfigVO.setAppKey(serverConfigMap.getOrDefault(APPKEY, new SysServiceSetting()).getSysServiceValue());
        sysDingDingConfigVO.setAppSecret(serverConfigMap.getOrDefault(APPSECRET, new SysServiceSetting()).getSysServiceValue());
        sysDingDingConfigVO.setReqAddress(serverConfigMap.getOrDefault(REQADDRESS, new SysServiceSetting()).getSysServiceValue());
        sysDingDingConfigVO.setToken(serverConfigMap.getOrDefault(TOKEN, new SysServiceSetting()).getSysServiceValue());
        
        return sysDingDingConfigVO;
    }
    
    @Override
    public void syncDingDingUserData(String uid, Integer currentReqUserId) {
        boolean hasError = false;
        
        try {
            //先获取钉钉数据
            final SysDingDingConfigVO sysDingDingConfigVO = getSysDingDingConfigVO();
            String accessToken = null;
            
            //验证权限
            try {
                accessToken = dingTalkService.getAccessToken(sysDingDingConfigVO.getAppKey(), sysDingDingConfigVO.getAppSecret());
                
                recordMessage(uid, "getRoleList", "验证获取角色列表权限", RecordMessageType.LOADING, "", false, currentReqUserId);
                roleSDK.getRoleList(1L, 0L, accessToken);
                recordMessage(uid, "getRoleList", "验证获取角色列表权限", RecordMessageType.SUCCESS, "", false, currentReqUserId);
                
                recordMessage(uid, "getDeptList", "验证获取部门列表权限", RecordMessageType.LOADING, "", false, currentReqUserId);
                departmentSDK.getDeptList(1L, accessToken);
                recordMessage(uid, "getDeptList", "验证获取部门列表权限", RecordMessageType.SUCCESS, "", false, currentReqUserId);
                
                recordMessage(uid, "getDeptInfo", "验证获取部门详情权限", RecordMessageType.LOADING, "", false, currentReqUserId);
                departmentSDK.getDeptInfo(1L, accessToken);
                recordMessage(uid, "getDeptInfo", "验证获取部门详情权限", RecordMessageType.SUCCESS, "", false, currentReqUserId);
                
                recordMessage(uid, "getDeptUserList", "验证获取部门用户userid列表权限", RecordMessageType.LOADING, "", false, currentReqUserId);
                userSDK.getDeptUserList(1L, accessToken);
                recordMessage(uid, "getDeptUserList", "验证获取部门用户userid列表权限", RecordMessageType.SUCCESS, "", false, currentReqUserId);

//                recordMessage(uid, "getUserInfo", "验证根据userid获取用户详情权限", RecordMessageType.LOADING, "", false, currentReqUserId);
//                userSDK.getUserInfo("1", accessToken);
//                recordMessage(uid, "getUserInfo", "验证根据userid获取用户详情权限", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            
            } catch (Exception e) {
                recordMessage(uid, "validDingDingApiPermissions", "验证钉钉接口权限失败", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            
            List<OapiRoleListResponse.OpenRoleGroup> openRoleGroupList = Lists.newArrayList();
            List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList = Lists.newArrayList();
            List<OapiV2UserGetResponse.UserGetResponse> userInfoList = Lists.newArrayList();
            
            try {
                //1.获取钉钉角色数据
                recordMessage(uid, "getDingDingRoleData", "获取钉钉角色数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                openRoleGroupList = getDingDingRoleData(accessToken);
                recordMessage(uid, "getDingDingRoleData", "获取钉钉角色数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "getDingDingRoleData", "获取钉钉角色数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            try {
                //2.获取钉钉部门数据
                recordMessage(uid, "getDingDingDeptData", "获取钉钉部门数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                deptList = getDingDingDeptData(accessToken);
                recordMessage(uid, "getDingDingDeptData", "获取钉钉部门数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "getDingDingDeptData", "获取钉钉部门数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            try {
                //3.获取钉钉用户数据
                recordMessage(uid, "getDingDingUserInfoData", "获取钉钉用户数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                userInfoList = getDingDingUserInfoData(accessToken, deptList);
                recordMessage(uid, "getDingDingUserInfoData", "获取钉钉用户数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "getDingDingUserInfoData", "获取钉钉用户数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            
            //解析钉钉入库
            
            try {
                //入库钉钉角色数据
                recordMessage(uid, "importDingDingRoleData", "入库钉钉角色数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                importDingDingRoleData(currentReqUserId, openRoleGroupList);
                recordMessage(uid, "importDingDingRoleData", "入库钉钉角色数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "importDingDingRoleData", "入库钉钉角色数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            //入库钉钉部门数据
            try {
                recordMessage(uid, "importDingDingDeptData", "入库钉钉部门数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                importDingDingDeptData(currentReqUserId, deptList);
                recordMessage(uid, "importDingDingDeptData", "入库钉钉部门数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "importDingDingDeptData", "入库钉钉部门数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
            //入库钉钉用户数据
            try {
                recordMessage(uid, "importDingDingUserData", "入库钉钉用户数据", RecordMessageType.LOADING, "", false, currentReqUserId);
                importDingDingUserData(currentReqUserId, userInfoList);
                recordMessage(uid, "importDingDingUserData", "入库钉钉用户数据", RecordMessageType.SUCCESS, "", false, currentReqUserId);
            } catch (Exception e) {
                recordMessage(uid, "importDingDingUserData", "入库钉钉用户数据", RecordMessageType.ERROR, getErrorMsg(e), false, currentReqUserId);
                throw e;
            }
            
        } catch (Exception e) {
            hasError = true;
            String msg = e.getMessage();
            log.error("同步钉钉数据入库失败", e);
            
            if (e instanceof BaseException) {
                final BaseException baseException = (BaseException) e;
                msg = baseException.getErrorCode().getMsg();
            }
            
            msg += " 入库失败，系统已回退至保存前状态";
            recordMessage(uid, "importDingDingDataSuccess", "入库钉钉数据失败", RecordMessageType.ERROR, msg, true, currentReqUserId);
            throw new CommonException(CommonErrorCode.COMMON_ERROR_49);
        } finally {
            if (!hasError) {
                recordMessage(uid, "importDingDingDataSuccess", "入库钉钉数据成功", RecordMessageType.SUCCESS, "", true, currentReqUserId);
            }
        }
    }
    
    /**
     * 功能: 获取错误信息
     * 作者: zjt
     * 日期: 2021/12/31 15:32
     * 版本: 1.0
     */
    private String getErrorMsg(Exception e) {
        String msg = e.getMessage();
        if (e instanceof BaseException) {
            final BaseException baseException = (BaseException) e;
            msg = baseException.getErrorCode().getMsg();
        } else {
            if (StringUtils.isBlank(msg)) {
                msg = "发生错误请重试";
            }
        }
        return msg;
    }
    
    /**
     * 功能: 入库钉钉用户数据
     * 作者: zjt
     * 日期: 2021/12/20 11:42
     * 版本: 1.0
     */
    private void importDingDingUserData(Integer currentReqUserId, List<OapiV2UserGetResponse.UserGetResponse> userInfoList) throws Exception {
        final List<SysUser> sysUserList = sysUserMapper.findall();
        final Map<String, SysUser> sysUserMap = sysUserList.stream().collect(Collectors.toMap(SysUser::getUserCode, Function.identity()));
        
        final List<String> sysUserCodes = sysUserList.stream().filter(sysUser -> {
            return sysUser.getUserCode().startsWith(DING_DING_PRE_CODE);
        }).map(SysUser::getUserCode).collect(Collectors.toList());
        
        final List<String> insertSysUserCodes = userInfoList.stream().map(userGetResponse -> {
            return getDingDingPreCode(userGetResponse.getUserid());
        }).collect(Collectors.toList());
        
        sysUserCodes.removeAll(insertSysUserCodes);
        
        //删除钉钉不存在的用户
        sysUserService.thoroughDeleteSysUser(sysUserCodes.stream().map(userCode -> {
            return sysUserMap.get(userCode).getId();
        }).collect(Collectors.toList()), currentReqUserId);
        
        //插入或者更新用户
        for (OapiV2UserGetResponse.UserGetResponse userInfo : userInfoList) {
            String userCode = getDingDingPreCode(userInfo.getUserid());
            final String mobile = userInfo.getMobile();
            final SysUser byUserAccountName = sysUserMapper.findByUserAccountName(mobile);
            SysUser sysUser = sysUserMapper.findByUserCode(userCode);
            //判断用户编号和登陆账号是否重复 如果登陆账号重复要删除
            sysUser = getAndcheckNeedDelete(byUserAccountName, sysUser, userInfo, currentReqUserId);
            
            
            if (sysUser == null) {
                //新增
                final AddSysUserDTO sysUserDTO = new AddSysUserDTO();
                sysUserDTO.setUserName(getDingDingPreName(userInfo.getName()));
                sysUserDTO.setUserAccountName(mobile);
                sysUserDTO.setUserPassword("RN123456");
                sysUserDTO.setUserSex(UserSex.DO_NOT_DISCLOSE.getSex());
                sysUserDTO.setUserCode(getDingDingPreCode(userInfo.getUserid()));
                sysUserDTO.setUserValidTime(Date8Util.parse("2099-12-31", Date8Util.DATE).getTime());
                sysUserDTO.setUserOccupation(userInfo.getTitle());
//                sysUserDTO.setUserPhone(userInfo.getMobile());
//                sysUserDTO.setUserEmail(userInfo.getEmail());
                
                //设置用户所属部门
                final List<OapiV2UserGetResponse.DeptLeader> deptLeaderInfos = userInfo.getLeaderInDept();
                final List<Integer> sysDepartmentIdList = getDingDingUserDeptIds(deptLeaderInfos, false);
                sysUserDTO.setSysDepartmentIdList(sysDepartmentIdList);
                
                //设置负责的部门
                final List<Integer> responsibleDeptList = getDingDingUserDeptIds(deptLeaderInfos, true);
                if (responsibleDeptList.isEmpty()) {
                    sysUserDTO.setUserSysrole(UserSysRole.MEMBER.getType());
                    sysUserDTO.setResponsibleDeptList(Collections.emptyList());
                } else {
                    sysUserDTO.setUserSysrole(UserSysRole.ADMINISTRATOR.getType());
                    sysUserDTO.setResponsibleDeptList(responsibleDeptList);
                }
                
                //设置系统角色
                final List<OapiV2UserGetResponse.UserRole> roleList = userInfo.getRoleList();
                sysUserDTO.setSysRoleIdList(getDingDingRoleUserIds(roleList));
                final Integer sysUserId = sysUserService.addSysUser(sysUserDTO, getUserAvatar(userInfo.getAvatar()), currentReqUserId, false);
                
                //绑定手机号和邮箱
                bindPhoneAndEmail(sysUserId, mobile, userInfo.getEmail());
            } else {
                final SysUserInfoVO sysUserInfo = sysUserService.getSysUserInfo(sysUser.getId(), null);
                //更新
                final UpdateSysUserDTO sysUserDTO = new UpdateSysUserDTO();
                sysUserDTO.setSysUserId(sysUserInfo.getSysUserId());
                sysUserDTO.setUserSex(sysUserInfo.getUserSex());
                sysUserDTO.setUserValidTime(sysUserInfo.getUserValidTime());
                sysUserDTO.setUserOccupation(userInfo.getTitle());
                sysUserDTO.setUserName(getDingDingPreName(userInfo.getName()));
                sysUserDTO.setUserAddress(sysUserInfo.getUserAddress());
                sysUserDTO.setUserBirthday(sysUserInfo.getUserBirthday());
                
                //设置用户所属部门
                final List<OapiV2UserGetResponse.DeptLeader> deptLeaderInfos = userInfo.getLeaderInDept();
                final List<Integer> sysDepartmentIdList = getDingDingUserDeptIds(deptLeaderInfos, false);
                
                //需要保留之前钉钉的部门数据
                final Set<Integer> preSysDeptIds = sysUserInfo.getSysDepartmentLabelList().stream().map(SysDepartmentLabel::getSysDepartmentId).collect(Collectors.toSet());
                preSysDeptIds.addAll(sysDepartmentIdList);
                sysUserDTO.setSysDepartmentIdList(preSysDeptIds);
                
                //设置负责的部门
                final List<Integer> responsibleDeptList = getDingDingUserDeptIds(deptLeaderInfos, true);
                final Set<Integer> preresponsibleDeptIds = sysUserInfo.getResponsibleDeptLabelList().stream().map(SysDepartmentLabel::getSysDepartmentId).collect(Collectors.toSet());
                responsibleDeptList.addAll(preresponsibleDeptIds);
                if (responsibleDeptList.isEmpty()) {
                    sysUserDTO.setUserSysrole(UserSysRole.MEMBER.getType());
                    sysUserDTO.setResponsibleDeptList(Collections.emptyList());
                } else {
                    sysUserDTO.setUserSysrole(UserSysRole.ADMINISTRATOR.getType());
                    sysUserDTO.setResponsibleDeptList(responsibleDeptList);
                }
                
                //设置系统角色
                final List<OapiV2UserGetResponse.UserRole> roleList = userInfo.getRoleList();
                final Set<Integer> preRoleIds = sysUserInfo.getSysRoleLabelList().stream().map(SysRoleLabel::getSysRoleId).collect(Collectors.toSet());
                preRoleIds.addAll(getDingDingRoleUserIds(roleList));
                sysUserDTO.setSysRoleIdList(preRoleIds);
                
                sysUserService.updateSysUser(sysUserDTO, getUserAvatar(userInfo.getAvatar()), currentReqUserId);
                
                //绑定手机号和邮箱
                bindPhoneAndEmail(sysUser.getId(), mobile, userInfo.getEmail());
            }
        }
    }
    
    /**
     * 功能: 检查是否需要删除
     * 作者: zjt
     * 日期: 2021/12/29 14:48
     * 版本: 1.0
     */
    private SysUser getAndcheckNeedDelete(SysUser byUserAccountName, SysUser bySysCodeUser, OapiV2UserGetResponse.UserGetResponse userInfo, Integer currentReqUserId) {
        //如果找不到重复的登录号跳过
        final Set<Integer> deleteUserIds = Sets.newHashSet();
        SysUser sysUser = null;
        final String mobile = userInfo.getMobile();
        
        if (byUserAccountName == null) {
            if (bySysCodeUser != null) {
                if (bySysCodeUser.getUserAccountName().equals(mobile)) {
                    sysUser = bySysCodeUser;
                } else {
                    deleteUserIds.add(bySysCodeUser.getId());
                }
            }
        } else {
            if (bySysCodeUser != null) {
                
                if (bySysCodeUser.getId().equals(byUserAccountName.getId())) {
                    sysUser = bySysCodeUser;
                } else {
                    deleteUserIds.add(bySysCodeUser.getId());
                    deleteUserIds.add(byUserAccountName.getId());
                }
            } else {
                deleteUserIds.add(byUserAccountName.getId());
            }
        }
        
        
        if (CollectionUtils.isNotEmpty(deleteUserIds)) {
            if (deleteUserIds.contains(1)) {
                throw new CommonException(new ErrorCode(100270, userInfo.getName() + "导入的登录账号和管理员相同无法导入请修改手机号"));
            }
            sysUserService.thoroughDeleteSysUser(Lists.newArrayList(deleteUserIds), currentReqUserId);
        }
        
        return sysUser;
    }
    
    /**
     * 功能: 入库钉钉部门数据
     * 作者: zjt
     * 日期: 2021/12/17 16:56
     * 版本: 1.0
     */
    private void importDingDingDeptData(Integer currentReqUserId, List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList) {
        final List<SysDepartment> sysDepartmentList = sysDepartmentMapper.findall();
        final Map<String, SysDepartment> sysDepartmentMap = sysDepartmentList.stream().collect(Collectors.toMap(SysDepartment::getDeptCode, Function.identity()));
        
        //找出钉钉不存在的部门进行删除
        final Set<String> dingDingDeptCodes = sysDepartmentList.stream().map(SysDepartment::getDeptCode).filter(deptCode -> {
            return deptCode.startsWith(DING_DING_PRE_CODE);
        }).collect(Collectors.toSet());
        
        final Set<String> insertDingDingDeptCodes = deptList.stream().map(deptBaseResponse -> {
            return getDingDingPreCode(deptBaseResponse.getDeptId());
        }).collect(Collectors.toSet());
        
        dingDingDeptCodes.removeAll(insertDingDingDeptCodes);
        
        //删除多余的钉钉考勤部门
        sysDepartmentService.deleteBatchDepartment(dingDingDeptCodes.stream().map(deptCode -> {
            return sysDepartmentMap.get(deptCode).getId();
        }).collect(Collectors.toList()), currentReqUserId);
        
        
        final List<Tree<Integer>> departmentTree = sysDepartmentService.getDepartmentList(null);
        //获取钉钉之前那棵树
        Tree<Integer> befDingDingTree = null;
        for (Tree<Integer> tree : departmentTree) {
            final SysDepartmentTree sysDepartmentTree = (SysDepartmentTree) tree.get("sysDepartmentTree");
            final String deptCode = sysDepartmentTree.getDeptCode();
            if (deptCode.startsWith(DING_DING_PRE_CODE)) {
                befDingDingTree = tree;
                break;
            }
        }
        
        //构建新钉钉树
        final Set<String> deptNames = Sets.newHashSet();
        final Map<Long, String> deptNameMap = deptList.stream().collect(Collectors.toMap(OapiV2DepartmentListsubResponse.DeptBaseResponse::getDeptId, OapiV2DepartmentListsubResponse.DeptBaseResponse::getName));
        
        final List<Tree<String>> dingDingTree = TreeUtil.build(deptList, getDingDingPreCode("null"), (dept, treeNode) -> {
            treeNode.setId(getDingDingPreCode(dept.getDeptId()));
            String deptName = getDingDingPreName(dept.getName());
            if (deptNames.contains(deptName)) {
                deptName = deptName + "-" + deptNameMap.get(dept.getParentId());
                while (deptNames.contains(deptName)) {
                    //还重复加时间戳
                    final long currentTimeMillis = System.currentTimeMillis();
                    deptName = deptName + "-" + currentTimeMillis;
                }
            }
            treeNode.setName(deptName);
            treeNode.setParentId(getDingDingPreCode(dept.getParentId()));
            treeNode.setWeight(dept.getDeptId());
            //判断名字是否重复
            deptNames.add(Convert.toStr(treeNode.getName()));
        });
        
        //对比树形 并进行添加删除
        compareAddOrUpdate(0, befDingDingTree == null ? null : Lists.newArrayList(befDingDingTree), dingDingTree, currentReqUserId);
    }
    
    /**
     * 功能: 对比添加部门
     * 作者: zjt
     * 日期: 2021/12/17 16:42
     * 版本: 1.0
     */
    private void compareAddOrUpdate(int parentDeptId, List<Tree<Integer>> befDingDingTree, List<Tree<String>> dingDingTree, int currentReqUserId) {
        
        final Map<String, Tree<Integer>> sysDepartmentMap = Maps.newHashMap();
        
        if (befDingDingTree != null) {
            //构建 sysDepartmentMap
            for (Tree<String> tree : dingDingTree) {
                final String deptCode = tree.getId();
                final Iterator<Tree<Integer>> treeIterator = befDingDingTree.iterator();
                while (treeIterator.hasNext()) {
                    final Tree<Integer> next = treeIterator.next();
                    final SysDepartmentTree sysDepartmentTree = (SysDepartmentTree) next.get("sysDepartmentTree");
                    if (sysDepartmentTree.getDeptCode().equals(deptCode)) {
                        sysDepartmentMap.put(deptCode, next);
                    }
                }
            }
            
            //将不匹配的节点删除
            final Iterator<Tree<Integer>> treeIterator = befDingDingTree.iterator();
            while (treeIterator.hasNext()) {
                final Tree<Integer> next = treeIterator.next();
                final SysDepartmentTree sysDepartmentTree = (SysDepartmentTree) next.get("sysDepartmentTree");
                if (!sysDepartmentMap.containsKey(sysDepartmentTree.getDeptCode())) {
                    treeIterator.remove();
                    final Integer deptId = sysDepartmentTree.getId();
                    sysDepartmentService.deleteBatchDepartment(Lists.newArrayList(deptId), currentReqUserId);
                }
            }
        }
        
        int weight = 0;
        for (Tree<String> tree : dingDingTree) {
            final String deptCode = tree.getId();
            final Tree<Integer> befTree = sysDepartmentMap.get(deptCode);
            Integer departmentId = null;
            if (befTree != null) {
                //更新
                final SysDepartmentTree sysDepartmentTree = (SysDepartmentTree) befTree.get("sysDepartmentTree");
                final UpdateDepartmentDTO departmentDTO = new UpdateDepartmentDTO();
                departmentDTO.setDeptId(sysDepartmentTree.getId());
                departmentDTO.setDeptName(Convert.toStr(tree.getName()));
                departmentDTO.setDeptOrder(Math.min(weight++, 100));
                sysDepartmentService.updateDepartment(departmentDTO, currentReqUserId, false);
                departmentId = sysDepartmentTree.getId();
            } else {
                //新增
                final AddDepartmentDTO departmentDTO = new AddDepartmentDTO();
                departmentDTO.setDeptCode(tree.getId());
                departmentDTO.setDeptName(Convert.toStr(tree.getName()));
                departmentDTO.setDeptOrder(Math.min(weight++, 100));
                departmentDTO.setParentDeptId(parentDeptId);
                departmentDTO.setDeptType(parentDeptId == 0 ? DeptType.FIRST_LEVEL_DEPARTMENT.getType() : DeptType.SUB_DEPARTMENT.getType());
                departmentId = sysDepartmentService.addDepartment(departmentDTO, currentReqUserId);
            }
            
            final List<Tree<String>> children = tree.getChildren();
            if (CollectionUtil.isNotEmpty(children)) {
                compareAddOrUpdate(departmentId, befTree == null ? null : befTree.getChildren(), children, currentReqUserId);
            }
        }
        
    }
    
    
    /**
     * 功能: 入库钉钉角色数据
     * 作者: zjt
     * 日期: 2021/12/17 15:34
     * 版本: 1.0
     */
    private void importDingDingRoleData(Integer currentReqUserId, List<OapiRoleListResponse.OpenRoleGroup> openRoleGroupList) {
        final List<SysRole> sysRoles = sysRoleMapper.findall();
        final Map<String, SysRole> sysRoleMap = sysRoles.stream().collect(Collectors.toMap(SysRole::getRoleCode, Function.identity()));
        
        //先找到所有DD开头编码的角色 然后删除不用保留的角色
        final Set<String> dingDingSysRole = sysRoles.stream().filter(sysRole -> {
            return sysRole.getRoleCode().startsWith(DING_DING_PRE_CODE);
        }).map(SysRole::getRoleCode).collect(Collectors.toSet());
        
        for (OapiRoleListResponse.OpenRoleGroup openRoleGroup : openRoleGroupList) {
            final List<OapiRoleListResponse.OpenRole> roles = openRoleGroup.getRoles();
            for (OapiRoleListResponse.OpenRole role : roles) {
                final Long id = role.getId();
                dingDingSysRole.remove(getDingDingPreCode(id));
            }
        }
        
        //删除 钉钉那边不存在的角色
        Collection<Integer> deleteSysRoleIds = Lists.newArrayList();
        for (String sysRoleCode : dingDingSysRole) {
            final SysRole sysRole = sysRoleMap.get(sysRoleCode);
            deleteSysRoleIds.add(sysRole.getId());
        }
        sysRoleManagementService.deleteSysRole(deleteSysRoleIds);
        
        
        //这些都是要新增或者更新的角色
        for (OapiRoleListResponse.OpenRoleGroup openRoleGroup : openRoleGroupList) {
            final List<OapiRoleListResponse.OpenRole> roles = openRoleGroup.getRoles();
            for (OapiRoleListResponse.OpenRole role : roles) {
                final String sysRoleCode = getDingDingPreCode(role.getId());
                final SysRole sysRole = sysRoleMap.get(sysRoleCode);
                final String roleName = String.format("%s(%s)%s", role.getName(), openRoleGroup.getName(), DING_DING_PRE_NAME);
                if (sysRole != null) {
                    //更新
                    final UpdateSysRoleDTO updateSysRoleDTO = new UpdateSysRoleDTO();
                    updateSysRoleDTO.setSysRoleId(sysRole.getId());
                    updateSysRoleDTO.setRoleName(roleName);
                    updateSysRoleDTO.setRoleDataScope(RoleDataScopeType.THIS_DEPARTMENT_AND_ITS_SUBORDINATE_DEPTS.getType());
                    sysRoleManagementService.updateSysRole(updateSysRoleDTO, currentReqUserId);
                } else {
                    //新增
                    final AddSysRoleDTO addSysRoleDTO = new AddSysRoleDTO();
                    addSysRoleDTO.setRoleName(roleName);
                    addSysRoleDTO.setRoleDataScope(RoleDataScopeType.THIS_DEPARTMENT_AND_ITS_SUBORDINATE_DEPTS.getType());
                    addSysRoleDTO.setRoleCode(sysRoleCode);
                    sysRoleManagementService.addSysRole(addSysRoleDTO, currentReqUserId);
                }
            }
        }
    }
    
    /**
     * 功能: 获取钉钉角色数据
     * 作者: zjt
     * 日期: 2021/12/17 14:23
     * 版本: 1.0
     */
    private List<OapiRoleListResponse.OpenRoleGroup> getDingDingRoleData(String accessToken) {
        List<OapiRoleListResponse.OpenRoleGroup> openRoleGroupList = Lists.newArrayList();
        
        long offset = 0;
        long size = 200;
        
        while (true) {
            final OapiRoleListResponse roleListResponse = roleSDK.getRoleList(size, offset, accessToken);
            final OapiRoleListResponse.PageVo result = roleListResponse.getResult();
            final Boolean hasMore = result.getHasMore();
            openRoleGroupList.addAll(result.getList());
            if (hasMore) {
                offset = offset + size;
            } else {
                break;
            }
        }
        
        return openRoleGroupList;
    }
    
    /**
     * 功能: 获取钉钉部门数据
     * 作者: zjt
     * 日期: 2021/12/17 14:23
     * 版本: 1.0
     */
    private List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDingDingDeptData(String accessToken) {
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList = Lists.newArrayList();
        
        Stack<Long> deptIds = new Stack<Long>();
        deptIds.push(null);
        
        while (!deptIds.empty()) {
            final Long deptId = deptIds.pop();
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse deptBaseResponse : departmentSDK.getDeptList(deptId, accessToken).getResult()) {
                final Long parentDeptId = deptBaseResponse.getDeptId();
                deptIds.push(parentDeptId);
                deptList.add(deptBaseResponse);
            }
        }
        
        //顶级部门固定为1
        final OapiV2DepartmentGetResponse.DeptGetResponse deptInfo = departmentSDK.getDeptInfo(1L, accessToken).getResult();
        final OapiV2DepartmentListsubResponse.DeptBaseResponse topDeptBaseResponse = new OapiV2DepartmentListsubResponse.DeptBaseResponse();
        BeanUtil.copyProperties(deptInfo, topDeptBaseResponse);
        deptList.add(topDeptBaseResponse);
        return deptList;
    }
    
    /**
     * 功能: 获取钉钉用户数据
     * 作者: zjt
     * 日期: 2021/12/17 14:22
     * 版本: 1.0
     */
    private List<OapiV2UserGetResponse.UserGetResponse> getDingDingUserInfoData(String accessToken, List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList) throws ExecutionException, InterruptedException {
        List<OapiV2UserGetResponse.UserGetResponse> userInfoList = Lists.newArrayList();
        
        List<String> userIds = Lists.newArrayList();
        for (Long deptId : deptList.stream().map(OapiV2DepartmentListsubResponse.DeptBaseResponse::getDeptId).collect(Collectors.toSet())) {
            final OapiUserListidResponse.ListUserByDeptResponse listUserByDeptResponse = userSDK.getDeptUserList(deptId, accessToken).getResult();
            final List<String> userIdList = listUserByDeptResponse.getUseridList();
            userIds.addAll(userIdList);
        }
        //获取单个用户详情
        final List<Future<OapiV2UserGetResponse.UserGetResponse>> userGetResponseFutures = Lists.newArrayList();
        for (String userId : userIds) {
            final Future<OapiV2UserGetResponse.UserGetResponse> userGetResponseFuture = ThreadPoolUtil.submit(() -> {
                return userSDK.getUserInfo(userId, accessToken).getResult();
            });
            userGetResponseFutures.add(userGetResponseFuture);
        }
        
        for (Future<OapiV2UserGetResponse.UserGetResponse> userGetResponseFuture : userGetResponseFutures) {
            final OapiV2UserGetResponse.UserGetResponse userGetResponse = userGetResponseFuture.get();
            final String mobile = userGetResponse.getMobile();
            final String name = userGetResponse.getName();
            if (StringUtils.isBlank(mobile)) {
                throw new IllegalArgumentException(String.format("员工%s找不到手机号,请检查钉钉手机号权限是否开启", name));
            }
            userInfoList.add(userGetResponse);
        }
        
        //去掉userid相同的数据
        return Lists.newArrayList(userInfoList.stream().collect(Collectors.toMap(OapiV2UserGetResponse.UserGetResponse::getUserid, Function.identity(), (oldData, newData) -> oldData)).values());
    }
    

    
    /**
     * 功能: 获取钉钉前缀的名称
     * 作者: zjt
     * 日期: 2021/12/20 11:11
     * 版本: 1.0
     */
    private String getDingDingPreName(String name) {
        return name + DING_DING_PRE_NAME;
    }
    
    /**
     * 功能: 获取钉钉前缀的编码
     * 作者: zjt
     * 日期: 2021/12/20 11:11
     * 版本: 1.0
     */
    private String getDingDingPreCode(Object code) {
        return DING_DING_PRE_CODE + code;
    }
    
    /**
     * 功能: 获取钉钉用户所属部门
     * 作者: zjt
     * 日期: 2021/12/20 11:23
     * 版本: 1.0
     */
    private List<Integer> getDingDingUserDeptIds(List<OapiV2UserGetResponse.DeptLeader> deptLeaderInfos, boolean isAdmin) {
        final List<Integer> deptIds = Lists.newArrayList();
        if (CollectionUtil.isEmpty(deptLeaderInfos)) {
            return deptIds;
        }
        for (OapiV2UserGetResponse.DeptLeader leaderInDept : deptLeaderInfos) {
            final String dingDingDeptCode = getDingDingPreCode(leaderInDept.getDeptId());
            final Integer deptId = sysDepartmentMapper.findIdByDeptCode(dingDingDeptCode);
            
            if (deptId == null) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_45);
            }
            
            if (isAdmin) {
                if (leaderInDept.getLeader()) {
                    deptIds.add(deptId);
                }
            } else {
                deptIds.add(deptId);
            }
        }
        return deptIds;
    }
    
    
    /**
     * 功能: 获取钉钉用户角色
     * 作者: zjt
     * 日期: 2021/12/20 11:33
     * 版本: 1.0
     */
    private List<Integer> getDingDingRoleUserIds(List<OapiV2UserGetResponse.UserRole> roleList) {
        final List<Integer> roleIds = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(roleList)) {
            for (OapiV2UserGetResponse.UserRole userRole : roleList) {
                final Long userRoleId = userRole.getId();
                roleIds.addAll(sysRoleMapper.findIdByRoleCode(getDingDingPreCode(userRoleId)));
            }
        }
        return roleIds;
    }
    
    /**
     * 功能: 记录消息
     * 作者: zjt
     * 日期: 2021/12/20 15:36
     * 版本: 1.0
     */
    private void recordMessage(String uid, String messageId, String message, RecordMessageType recordMessageType, String errorMsg, boolean isLast, Integer currentReqUserId) {
        final RecordMessage recordMessage = new RecordMessage();
        recordMessage.setMessageId(messageId);
        recordMessage.setMessage(message);
        recordMessage.setRecordMessageType(recordMessageType);
        recordMessage.setErrorMsg(errorMsg);
        recordMessage.setIsLast(isLast);
        recordMessage.setUid(uid);
        
        RECORD_MESSAGE_MAP.compute(uid, (k, v) -> {
            if (v == null) {
                v = Lists.newLinkedList();
            }
            v.add(recordMessage);
            return v;
        });
        
        //发送websocket
        pcMessageUtil.sendMessageByOnLineUser("importDingDingUserData", JSONUtil.toJsonStr(recordMessage));
        
        if (isLast) {
            if (recordMessageType == RecordMessageType.SUCCESS) {
                sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "钉钉对接配置入库成功", "钉钉对接配置入库成功！", "钉钉对接配置入库成功", Lists.newArrayList(currentReqUserId), AnnPriorityType.VERY_HIGH);
            } else {
                
                List<String> items = Lists.newArrayList();
                
                final List<RecordMessage> recordMessageList = RECORD_MESSAGE_MAP.getOrDefault(uid, Lists.newArrayList());
                
                String befMessageId = "";
                ListIterator<RecordMessage> li = recordMessageList.listIterator();// 获得ListIterator对象
                for (li = recordMessageList.listIterator(); li.hasNext(); ) {// 将游标定位到列表结尾
                    li.next();
                }
                while (li.hasPrevious()) {// 逆序输出列表中的元素
                    final RecordMessage previous = li.previous();
                    final String nowMessageId = previous.getMessageId();
                    if (nowMessageId.equals(befMessageId)) {
                        li.remove();
                    }
                    befMessageId = nowMessageId;
                }
                
                
                for (RecordMessage rm : recordMessageList) {
                    String item = String.format("<tr>\n" + "      <td>%s</td>\n" + "      <td>%s</td>\n" + "      <td>%s</td>\n" + "   </tr>", rm.getMessage(), rm.getRecordMessageType().getName(), rm.getErrorMsg());
                    items.add(item);
                }
                
                
                String content = String.format("<table border=\"1\" style=\"border-collapse: collapse;\">\n" + "   <tr>\n" + "      " + "<th>名称</th>\n" + "      <th>状态</th>\n" + "      <th>原因</th>\n" + "" + "    </tr>\n" + "%s" + "</table>", String.join("\n", items));
                sysAnnouncementSenderService.sendAnnouncementAsync(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "钉钉对接配置入库失败", "钉钉对接配置入库失败", content, Lists.newArrayList(currentReqUserId), AnnPriorityType.VERY_HIGH);
            }
            
            RECORD_MESSAGE_MAP.remove(uid);
        }
        
    }
    
    /**
     * 功能: 绑定手机号和邮箱
     * 作者: zjt
     * 日期: 2021/12/29 11:59
     * 版本: 1.0
     */
    private void bindPhoneAndEmail(int sysUserId, String phone, String email) {
        final SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (StringUtils.isNotBlank(phone)) {
            sysUser.setUserPhone(phone);
            sysUser.setUserSafePhone(phone);
            
            //如果其他人绑定了相同的手机号 解绑其他号
            final List<SysUser> sysUserList = sysUserMapper.findByUserSafePhone(phone);
            for (SysUser user : sysUserList) {
                if (!user.getId().equals(sysUserId)) {
                    //如果和admin一样绑定了一个手机要提升用户
                    if (user.getId() == 1) {
                        throw new CommonException(new ErrorCode(999, sysUser.getUserName() + "该钉钉账号与最高管理员账号绑定了相同的手机号，导入失败，系统已回退至保存前状态，请在更改系统内最高管理员账号的绑定手机或该钉钉账号的绑定手机后，再次进行导入"));
                    }
                    sysUserService.unbindPhone(user.getId());
                }
            }
        }
        
        if (StringUtils.isNotBlank(email)) {
            sysUser.setUserEmail(email);
        }
        
        sysUserMapper.updateById(sysUser);
    }
    
    /**
     * 功能: 获取用户头像
     * 作者: zjt
     * 日期: 2021/12/29 14:07
     * 版本: 1.0
     */
    private byte[] getUserAvatar(String avatarUrl) throws IOException {
        if (StringUtils.isBlank(avatarUrl)) {
            return null;
        } else {
            URL url = new URL(avatarUrl);
            final BufferedImage bufferedImage = ImageIO.read(url);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
    
}