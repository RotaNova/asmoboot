package com.rotanava.boot.system.module.system.impl.sysuser;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetbyunionidResponse;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.DeptRoleManagementService;
import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.SetAccountService;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysDepartmentService;
import com.rotanava.boot.system.api.SysDingDingConfigService;
import com.rotanava.boot.system.api.SysServiceSettingService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.constant.enums.UserIsOnlineStatus;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.BannerFrequency;
import com.rotanava.boot.system.api.module.constant.BannerOption;
import com.rotanava.boot.system.api.module.constant.DeptManageStatus;
import com.rotanava.boot.system.api.module.constant.EmailSafeType;
import com.rotanava.boot.system.api.module.constant.LastLoginInfoPromptOn;
import com.rotanava.boot.system.api.module.constant.LoginAccessType;
import com.rotanava.boot.system.api.module.constant.LoginIpFilteringOn;
import com.rotanava.boot.system.api.module.constant.LoginLockoutStrategyOn;
import com.rotanava.boot.system.api.module.constant.LoginStatus;
import com.rotanava.boot.system.api.module.constant.PassMandatoryModificationOn;
import com.rotanava.boot.system.api.module.constant.PhoneSafeType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.constant.SysServiceType;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.constant.UserSysRole;
import com.rotanava.boot.system.api.module.system.bean.DeptRoleLabel;
import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysRoleLabel;
import com.rotanava.boot.system.api.module.system.bo.AccountSafeSetting;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRole;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRoleUser;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysOnlineUser;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserDepartment;
import com.rotanava.boot.system.api.module.system.bo.SysUserRole;
import com.rotanava.boot.system.api.module.system.bo.UserLoginInfo;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.AuthUserDeptRoleDTO;
import com.rotanava.boot.system.api.module.system.dto.CheckRetrievePwCaptchaDTO;
import com.rotanava.boot.system.api.module.system.dto.DingDingAuthDTO;
import com.rotanava.boot.system.api.module.system.dto.FirstPasswordDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessTokenDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListDeptUserAddDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.IntegrityVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.ListUserLoginInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.PassWordLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.PhoneLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.RetrievePwDTO;
import com.rotanava.boot.system.api.module.system.dto.SecondaryVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.UserAccountNameDTO;
import com.rotanava.boot.system.api.module.system.dto.VerifyPhoneDTO;
import com.rotanava.boot.system.api.module.system.vo.AccessInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AccessTokenVO;
import com.rotanava.boot.system.api.module.system.vo.AccountSafeSettingVO;
import com.rotanava.boot.system.api.module.system.vo.DdScanLoginParamVO;
import com.rotanava.boot.system.api.module.system.vo.GetBindInfoVO;
import com.rotanava.boot.system.api.module.system.vo.PlatformSettingVO;
import com.rotanava.boot.system.api.module.system.vo.SysDingDingConfigVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.boot.system.api.module.system.vo.UserDepartmentInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginVO;
import com.rotanava.boot.system.module.dao.AccountSafeSettingMapper;
import com.rotanava.boot.system.module.dao.OpenAppMapper;
import com.rotanava.boot.system.module.dao.SysDepartRoleMapper;
import com.rotanava.boot.system.module.dao.SysDepartRoleUserMapper;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysOnlineUserMapper;
import com.rotanava.boot.system.module.dao.SysRoleMapper;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import com.rotanava.boot.system.module.dao.SysUserDepartmentMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.boot.system.module.dao.SysUserRoleMapper;
import com.rotanava.boot.system.module.dao.UserLoginInfoMapper;
import com.rotanava.boot.system.module.system.impl.SysDingDingConfigServiceImpl;
import com.rotanava.boot.system.module.system.mq.MqTransactionalMessageSender;
import com.rotanava.boot.system.module.system.mq.SysUserListenter;
import com.rotanava.dingding.sdk.AuthSDK;
import com.rotanava.dingding.sdk.UserSDK;
import com.rotanava.dingding.service.DingTalkService;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.constant.CacheConstant;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.enums.SingleSignOn;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.AppErrorCode;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.util.AESUtil;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.ImageUtil;
import com.rotanava.framework.util.JwtUtil;
import com.rotanava.framework.util.MD5Tools;
import com.rotanava.boot.system.util.MailUtil;
import com.rotanava.framework.util.PageUtils;
import com.rotanava.framework.util.PasswordUtil;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.boot.system.util.sms.SendMsgUtil;
import lombok.extern.log4j.Log4j2;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 系统用户实现类
 *
 * @author richenLi
 * @date 2021-03-01
 */
@Service
@Validated
@DubboService
@Log4j2
@Transactional(rollbackFor = Throwable.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysOnlineUserMapper sysOnlineUserMapper;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    @Lazy
    private SetAccountService accountService;

    @Autowired
    private SysServiceSettingMapper sysServiceSettingMapper;

    @Autowired
    private SysDepartRoleUserMapper sysDepartRoleUserMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AccountSafeSettingMapper accountSafeSettingMapper;

    @Autowired
    private SysServiceSettingService sysServiceSettingService;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;

    @Autowired
    private MqTransactionalMessageSender mqTransactionalMessageSender;

    @Autowired
    private ManageSecurityService manageSecurityService;

    @Autowired
    @Lazy
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private DeptRoleManagementService deptRoleManagementService;

    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    @Autowired
    private OpenAppMapper openAppMapper;

    @Autowired
    @Lazy
    private SysDingDingConfigService sysDingDingConfigService;

    @Autowired
    private AuthSDK authSDK;

    @Autowired
    private UserSDK userSDK;

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private SendMsgUtil sendMsgUtil;

    @Autowired
    @Lazy
    private GlobalClass globalClass;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer addSysUser(@Validated AddSysUserDTO addSysUserDTO, @Nullable byte[] photoBytes, int userId, boolean isEncryptPawword) throws Exception {

        //解密密码
        if (isEncryptPawword) {
            addSysUserDTO.setUserPassword(AESUtil.Decrypt(addSysUserDTO.getUserPassword(), CommonConstant.AES_DECRYPTION_KEY));
        }

        final SysUser sysUser = new SysUser();
        sysUser.setUserName(addSysUserDTO.getUserName());
        sysUser.setUserAccountName(addSysUserDTO.getUserAccountName());
        sysUser.setUserPasswdSalt(PasswordUtil.randomGen(8));
        sysUser.setUserPassword(PasswordUtil.encrypt(addSysUserDTO.getUserAccountName(), addSysUserDTO.getUserPassword(), sysUser.getUserPasswdSalt()));
        sysUser.setUserSysrole(addSysUserDTO.getUserSysrole());
        sysUser.setUserCode(addSysUserDTO.getUserCode());
        if (addSysUserDTO.getUserValidTime() == null) {
            addSysUserDTO.setUserValidTime(4102329600000L);
        }
        sysUser.setUserValidTime(new Date(addSysUserDTO.getUserValidTime()));
        sysUser.setUserSex(addSysUserDTO.getUserSex());
        sysUser.setUserOccupation(addSysUserDTO.getUserOccupation());
        sysUser.setUserPhone(addSysUserDTO.getUserPhone());
        sysUser.setUserEmail(addSysUserDTO.getUserEmail());
        sysUser.setUserAddress(addSysUserDTO.getUserAddress());
        sysUser.setUserBirthday(Optional.ofNullable(addSysUserDTO.getUserBirthday()).map(Date::new).orElse(null));
        sysUser.setUserStatus(UserStatus.INACTIVATED.getStatus());
        sysUser.setUserIsOnline(UserIsOnlineStatus.OFFLINE.getStatus());
        sysUser.setUserStatus(UserStatus.INACTIVATED.getStatus());
        sysUser.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        sysUser.setCreateBy(userId);
        sysUser.setCreateTime(new Date());

        //头像
        if (photoBytes != null) {
            final UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.HEAD_URL_BUCKET, new ByteArrayInputStream(photoBytes), "");
            sysUser.setPhotoBucketName(uploadResultBean.getBucketName());
            sysUser.setPhotoObjectName(uploadResultBean.getObjName());
        }

        checkSysUser(null, sysUser);

        sysUserMapper.insert(sysUser);
        final Integer sysUserId = sysUser.getId();

        //设置有效期
        setStatus(sysUser, true);

        //用户所属部门
        setResponsibleDeptList(sysUserId, DeptManageStatus.fromValue(addSysUserDTO.getUserSysrole()), addSysUserDTO.getSysDepartmentIdList(), addSysUserDTO.getResponsibleDeptList());

        //用户角色
        if (!CollectionUtils.isEmpty(addSysUserDTO.getSysRoleIdList())) {
            for (Integer sysRoleId : addSysUserDTO.getSysRoleIdList()) {
                final SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysRoleId(sysRoleId);
                sysUserRole.setSysUserId(sysUserId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }

        //创建账号的时候初始化 account_safe_setting
        final AccountSafeSetting accountSafeSetting = new AccountSafeSetting();
        accountSafeSetting.setSysUserId(sysUser.getId());
        accountSafeSetting.setPhoneSafeType(PhoneSafeType.NONE.getType());
        accountSafeSetting.setEmailSafeType(EmailSafeType.NONE.getType());
        accountSafeSetting.setAccountPrePasswdTime(new Date());
        accountSafeSettingMapper.insert(accountSafeSetting);

        return sysUser.getId();
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysUser(UpdateSysUserDTO updateSysUserDTO, byte[] photoBytes, int userId) {
        final SysUser sysUser = sysUserMapper.selectById(updateSysUserDTO.getSysUserId());
        if (sysUser != null) {
            final Integer sysUserId = sysUser.getId();
            sysUser.setUserName(updateSysUserDTO.getUserName());
            sysUser.setUserSysrole(updateSysUserDTO.getUserSysrole());
            if (updateSysUserDTO.getUserValidTime() == null) {
                updateSysUserDTO.setUserValidTime(4102329600000L);
            }
            sysUser.setUserValidTime(new Date(updateSysUserDTO.getUserValidTime()));


            sysUser.setUserSex(updateSysUserDTO.getUserSex());
            sysUser.setUserOccupation(updateSysUserDTO.getUserOccupation());
            sysUser.setUserPhone(updateSysUserDTO.getUserPhone());
            sysUser.setUserEmail(updateSysUserDTO.getUserEmail());
            sysUser.setUserAddress(updateSysUserDTO.getUserAddress());
            sysUser.setUserStatus(UserStatus.INACTIVATED.getStatus());
            sysUser.setUserBirthday(Optional.ofNullable(updateSysUserDTO.getUserBirthday()).map(Date::new).orElse(null));
            sysUser.setUpdateBy(userId);
            sysUser.setUpdateTime(new Date());

            //头像
            if (photoBytes != null) {
                final UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.HEAD_URL_BUCKET, new ByteArrayInputStream(photoBytes), "");
                sysUser.setPhotoBucketName(uploadResultBean.getBucketName());
                sysUser.setPhotoObjectName(uploadResultBean.getObjName());
            }

            checkSysUser(sysUserId, sysUser);

            //设置有效期
            setStatus(sysUser, false);

            sysUserMapper.updateById(sysUser);

            //设置用户所属部门角色
            final AuthUserDeptRoleDTO authUserDeptRoleDTO = new AuthUserDeptRoleDTO();
            authUserDeptRoleDTO.setDeptId(updateSysUserDTO.getDeptId());
            authUserDeptRoleDTO.setDeptRoleIdList(updateSysUserDTO.getDeptRoleIdList());
            if (updateSysUserDTO.getDeptId() != null && updateSysUserDTO.getDeptRoleIdList().size() > 0) {
                deptRoleManagementService.authUserDeptRole(authUserDeptRoleDTO);
            }

            //用户角色
            sysUserRoleMapper.deleteBySysUserId(sysUserId);

            //管理员不能删除id为1 的角色
            if (sysUserId == 1) {
                updateSysUserDTO.getSysRoleIdList().add(1);
            }

            if (!CollectionUtils.isEmpty(updateSysUserDTO.getSysRoleIdList())) {
                for (Integer sysRoleId : updateSysUserDTO.getSysRoleIdList()) {
                    final SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setSysRoleId(sysRoleId);
                    sysUserRole.setSysUserId(sysUserId);
                    sysUserRoleMapper.insert(sysUserRole);
                }
            }

            //用户所属系统部门
            setResponsibleDeptList(sysUserId, DeptManageStatus.fromValue(updateSysUserDTO.getUserSysrole()), updateSysUserDTO.getSysDepartmentIdList(), updateSysUserDTO.getResponsibleDeptList());


        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }

    /**
     * 功能: 设置状态
     * 作者: zjt
     * 日期: 2021/4/13 10:11
     * 版本: 1.0
     */
    private void setStatus(SysUser sysUser, boolean insert) {
        final Date userValidTime = sysUser.getUserValidTime();
        if (userValidTime.before(new Date())) {
            sysUser.setUserStatus(UserStatus.EXPIRED.getStatus());
        } else {
            if (UserStatus.FREEZE.getStatus().equals(sysUser.getBefUserStatus())) {
                sysUser.setUserStatus(UserStatus.FREEZE.getStatus());
            } else {
                if (insert) {
                    //判断 初始密码强制修改开关:0-关闭;1-开启
                    //如果是关闭则为正常
                    if ("0".equals(sysServiceSettingMapper.findSysServiceValueBySysServiceCode("pass_mandatory_modification_on"))) {
                        sysUser.setUserStatus(UserStatus.NORMAL.getStatus());
                    } else {
                        sysUser.setUserStatus(UserStatus.INACTIVATED.getStatus());
                    }
                } else {
                    sysUser.setUserStatus(UserStatus.NORMAL.getStatus());
                }
            }
            mqTransactionalMessageSender.insertMqTransactionalMessage(SysUserListenter.EXPIRATION_TIME_QUEUE, Convert.toStr(sysUser.getId()), userValidTime.getTime() - System.currentTimeMillis());
        }
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 功能: 检查编号
     * 作者: zjt
     * 日期: 2021/3/20 11:58
     * 版本: 1.0
     */
    private void checkSysUser(Integer userId, SysUser sysUser) {
        final String userName = sysUser.getUserName();
        final String userAccountName = sysUser.getUserAccountName();
        final String userCode = sysUser.getUserCode();
        final String userEmail = sysUser.getUserEmail();
        final String userPhone = sysUser.getUserPhone();

        final List<Integer> userAccountNameList = sysUserMapper.findIdByUserAccountName(userAccountName);
        doCheck(userId, userAccountNameList, userAccountName + "系统用户登录账户名重复");
//        final List<Integer> userNameList = sysUserMapper.findIdByUserName(userName);
//        doCheck(userId, userNameList, userName + "系统显示的用户昵称重复");
        final List<Integer> userCodeList = sysUserMapper.findIdByUserCode(userCode);
        doCheck(userId, userCodeList, userCode + "系统用户的编号重复");

        if (!StringUtils.isEmpty(userPhone)) {
            final List<Integer> userPhoneList = sysUserMapper.findIdByUserPhone(userPhone);
            doCheck(userId, userPhoneList, userPhone + "系统用户联系手机号重复");
        }

        if (!StringUtils.isEmpty(userEmail)) {
            final List<Integer> userEmailList = sysUserMapper.findIdByUserEmail(userEmail);
            doCheck(userId, userEmailList, userEmail + "系统用户邮件重复");
        }
    }

    /**
     * 功能: 异常就抛出 throwStr
     * 作者: zjt
     * 日期: 2021/3/20 14:16
     * 版本: 1.0
     */
    private void doCheck(Integer userId, List<Integer> userIdList, String throwStr) {
        if (userId != null) {
            userIdList.remove(userId);
        }

        if (!userIdList.isEmpty()) {
            throw new CommonException(new ErrorCode(100100, throwStr));
        }
    }

    /**
     * 功能: 设置所属部门
     * 作者: zjt
     * 日期: 2021/3/9 19:07
     * 版本: 1.0
     */
    private void setResponsibleDeptList(int sysUserId, DeptManageStatus deptManageStatus, Collection<Integer> deptList, Collection<Integer> responsibleDeptList) {

        if (deptManageStatus == DeptManageStatus.ADMINISTRATOR && !deptList.containsAll(responsibleDeptList)) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_40);
        }

        //用户当前拥有的部门
        final List<Integer> departmentIdList = sysUserDepartmentMapper.findSysDepartmentIdBySysUserId(sysUserId);
        departmentIdList.removeAll(deptList);

        //获取要删除的部门
        for (Integer deptId : departmentIdList) {
            sysDepartmentService.departmentUnlinkUser(deptId, Collections.singletonList(sysUserId));
        }

        sysUserDepartmentMapper.deleteBySysUserId(sysUserId);
        for (Integer departmentId : deptList) {
            final SysUserDepartment sysUserDepartment = new SysUserDepartment();
            sysUserDepartment.setSysUserId(sysUserId);
            sysUserDepartment.setSysDepartmentId(departmentId);
            if (responsibleDeptList.contains(departmentId)) {
                sysUserDepartment.setDeptManage(deptManageStatus.getStatus());
            } else {
                sysUserDepartment.setDeptManage(DeptManageStatus.NOT_ADMINISTRATOR.getStatus());
            }
            sysUserDepartmentMapper.insert(sysUserDepartment);
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public SysUserInfoVO getSysUserInfo(int sysUserId, Integer deptId) {
        final SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (sysUser != null) {
            final SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
            final Integer userId = sysUser.getId();
            sysUserInfoVO.setSysUserId(userId);
            sysUserInfoVO.setUserAddress(sysUser.getUserAddress());
            sysUserInfoVO.setUserBirthday(Optional.ofNullable(sysUser.getUserBirthday()).map(Date::getTime).orElse(null));
            sysUserInfoVO.setUserName(sysUser.getUserName());
            sysUserInfoVO.setUserAccountName(sysUser.getUserAccountName());
            sysUserInfoVO.setUserOccupation(sysUser.getUserOccupation());
            sysUserInfoVO.setUserEmail(sysUser.getUserEmail());
            sysUserInfoVO.setUserPhone(sysUser.getUserPhone());
            sysUserInfoVO.setUserSex(sysUser.getUserSex());
            sysUserInfoVO.setUserSysrole(sysUser.getUserSysrole());
            sysUserInfoVO.setUserCode(sysUser.getUserCode());
            sysUserInfoVO.setUserValidTime(sysUser.getUserValidTime().getTime());

            final String photoBucketName = sysUser.getPhotoBucketName();
            final String photoObjectName = sysUser.getPhotoObjectName();

            if (!StringUtils.isEmpty(photoBucketName) && !StringUtils.isEmpty(photoBucketName)) {
                sysUserInfoVO.setAvatarUrl(fileUploadUtil.getObjUrl(photoBucketName, photoObjectName, 60));
            }


            final String userSafePhone = sysUser.getUserSafePhone();
            sysUserInfoVO.setBindPhoneFlag(!StringUtils.isEmpty(userSafePhone));

            final String userSafeEmail = sysUser.getUserSafeEmail();
            sysUserInfoVO.setVerificationEmailFlag(!StringUtils.isEmpty(userSafeEmail));


            //关联的系统角色
            final List<Integer> sysRoleIdList = sysUserRoleMapper.findSysRoleIdBySysUserId(userId);
            final List<SysRoleLabel> sysRoleLabelList = sysRoleIdList.stream().map(sysRoleId -> {
                final SysRoleLabel sysRoleLabel = new SysRoleLabel();
                final String roleName = sysRoleMapper.findRoleNameById(sysRoleId);
                sysRoleLabel.setSysRoleId(sysRoleId);
                sysRoleLabel.setSysRoleName(roleName);
                return sysRoleLabel;
            }).collect(Collectors.toList());
            sysUserInfoVO.setSysRoleLabelList(sysRoleLabelList);

            //关联的部门角色
            List<DeptRoleLabel> deptRoleLabels = Lists.newArrayList();
            if (deptId != null) {
                final List<SysDepartRoleUser> sysDepartRoleUserList = sysDepartRoleUserMapper.findBySysUserId(sysUserId);
                final List<Integer> departRoleIdList = sysDepartRoleUserList.stream().map(SysDepartRoleUser::getDepartRoleId).collect(Collectors.toList());
                if (departRoleIdList.size() > 0) {
                    final List<SysDepartRole> sysDepartRoles = sysDepartRoleMapper.selectBatchIds(departRoleIdList);
                    deptRoleLabels = sysDepartRoles.stream().filter(sysDepartRole -> sysDepartRole.getSysDepartmentId().equals(deptId)).map(sysDepartRole -> {
                        final DeptRoleLabel deptRoleLabel = new DeptRoleLabel();
                        deptRoleLabel.setDeptRoleId(sysDepartRole.getId());
                        deptRoleLabel.setDeptRoleName(sysDepartRole.getDepartRoleName());
                        return deptRoleLabel;
                    }).collect(Collectors.toList());
                }
            }
            sysUserInfoVO.setDeptRoleLabelList(deptRoleLabels);

            //用户所属部门
            final List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.findAllBySysUserId(userId);
            final List<SysDepartmentLabel> sysDepartmentLabelList = sysUserDepartmentList.stream().map(sysUserDepart -> {
                final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
                final String deptName = sysDepartmentMapper.findDeptNameById(sysUserDepart.getSysDepartmentId());
                sysDepartmentLabel.setSysDepartmentId(sysUserDepart.getSysDepartmentId());
                sysDepartmentLabel.setSysDepartmentName(deptName);
                return sysDepartmentLabel;
            }).collect(Collectors.toList());
            sysUserInfoVO.setSysDepartmentLabelList(sysDepartmentLabelList);

            //负责部门
            List<SysDepartmentLabel> sysDepartmentLabels = Lists.newArrayList();
            if (sysUser.getUserSysrole().equals(UserSysRole.ADMINISTRATOR.getType())) {
                sysDepartmentLabels = sysUserDepartmentList.stream().filter(sysUserDepart -> {
                    final Integer deptManage = sysUserDepart.getDeptManage();
                    return deptManage != null && deptManage.equals(DeptManageStatus.ADMINISTRATOR.getStatus());
                }).map(sysUserDepart -> {
                    final SysDepartmentLabel sysDepartmentLabel = new SysDepartmentLabel();
                    final String deptName = sysDepartmentMapper.findDeptNameById(sysUserDepart.getSysDepartmentId());
                    sysDepartmentLabel.setSysDepartmentId(sysUserDepart.getSysDepartmentId());
                    sysDepartmentLabel.setSysDepartmentName(deptName);
                    return sysDepartmentLabel;
                }).collect(Collectors.toList());
            }
            sysUserInfoVO.setResponsibleDeptLabelList(sysDepartmentLabels);

            final ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
            sysUserInfoVO.setPasswordStrength(manageSecurity.getPasswordStrength());

            return sysUserInfoVO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public IPage<SysUserItemVO> getListSysUser(GetListSysUserDTO sysUserDTO) {

        final QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(sysUserDTO);
        queryWrapper.eq("a.user_delete_status", sysUserDTO.getUserDeleteStatus());

        if (sysUserDTO.getDeptId() != null) {
            queryWrapper.eq("b.sys_department_id", sysUserDTO.getDeptId());
        }

        if (sysUserDTO.getUserSysrole() != null) {
            queryWrapper.eq("a.user_sysrole", sysUserDTO.getUserSysrole());
        }

        if (!StringUtils.isEmpty(sysUserDTO.getDeptName())) {
            queryWrapper.eq("c.dept_name", sysUserDTO.getDeptName());
        }

        queryWrapper.groupBy("a.id");

        final IPage<SysUser> sysUserIPage = sysUserMapper.queryPage(queryWrapper, PageUtils.startPage(sysUserDTO));

        return getUserItemVOIPage(sysUserIPage, sysUserDTO.getDeptId());
    }

    @Override
    public IPage<SysUserItemVO> getNotAddListSysUser(GetListDeptUserAddDTO listSysUserDTO) {
        final QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(listSysUserDTO);
        final List<Integer> sysUserIdList = sysUserDepartmentMapper.findSysUserIdBySysDepartmentId(listSysUserDTO.getDeptId());
        if (!CollectionUtils.isEmpty(sysUserIdList)) {
            queryWrapper.notIn("a.id", sysUserIdList);
        }
        queryWrapper.eq("a.user_delete_status", UserDeleteStatus.NOT_DELETED.getStatus());
        queryWrapper.groupBy("a.id");
        final IPage<SysUser> notAddListSysUser = sysUserMapper.queryPage(queryWrapper, PageUtils.startPage(listSysUserDTO));
        return getUserItemVOIPage(notAddListSysUser, listSysUserDTO.getDeptId());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public IPage<SysUserItemVO> getUserItemVOIPage(IPage<SysUser> sysUserIPage, Integer deptId) {
        final List<SysUserItemVO> sysUserItemVOList = sysUserIPage.getRecords().stream().map(sysUser -> {
            final SysUserItemVO sysUserItemVO = new SysUserItemVO();
            final Integer sysUserId = sysUser.getId();
            sysUserItemVO.setSysUserId(sysUserId);
            sysUserItemVO.setUserSex(sysUser.getUserSex());
            sysUserItemVO.setUserCode(sysUser.getUserCode());
            sysUserItemVO.setUserName(sysUser.getUserName());
            sysUserItemVO.setUserPhone(sysUser.getUserPhone());
            sysUserItemVO.setUserStatus(sysUser.getUserStatus());
            sysUserItemVO.setUserSysrole(sysUser.getUserSysrole());
            sysUserItemVO.setUserAccountName(sysUser.getUserAccountName());

            //系统角色名称
            final List<Integer> sysRoleIdList = sysUserRoleMapper.findSysRoleIdBySysUserId(sysUserId);
            String sysRoleStr = sysRoleIdList.stream().map(sysRoleMapper::findRoleNameById).collect(Collectors.joining(","));
            sysUserItemVO.setSysRoleStr(sysRoleStr);

            //部门角色名称
            final List<SysDepartRole> sysDepartRoleList = sysDepartRoleMapper.findBySysUserId(sysUserId);
            final Map<Integer, String> deptRoleStrMap = sysDepartRoleList.stream().collect(Collectors.groupingBy(SysDepartRole::getSysDepartmentId, Collectors.mapping(SysDepartRole::getDepartRoleName, Collectors.joining(","))));
            sysUserItemVO.setDeptRoleStrMap(deptRoleStrMap);


            //部门名称
            final List<Integer> departmentIdList = sysUserDepartmentMapper.findSysDepartmentIdBySysUserId(sysUserId);
            String sysDepartmentStr = departmentIdList.stream().map(sysDepartmentMapper::findDeptNameById).collect(Collectors.joining(","));
            sysUserItemVO.setSysDepartmentStr(sysDepartmentStr);


            //前端不会取 我帮他
            if (deptId != null) {
                sysUserItemVO.setDeptRoleStr(sysUserItemVO.getDeptRoleStrMap().get(deptId));
            }

            return sysUserItemVO;
        }).collect(Collectors.toList());

        return PageUtils.conversionIpageRecords(sysUserIPage, SysUserItemVO.class, sysUserItemVOList);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysUser(List<Integer> sysUserIdList, int userId) {
        for (Integer sysUserId : sysUserIdList) {
            //1 不能删
            if (sysUserId != 1) {
                if (sysUserId == userId) {
                    throw new CommonException(ParamErrorCode.PARAM_ERROR_50);
                }

                final SysUser sysUser = new SysUser();
                sysUser.setId(sysUserId);
                sysUser.setUpdateTime(new Date());
                sysUser.setUpdateBy(userId);
                sysUser.setUserDeleteStatus(UserDeleteStatus.DELETED.getStatus());
                sysUserMapper.updateById(sysUser);

                //踢人
                kickOffTheAssemblyLine(sysUser.getUserAccountName());

            } else {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_50);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void thoroughDeleteSysUser(List<Integer> sysUserIdList, int userId) {

        if (CollectionUtils.isEmpty(sysUserIdList)) {
            return;
        }

        if (sysUserIdList.contains(1)) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_97);
        } else if (sysUserIdList.contains(userId)) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_50);
        }


        sysUserMapper.deleteBatchIds(sysUserIdList);
        sysUserDepartmentMapper.deleteBySysUserIdIn(sysUserIdList);
        sysDepartRoleUserMapper.deleteBySysUserIdIn(sysUserIdList);
        accountSafeSettingMapper.deleteBySysUserIdIn(sysUserIdList);

        for (Integer sysUserId : sysUserIdList) {
            final SysUser sysUser = sysUserMapper.selectById(sysUserId);
            if (sysUser != null) {
                //踢人
                kickOffTheAssemblyLine(sysUser.getUserAccountName());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void restoreUser(List<Integer> sysUserIdList, int userId) {
        for (Integer sysUserId : sysUserIdList) {
            final SysUser sysUser = new SysUser();
            sysUser.setId(sysUserId);
            sysUser.setUpdateTime(new Date());
            sysUser.setUpdateBy(userId);
            sysUser.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
            sysUserMapper.updateById(sysUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void freezeSysUser(List<Integer> sysUserIdList, int userId) {
        freezeOrUnFreezeSysUserStatus(sysUserIdList, UserStatus.FREEZE.getStatus(), userId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void unfreezeSysUser(List<Integer> sysUserIdList, int userId) {
        freezeOrUnFreezeSysUserStatus(sysUserIdList, UserStatus.NORMAL.getStatus(), userId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void restPassword(int sysUserId, String password, int userId) {
        final SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (sysUser != null) {
            accountService.updatePassword(sysUser, password);
        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }


    /**
     * 功能: 更新系统用户状态
     * 作者: zjt
     * 日期: 2021/3/4 17:55
     * 版本: 1.0
     */
    private void freezeOrUnFreezeSysUserStatus(List<Integer> sysUserIdList, int sysUserStatus, int userId) {

        for (Integer sysUserId : sysUserIdList) {
            final SysUser sysUser = sysUserMapper.selectById(sysUserId);
            if (sysUser != null) {
                //勾选了冻结/过期的用户，冻结失败。
                //勾选了正常/过期的用户，解冻失败。

                if (sysUserStatus == UserStatus.FREEZE.getStatus()) {

                    if (sysUserId == userId) {
                        throw new CommonException(CommonErrorCode.COMMON_ERROR_39);
                    }


                    //冻结
                    if (sysUser.getUserStatus().equals(UserStatus.FREEZE.getStatus()) || sysUser.getUserStatus().equals(UserStatus.EXPIRED.getStatus())) {
                        throw new CommonException(CommonErrorCode.COMMON_ERROR_37);
                    }
                    if (UserStatus.FREEZE.getStatus().equals(sysUser.getBefUserStatus())) {
                        sysUserStatus = UserStatus.FREEZE.getStatus();
                    }

                    sysUser.setBefUserStatus(UserStatus.FREEZE.getStatus());

                    //下线
                    kickOffTheAssemblyLine(sysUser.getUserAccountName());
                } else if (sysUserStatus == UserStatus.NORMAL.getStatus()) {
                    //解冻
                    if (sysUser.getUserStatus().equals(UserStatus.NORMAL.getStatus()) || sysUser.getUserStatus().equals(UserStatus.EXPIRED.getStatus())) {
                        throw new CommonException(CommonErrorCode.COMMON_ERROR_36);
                    }
                    sysUser.setBefUserStatus(UserStatus.NORMAL.getStatus());
                }

                sysUser.setUserStatus(sysUserStatus);
                sysUser.setUpdateBy(userId);
                sysUser.setUpdateTime(new Date());
                sysUserMapper.updateById(sysUser);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserLoginVO passWordLogin(PassWordLoginDTO passWordLoginDTO, String header, String ipAddr) {
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(passWordLoginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        UserLoginInfo userLoginInfo = getUserLoginInfo(passWordLoginDTO.getUserAccountName(), passWordLoginDTO.getLoginLocation(), header, ipAddr, sysUser);

        checkLoginPassWordAndLocking(passWordLoginDTO, userLoginInfo, header, ipAddr, sysUser, manageSecurity);

        if (PassMandatoryModificationOn.TURN_ON.getOn().equals(manageSecurity.getPassMandatoryModificationOn()) && UserStatus.INACTIVATED.getStatus().equals(sysUser.getUserStatus())) {
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setUserStatus(UserStatus.INACTIVATED.getStatus());
            userLoginVO.setPasswordStrength(manageSecurity.getPasswordStrength());
            return userLoginVO;
        }


        checkLogin(userLoginInfo, header, ipAddr, sysUser, manageSecurity);

        AccountSafeSetting accountSafeSetting = accountSafeSettingMapper.findBySysUserId(sysUser.getId());
        if (!EmailSafeType.NONE.getType().equals(accountSafeSetting.getEmailSafeType()) || !PhoneSafeType.NONE.getType().equals(accountSafeSetting.getPhoneSafeType())) {
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setSecondaryVerification(1);
            try {
                userLoginVO.setUserAccountName(AESUtil.Encrypt(passWordLoginDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userLoginVO;
        }

        return doUserLogin(header, ipAddr, sysUser, manageSecurity, userLoginInfo);
    }

    /**
     * 功能: 进行登录并获取用户登陆token和信息
     * 作者: zjt
     * 日期: 2021/12/29 10:45
     * 版本: 1.0
     */
    private UserLoginVO doUserLogin(String header, String ipAddr, SysUser sysUser, ManageSecurity manageSecurity, UserLoginInfo userLoginInfo) {
        UserLoginInfo oldUserLoginInfo = userLoginInfoMapper.findByLoginUserIdOrderByLoginTimeDescLimit(sysUser.getId());
        if (oldUserLoginInfo != null && LastLoginInfoPromptOn.TURN_ON.getOn().equals(manageSecurity.getLastLoginInfoPromptOn())) {
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "上次登录信息", "上次登录信息", String.format("<h1 style=\"font-size: 18px;\"><p>IP地址：%s</p><p>时间：%s</p><p>地点：%s</p></h1>", oldUserLoginInfo.getLoginIp(), new DateTime(oldUserLoginInfo.getLoginTime()), oldUserLoginInfo.getLoginLocation()), Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        }

        updateSysUser(userLoginInfo, header, ipAddr, sysUser, LoginAccessType.ACCOUNT_PASSWORD.getType());

        return getUserLoginVO(sysUser, userLoginInfo.getId(), manageSecurity);
    }


    @Override
//    @Transactional(rollbackFor = Throwable.class)
    public void logout(String token) {
        String accountName = JwtUtil.getUserAccountName(token);
        if (!StringUtils.isEmpty(accountName)) {
            SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(accountName, UserDeleteStatus.NOT_DELETED.getStatus());
            if (sysUser != null) {
                String prefixManageSecurity = Convert.toStr(redisUtil.get(CommonConstant.PREFIX_MANAGE_SECURITY));
                ManageSecurity manageSecurity = JSONUtil.toBean(prefixManageSecurity, ManageSecurity.class);
                SingleSignOn singleSignOn = SingleSignOn.findByOn(manageSecurity.getSingleSignOn());
                EnumUtil.getEnumMap(SingleSignOn.class).values().forEach(value -> {
                    String prefix = String.format("%s%s", value.getCache(), SingleSignOn.SHUT_DOWN.equals(value) ? token : sysUser.getUserAccountName());
                    Object userToken = redisUtil.get(prefix);
                    if (value.equals(singleSignOn)) {
                        if (token.equals(userToken)) {
                            if (!StringUtils.isEmpty(userToken)) {
                                redisUtil.del(prefix);
                            }
                            //清空用户登录Shiro权限缓存
                            redisUtil.del(String.format("%s%s", CommonConstant.CACHE_KEY_PREFIX, sysUser.getId()));
                            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
                            redisUtil.del(String.format("%s%s", CommonConstant.PREFIX_ONLINE_USER_INFO, sysUser.getUserAccountName()));

                            SysOnlineUser sysOnlineUser = sysOnlineUserMapper.findBySysUserToken(token);
                            sysOnlineUserMapper.deleteBySysUserToken(token);
                            if (sysOnlineUser != null) {
                                UserLoginInfo userLoginInfo = new UserLoginInfo();
                                userLoginInfo.setId(sysOnlineUser.getUserLoginInfoId());
                                userLoginInfo.setOfflineTime(new Date());
                                userLoginInfoMapper.updateById(userLoginInfo);
                            }
                        }
                    } else {
                        if (!StringUtils.isEmpty(userToken)) {
                            redisUtil.del(prefix);
                        }
                    }
                });
                //清空不活跃用户Token缓存
                redisUtil.sRemove(CommonConstant.PREFIX_ACTIVE_USER_TOKEN, token);
            }

        } else {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_00);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String findUserNameById(Integer userId) {
        return userId == null ? "" : userId == -1 ? "系统" : sysUserMapper.findUserNameById(userId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void onlineRecord(String token) {
        final String userAccountName = JwtUtil.getUserAccountName(token);
        // 设置用户名关联token缓存 key
        final ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
        final SingleSignOn singleSignOn = SingleSignOn.findByOn(manageSecurity.getSingleSignOn());
        final String prefixOnlineUserToken = String.format("%s%s", singleSignOn.getCache(), SingleSignOn.SHUT_DOWN.equals(singleSignOn) ? token : userAccountName);
        final String cacheToken = Convert.toStr(redisUtil.get(prefixOnlineUserToken));
        if (redisUtil.hasKey(prefixOnlineUserToken)) {
            SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(userAccountName, UserDeleteStatus.NOT_DELETED.getStatus());
            // 校验token有效性
            log.info("token={},SysUser={}", token, sysUser);
            if (JwtUtil.verify(cacheToken, sysUser.getUserAccountName(), sysUser.getId(), sysUser.getUserPassword())) {
                if (!token.equals(cacheToken)) {
                    logout(token);
                    throw new CommonException(AuthErrorCode.AUTH_ERROR_23);
                }
            } else {
                logout(token);
                throw new CommonException(AuthErrorCode.AUTH_ERROR_05);
            }
        } else {
            for (SingleSignOn value : EnumUtil.getEnumMap(SingleSignOn.class).values()) {
                if (!value.equals(singleSignOn)) {
                    Object userToken = redisUtil.get(String.format("%s%s", value.getCache(), SingleSignOn.SHUT_DOWN.equals(value) ? token : userAccountName));
                    if (token.equals(userToken)) {
                        logout(token);
                        throw new CommonException(AuthErrorCode.AUTH_ERROR_24);
                    }
                }
            }
            logout(token);
            throw new CommonException(AuthErrorCode.AUTH_ERROR_03);
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseVO<UserLoginInfoVO> listOnlineRecord(ListUserLoginInfoDTO listUserLoginInfoDTO) {

        //获取在线用户token列表
        Set<String> tokens = redisUtil.listOnline();

        List<UserLoginInfoVO> userLoginInfoVOList = new ArrayList<>();
        IPage<UserLoginInfo> userLoginInfoPage = PageUtils.startPage(listUserLoginInfoDTO);

        //判断 在线token列表 不能为空
        if (!CollectionUtils.isEmpty(tokens)) {
            // 设置token缓存有效时间
            List<UserLoginInfo> userLoginInfos = userLoginInfoMapper.findLatestByLoginUserIdIn(tokens, listUserLoginInfoDTO.getLoginName(), listUserLoginInfoDTO.getLoginIp(), listUserLoginInfoDTO.getLoginLocation(), Convert.toLong(listUserLoginInfoDTO.getBeginTime()), Convert.toLong(listUserLoginInfoDTO.getEndTime()), userLoginInfoPage);

            for (UserLoginInfo userLoginInfo : userLoginInfos) {
                UserLoginInfoVO userLoginInfoVO = new UserLoginInfoVO();
                BeanUtils.copyProperties(userLoginInfo, userLoginInfoVO);
                userLoginInfoVO.setLoginTime(userLoginInfo.getLoginTime().getTime());

                Date date = userLoginInfo.getOfflineTime() != null ? userLoginInfo.getOfflineTime() : new Date();
                userLoginInfoVO.setOnlineTime(DateUtil.betweenMs(userLoginInfo.getLoginTime(), date));
                userLoginInfoVOList.add(userLoginInfoVO);
            }
        }

        BaseVO<UserLoginInfoVO> baseVO = new BaseVO<>();
        baseVO.setRecords(userLoginInfoVOList);
        baseVO.setTotal(userLoginInfoPage.getTotal());
        return baseVO;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void kickOffTheAssemblyLine(String loginName) {
        if (!StringUtils.isEmpty(loginName)) {
            List<SysOnlineUser> sysOnlineUsers = sysOnlineUserMapper.findByUserAccountName(loginName);
            sysOnlineUsers.forEach(sysOnlineUser -> logout(sysOnlineUser.getSysUserToken()));
            ;
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void unbindPhone(int userId) {
        final SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser != null) {
            sysUserMapper.unbindPhone(userId, new Date());
            accountSafeSettingMapper.updatePhoneSafeTypeBySysUserId(PhoneSafeType.NONE.getType(), userId);
        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void unbindEmail(int userId) {
        final SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser != null) {
            sysUserMapper.unbindEmail(userId, new Date());
            accountSafeSettingMapper.updateEmailSafeTypeBySysUserId(PhoneSafeType.NONE.getType(), userId);
        } else {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
    }

    @Override
    public SysUser findSysUserById(int userId) {
        final SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
        return sysUser;
    }

    @Override
    public void sendLoginPhoneVerificationCode(VerifyPhoneDTO verifyPhoneDTO) {

        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserSafePhoneAndUserDeleteStatus(verifyPhoneDTO.getPhone(), UserDeleteStatus.NOT_DELETED.getStatus());

        if (sysUser == null || StringUtils.isEmpty(sysUser.getUserSafePhone())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_28);
        }
        final String templateId = globalClass.getMappingValue("templateId");
        sendMsgUtil.send(templateId, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_LOGIN);

    }

    @Override
    public UserLoginVO phoneLogin(PhoneLoginDTO PhoneLoginDTO, String header, String ipAddr) {

        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserSafePhoneAndUserDeleteStatus(PhoneLoginDTO.getPhone(), UserDeleteStatus.NOT_DELETED.getStatus());

        UserLoginInfo userLoginInfo = getUserLoginInfo(PhoneLoginDTO.getPhone(), PhoneLoginDTO.getLoginLocation(), header, ipAddr, sysUser);

        try {
            accountService.checkFrequency(sysUser.getUserSafePhone(), PhoneLoginDTO.getVerificationCode(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_LOGIN);
        } catch (CommonException commonException) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), commonException.getErrorCode().getMsg());
            throw commonException;
        }


        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        checkLogin(userLoginInfo, header, ipAddr, sysUser, manageSecurity);

        UserLoginInfo oldUserLoginInfo = userLoginInfoMapper.findByLoginUserIdOrderByLoginTimeDescLimit(sysUser.getId());
        if (oldUserLoginInfo != null && LastLoginInfoPromptOn.TURN_ON.getOn().equals(manageSecurity.getLastLoginInfoPromptOn())) {
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "上次登录信息", "上次登录信息", String.format("<h1 style=\"font-size: 18px;\"><p>IP地址：%s</p><p>时间：%s</p><p>地点：%s</p></h1>", oldUserLoginInfo.getLoginIp(), new DateTime(oldUserLoginInfo.getLoginTime()), oldUserLoginInfo.getLoginLocation()), Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        }

        updateSysUser(userLoginInfo, header, ipAddr, sysUser, LoginAccessType.VERIFICATION_CODE.getType());

        return getUserLoginVO(sysUser, userLoginInfo.getId(), manageSecurity);


    }

    @Override
    public BaseVO<AccountSafeSettingVO> getSecondVerification(UserAccountNameDTO loginDTO) {
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(loginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        if (sysUser == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }
        AccountSafeSetting accountSafeSetting = accountSafeSettingMapper.findBySysUserId(sysUser.getId());
        AccountSafeSettingVO accountSafeSettingVO = new AccountSafeSettingVO();
        if (accountSafeSetting != null) {
            if (!EmailSafeType.NONE.getType().equals(accountSafeSetting.getEmailSafeType())) {
                accountSafeSettingVO.setEmailSafeType(accountSafeSetting.getEmailSafeType());
                accountSafeSettingVO.setUserEmail(sysUser.getUserSafeEmail().replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "****$4"));
            }
            if (!PhoneSafeType.NONE.getType().equals(accountSafeSetting.getPhoneSafeType())) {
                accountSafeSettingVO.setPhoneSafeType(accountSafeSetting.getPhoneSafeType());
                accountSafeSettingVO.setUserPhone(sysUser.getUserSafePhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
        }

        return BuildUtil.buildListVO(accountSafeSettingVO);
    }

    @Override
    public void sendSecondPhoneVerificationCode(UserAccountNameDTO loginDTO) {
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(loginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        if (sysUser == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }

        if (StringUtils.isEmpty(sysUser.getUserSafePhone())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_28);
        }
        final String templateId = globalClass.getMappingValue("templateId");
        sendMsgUtil.send(templateId, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_SECONDARY_PHONE);
    }

    @Override
    public void sendSecondaryEmailVerificationCode(UserAccountNameDTO loginDTO) {
        String subject = "登录二次验证-邮箱验证码";
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(loginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        if (sysUser == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }

        if (StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_26);
        }

        mailUtil.send(sysUser.getUserSafeEmail(), subject, CacheConstant.VERIFICATION_CODE_CACHE_SECONDARY_EMAIL);

    }

    @Override
    public UserLoginVO mobilePhoneIntegrityVerification(IntegrityVerificationLoginDTO integrityVerificationLoginDTO, String header, String ipAddr) {
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(integrityVerificationLoginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());
        UserLoginInfo userLoginInfo = getUserLoginInfo(integrityVerificationLoginDTO.getUserAccountName(), integrityVerificationLoginDTO.getLoginLocation(), header, ipAddr, sysUser);


        if (sysUser == null) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_06.getMsg());
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }


        if (!sysUser.getUserSafePhone().equals(integrityVerificationLoginDTO.getPhone())) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), ParamErrorCode.PARAM_ERROR_56.getMsg());
            throw new CommonException(ParamErrorCode.PARAM_ERROR_56);
        }

        updateSysUser(userLoginInfo, header, ipAddr, sysUser, LoginAccessType.ACCOUNT_PASSWORD.getType());
        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
        return getUserLoginVO(sysUser, userLoginInfo.getId(), manageSecurity);

    }

    @Override
    public UserLoginVO mobileEmailIntegrityVerification(IntegrityVerificationLoginDTO integrityVerificationLoginDTO, String header, String ipAddr) {
        //按账号查询

        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(integrityVerificationLoginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        UserLoginInfo userLoginInfo = getUserLoginInfo(integrityVerificationLoginDTO.getUserAccountName(), integrityVerificationLoginDTO.getLoginLocation(), header, ipAddr, sysUser);

        if (!sysUser.getUserSafeEmail().equals(integrityVerificationLoginDTO.getMailbox())) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), ParamErrorCode.PARAM_ERROR_57.getMsg());
            throw new CommonException(ParamErrorCode.PARAM_ERROR_57);
        }

        updateSysUser(userLoginInfo, header, ipAddr, sysUser, LoginAccessType.ACCOUNT_PASSWORD.getType());
        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
        return getUserLoginVO(sysUser, userLoginInfo.getId(), manageSecurity);

    }

    @Override
    public UserLoginVO secondPhoneLogin(SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, String header, String ipAddr) {

        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(secondaryVerificationLoginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        return getUserLoginVO(sysUser, secondaryVerificationLoginDTO, header, ipAddr, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_SECONDARY_PHONE);

    }

    @Override
    public UserLoginVO secondEmailLogin(SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, String header, String ipAddr) {
        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(secondaryVerificationLoginDTO.getUserAccountName(), UserDeleteStatus.NOT_DELETED.getStatus());

        return getUserLoginVO(sysUser, secondaryVerificationLoginDTO, header, ipAddr, sysUser.getUserSafeEmail(), CacheConstant.VERIFICATION_CODE_CACHE_SECONDARY_EMAIL);
    }

    @Override
    public void updateFirstPassword(FirstPasswordDTO firstPasswordDTO) {
        SysUser sysUser = sysUserMapper.findByUserAccountName(firstPasswordDTO.getUserAccountName());

        //生成旧密码
        String oldPassword = PasswordUtil.encrypt(sysUser.getUserAccountName(), firstPasswordDTO.getOldUserPassword(), sysUser.getUserPasswdSalt());

        if (!sysUser.getUserPassword().equals(oldPassword)) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_18);
        }

        accountService.updatePassword(sysUser, firstPasswordDTO.getNewUserPassword());
    }

    @Override
    public UserLoginVO getUserInfo(String token) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        UserLoginVO userLoginVO = new UserLoginVO();
        final AccountSafeSetting accountSafeSetting = accountSafeSettingMapper.findBySysUserId(loginUser.getId());
        final long day = DateUtil.betweenDay(accountSafeSetting.getAccountPrePasswdTime(), new Date(), Boolean.FALSE);
        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        String userPhotoUrl = fileUploadUtil.getObjUrl(BucketNamePool.HEAD_URL_BUCKET, "DEFAULT.jpg", 666);
        if (!StringUtils.isEmpty(loginUser.getPhotoObjectName())) {
            userPhotoUrl = fileUploadUtil.getObjUrl(loginUser.getPhotoBucketName(), loginUser.getPhotoObjectName(), 666);
        }

        PlatformSettingVO platformSetting = sysServiceSettingService.getPlatformSetting();
        userLoginVO.setBannerOption(platformSetting.getBannerOption());
        userLoginVO.setBannerShowOption(BannerOption.TURN_ON.getOption());
        userLoginVO.setLogoUrl(platformSetting.getLogoUrl());
        userLoginVO.setBannerUrl(platformSetting.getBannerUrl());
        userLoginVO.setBannerCloseOption(platformSetting.getBannerCloseOption());
        userLoginVO.setUserPhotoUrl(userPhotoUrl);
        userLoginVO.setUserSysrole(loginUser.getUserSysrole());
        userLoginVO.setUserAccountName(loginUser.getUserAccountName());
        userLoginVO.setUserName(loginUser.getUserName());
        userLoginVO.setLogoUrl(platformSetting.getLogoUrl());
        userLoginVO.setPasswordExpired(day >= manageSecurity.getAccountPassOutMins());
        userLoginVO.setPasswordStrength(manageSecurity.getPasswordStrength());
        userLoginVO.setUserStatus(loginUser.getUserStatus());
        try {
            String encrypt = AESUtil.Encrypt(token, CommonConstant.AES_DECRYPTION_KEY);
            userLoginVO.setUserCode(URLEncoder.encode(encrypt, StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (BannerOption.TURN_ON.getOption().equals((platformSetting.getBannerOption()))) {
            if (BannerFrequency.FIRST_LOGIN.getFrequency().equals(platformSetting.getBannerFrequency())) {
                List<UserLoginInfo> userLoginInfos = userLoginInfoMapper.findByLoginUserIdAndLoginTimeBetweenEqual(loginUser.getId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
                if (!CollectionUtils.isEmpty(userLoginInfos)) {
                    userLoginVO.setBannerShowOption(BannerOption.TURN_ON.getOption());
                }
            }
        }


        return userLoginVO;
    }

    @Override
    public AccessTokenVO getAccessToken(GetAccessTokenDTO getAccessTokenDTO) {
        OpenApp openApp = openAppMapper.findByAgentIdAndAppKeyAndAppSecret(getAccessTokenDTO.getAgentId(), getAccessTokenDTO.getAppKey(), getAccessTokenDTO.getAppSecret());
        if (openApp == null) {
            throw new CommonException(AppErrorCode.NO_DOCKING_WITH_THIS_APP);
        }

        AccessTokenVO accessTokenVO = new AccessTokenVO();
        String urlEncodeSignature = getSignature(getAccessTokenDTO.getAgentId(), getAccessTokenDTO.getAppKey(), getAccessTokenDTO.getAppSecret(), getAccessTokenDTO.getTimestamp());
        if (getAccessTokenDTO.getSignature().equals(urlEncodeSignature)) {

            String accessToken = null;
            String key = MD5Tools.string2MD5(String.format("%s%s%s", getAccessTokenDTO.getAgentId(), getAccessTokenDTO.getAppKey(), getAccessTokenDTO.getAppSecret()));
            key = String.format("%s%s", CacheConstant.APP_ACCESS_TOKEN, key);
            Object objToken = redisUtil.get(key);
            if (objToken != null) {
                accessToken = objToken.toString();
            }
            if (StringUtils.isEmpty(accessToken)) {
                accessToken = IdUtil.fastUUID();
                redisUtil.set(key, accessToken, 1, TimeUnit.DAYS);
                redisUtil.set(String.format("%s%s", CacheConstant.APP_ACCESS_INFO, accessToken), openApp, 1, TimeUnit.DAYS);
            }

            accessTokenVO.setAccessToken(accessToken);
            return accessTokenVO;
        } else {
            throw new CommonException(AppErrorCode.SIGNATURE_ERROR);
        }
    }


    @Override
    public AccessInfoVO getAccessInfo(GetAccessInfoDTO getAccessInfoDTO) {

        AccessInfoVO accessInfoVO = new AccessInfoVO();

        String uuid = String.format("%s%s", CacheConstant.APP_ACCESS_INFO, getAccessInfoDTO.getAccessToken());

        if (redisUtil.hasKey(uuid)) {
            String urlEncodeSignature = getSignature(getAccessInfoDTO.getAccessToken(), getAccessInfoDTO.getUserCode(), getAccessInfoDTO.getTimestamp());
            if (getAccessInfoDTO.getSignature().equals(urlEncodeSignature)) {
                String token = "";
                try {
                    token = AESUtil.Decrypt(getAccessInfoDTO.getUserCode(), CommonConstant.AES_DECRYPTION_KEY);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CommonException(AppErrorCode.WRONG_OR_INVALID_CODE);
                }
                onlineRecord(token);
                String userAccountName = JwtUtil.getUserAccountName(token);
                SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(userAccountName, UserDeleteStatus.NOT_DELETED.getStatus());
                if (sysUser != null) {
                    List<SysUserDepartment> userDepartments = sysUserDepartmentMapper.findAllBySysUserId(sysUser.getId());
                    Map<Integer, Integer> departmentMap = userDepartments.stream().collect(Collectors.toMap(SysUserDepartment::getSysDepartmentId, SysUserDepartment::getDeptManage));
                    List<SysDepartment> sysDepartments = sysDepartmentMapper.findDepartmentByUserId(sysUser.getId());
                    List<UserDepartmentInfoVO> userDepartmentInfos = new ArrayList<>();
                    for (SysDepartment sysDepartment : sysDepartments) {
                        UserDepartmentInfoVO userDepartmentInfoVO = new UserDepartmentInfoVO();
                        userDepartmentInfoVO.setDeptCode(sysDepartment.getDeptCode());
                        userDepartmentInfoVO.setDeptName(sysDepartment.getDeptName());
                        userDepartmentInfoVO.setDeptNameEn(sysDepartment.getDeptNameEn());
                        userDepartmentInfoVO.setDeptManagerPhone(sysDepartment.getDeptManagerPhone());
                        userDepartmentInfoVO.setDeptManager(sysDepartment.getDeptManager());
                        userDepartmentInfoVO.setDeptManage(departmentMap.get(sysUser.getId()) == null ? 0 : departmentMap.get(sysUser.getId()));
                        userDepartmentInfoVO.setDeptValidTime(sysDepartment.getDeptValidTime() != null ? sysDepartment.getDeptValidTime().getTime() : null);
                        userDepartmentInfos.add(userDepartmentInfoVO);
                    }
                    accessInfoVO.setUserAccountName(sysUser.getUserAccountName());
                    accessInfoVO.setUserName(sysUser.getUserName());
                    accessInfoVO.setUserSex(sysUser.getUserSex());
                    accessInfoVO.setUserEmail(sysUser.getUserSafeEmail());
                    accessInfoVO.setUserPhone(sysUser.getUserSafePhone());
                    accessInfoVO.setUserDepartmentInfos(userDepartmentInfos);
                }
                return accessInfoVO;

            } else {
                throw new CommonException(AppErrorCode.SIGNATURE_ERROR);
            }
        } else {
            throw new CommonException(AppErrorCode.ACCESS_TOKEN_ERROR);
        }
    }

    private String getSignature(String agentId, String appKey, String appSecret, String timestamp) {
        try {
            String appMark = String.format("%s%s%s", agentId, appKey, appSecret);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appMark.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(timestamp.getBytes(StandardCharsets.UTF_8));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            if (StringUtils.isEmpty(signature)) {
                return "";
            }
            String encoded = URLEncoder.encode(signature, StandardCharsets.UTF_8.name());
            return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getSignature(String accessToken, String userCode, String timestamp) {
        try {
            String appMark = String.format("%s%s", accessToken, userCode);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appMark.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(timestamp.getBytes(StandardCharsets.UTF_8));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            if (StringUtils.isEmpty(signature)) {
                return "";
            }
            String encoded = URLEncoder.encode(signature, StandardCharsets.UTF_8.name());
            return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取用户登录信息
     *
     * @param name          的名字
     * @param loginLocation 登录的位置
     * @param header        头
     * @param ipAddr        ip addr
     * @param sysUser       系统用户
     * @return {@link UserLoginInfo }
     * @author WeiQiangMiao
     * @date 2021-04-14
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private UserLoginInfo getUserLoginInfo(String name, String loginLocation, String header, String ipAddr, SysUser sysUser) {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        if (sysUser != null) {
            userLoginInfo.setLoginUserId(sysUser.getId());
            userLoginInfo.setLoginName(sysUser.getUserAccountName());
        } else {
            userLoginInfo.setLoginName(name);
        }
        userLoginInfo.setLoginLocation(StringUtils.isEmpty(loginLocation) ? "" : loginLocation);


        //判断账户存不存在
        if (sysUser == null) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_06.getMsg());
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }
        return userLoginInfo;
    }

    /**
     * 获取用户登录签证官
     *
     * @param sysUser                       系统用户
     * @param secondaryVerificationLoginDTO 二次验证登录dto
     * @param header                        头
     * @param ipAddr                        ip addr
     * @param way                           的方式
     * @param cache                         缓存
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-13
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private UserLoginVO getUserLoginVO(SysUser sysUser, SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, String header, String ipAddr, String way, String cache) {
        UserLoginInfo userLoginInfo = getUserLoginInfo(secondaryVerificationLoginDTO.getUserAccountName(), secondaryVerificationLoginDTO.getLoginLocation(), header, ipAddr, sysUser);

        try {
            accountService.checkFrequency(way, secondaryVerificationLoginDTO.getVerificationCode(), cache);
        } catch (CommonException commonException) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), commonException.getErrorCode().getMsg());
            throw commonException;
        }


        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        checkLogin(userLoginInfo, header, ipAddr, sysUser, manageSecurity);

        updateSysUser(userLoginInfo, header, ipAddr, sysUser, LoginAccessType.ACCOUNT_PASSWORD.getType());

        return getUserLoginVO(sysUser, userLoginInfo.getId(), manageSecurity);
    }


    /**
     * 保存用户登录信息
     *
     * @param header           头
     * @param ipAddr           ip addr
     * @param userLoginInfo    用户登录信息表
     * @param loginStatus      登录状态
     * @param loginAccessType  登录访问类型
     * @param loginDescription 登录描述
     * @author WeiQiangMiao
     * @date 2021-03-09
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void saveUserLoginInfo(String header, String ipAddr, UserLoginInfo userLoginInfo, Integer loginStatus, Integer loginAccessType, String loginDescription) {
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
        //系统名称
        String system = os.getName();
        //浏览器名称
        String browserName = browser.getName();


        userLoginInfo.setId(BaseUtil.getSnowflakeId());
        userLoginInfo.setLoginIp(ipAddr);
        userLoginInfo.setLoginBrowser(browserName);
        userLoginInfo.setLoginOs(system);
        userLoginInfo.setLoginStatus(loginStatus);
        userLoginInfo.setLoginTime(new Date());
        userLoginInfo.setLoginAccessType(loginAccessType);
        userLoginInfo.setLoginDescription(StringUtils.isEmpty(loginDescription) ? "登录成功" : loginDescription);

        userLoginInfoMapper.insert(userLoginInfo);

    }

    /**
     * 获取用户登录信息
     *
     * @param sysUser 系统用户
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-03-09
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private UserLoginVO getUserLoginVO(SysUser sysUser, Long userLoginInfoId, ManageSecurity manageSecurity) {
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(sysUser, userLoginVO);

        String userPhotoUrl = fileUploadUtil.getObjUrl(BucketNamePool.HEAD_URL_BUCKET, "DEFAULT.jpg", 666);
        if (!StringUtils.isEmpty(sysUser.getPhotoObjectName())) {
            userPhotoUrl = fileUploadUtil.getObjUrl(sysUser.getPhotoBucketName(), sysUser.getPhotoObjectName(), 666);
        }
        userLoginVO.setUserPhotoUrl(userPhotoUrl);

        //生成token
        String token = JwtUtil.sign(sysUser.getUserAccountName(), sysUser.getId(), sysUser.getUserPassword());
        userLoginVO.setToken(token);
        // 设置token缓存有效时间 key
        SingleSignOn singleSignOn = SingleSignOn.findByOn(manageSecurity.getSingleSignOn());
        String prefixOnlineUserToken = String.format("%s%s", singleSignOn.getCache(), SingleSignOn.SHUT_DOWN.equals(singleSignOn) ? token : sysUser.getUserAccountName());


        sysOnlineUserMapper.insert(new SysOnlineUser(BaseUtil.getSnowflakeId(), userLoginInfoId, token, sysUser.getId()));
        //刷新 在线token
        redisUtil.online(token);
        userLoginVO.setUserStatus(UserStatus.NORMAL.getStatus());
        redisUtil.del(String.format("%s%s", CommonConstant.PREFIX_ONLINE_USER_INFO, sysUser.getUserAccountName()));
        //清空用户登录Shiro权限缓存
        redisUtil.del(String.format("%s%s", CommonConstant.CACHE_KEY_PREFIX, sysUser.getId()));

        redisUtil.set(prefixOnlineUserToken, token, manageSecurity.getSingleLoginValidTime(), TimeUnit.MINUTES);

        //给websocket使用
        redisUtil.set(String.format("%s:%s:%s", CommonConstant.LOGINUSERIDS, sysUser.getId(), token), token, JwtUtil.EXPIRE_TIME);


        PlatformSettingVO platformSetting = sysServiceSettingService.getPlatformSetting();
        userLoginVO.setBannerOption(platformSetting.getBannerOption());
        userLoginVO.setBannerShowOption(BannerOption.TURN_ON.getOption());
        userLoginVO.setLogoUrl(platformSetting.getLogoUrl());
        userLoginVO.setBannerUrl(platformSetting.getBannerUrl());
        userLoginVO.setBannerCloseOption(platformSetting.getBannerCloseOption());
        if (BannerOption.TURN_ON.getOption().equals((platformSetting.getBannerOption()))) {
            if (BannerFrequency.FIRST_LOGIN.getFrequency().equals(platformSetting.getBannerFrequency())) {
                List<UserLoginInfo> userLoginInfos = userLoginInfoMapper.findByLoginUserIdAndLoginTimeBetweenEqual(sysUser.getId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
                if (!CollectionUtils.isEmpty(userLoginInfos)) {
                    userLoginVO.setBannerShowOption(BannerOption.TURN_ON.getOption());
                }
            }
        }


        AccountSafeSetting accountSafeSetting = accountSafeSettingMapper.findBySysUserId(sysUser.getId());
        if (accountSafeSetting != null) {
            long day = DateUtil.betweenDay(accountSafeSetting.getAccountPrePasswdTime(), new Date(), Boolean.FALSE);
            if (manageSecurity.getAccountPassOutMins() != null && day >= manageSecurity.getAccountPassOutMins()) {
                userLoginVO.setPasswordExpired(day >= manageSecurity.getAccountPassOutMins());
            }
        }

        userLoginVO.setPasswordStrength(manageSecurity.getPasswordStrength());
        try {
            String encrypt = AESUtil.Encrypt(token, CommonConstant.AES_DECRYPTION_KEY);
            userLoginVO.setUserCode(URLEncoder.encode(encrypt, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLoginVO;
    }

    /**
     * 检查登录
     *
     * @param userLoginInfo 用户登录信息
     * @param header        头
     * @param ipAddr        ip addr
     * @param sysUser       系统用户
     * @author WeiQiangMiao
     * @date 2021-03-26
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void checkLogin(UserLoginInfo userLoginInfo, String header, String ipAddr, SysUser sysUser, ManageSecurity manageSecurity) {

        //系统用户状态
        if (!UserStatus.NORMAL.getStatus().equals(sysUser.getUserStatus()) && !UserStatus.INACTIVATED.getStatus().equals(sysUser.getUserStatus())) {
            ErrorCode errorCode = new ErrorCode(100509, String.format("此账号%s", UserStatus.getDescByStatus(sysUser.getUserStatus())));
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), errorCode.getMsg());
            throw new CommonException(errorCode);
        }

        //判断过期时间
        if (System.currentTimeMillis() > sysUser.getUserValidTime().getTime()) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_12.getMsg());
            throw new CommonException(AuthErrorCode.AUTH_ERROR_12);
        }


        if (UserSysRole.ADMINISTRATOR.getType().equals(sysUser.getUserSysrole())) {
            //获取管理员ip过滤系统配置
            List<String> adminIps = StrUtil.split(manageSecurity.getAdminLoginIpFiltering(), ",", -1, true, true);
            checkLoginIp(manageSecurity.getAdminLoginIpFilteringOn(), ipAddr, adminIps);
        } else {
            //获取成员ip过滤系统配置
            List<String> memberIps = StrUtil.split(manageSecurity.getMemberLoginIpFiltering(), ",", -1, true, true);
            checkLoginIp(manageSecurity.getMemberLoginIpFilteringOn(), ipAddr, memberIps);
        }


        //获取单点登录配置
        if (SingleSignOn.PROHIBIT.getOn().equals(manageSecurity.getSingleSignOn()) && redisUtil.hasKey(String.format("%s%s", CommonConstant.PREFIX_ONLINE_USER_PROHIBIT_TOKEN, sysUser.getUserAccountName()))) {
            saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_22.getMsg());
            throw new CommonException(AuthErrorCode.AUTH_ERROR_22);
        }
    }


    /**
     * 更新系统用户状态
     *
     * @param userLoginInfo 用户登录信息表
     * @param header        头
     * @param ipAddr        ip addr
     * @param sysUser       系统用户
     * @author WeiQiangMiao
     * @date 2021-03-26
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void updateSysUser(UserLoginInfo userLoginInfo, String header, String ipAddr, SysUser sysUser, Integer loginAccessType) {
        SysUser updateSysUser = new SysUser();
        updateSysUser.setId(sysUser.getId());
        updateSysUser.setUserStatus(UserStatus.NORMAL.getStatus());
        //修改用户在线状态
        sysUserMapper.updateById(updateSysUser);
        //保存用户登录日志
        saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.SUCCESS.getStatus(), loginAccessType, "登录成功");
    }


    /**
     * 检查登录ip地址
     *
     * @param loginIpFilteringOn 登录ip过滤开关
     * @param ipAddr             ip
     * @param ips                过滤ip集合
     * @author WeiQiangMiao
     * @date 2021-03-26
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void checkLoginIp(Integer loginIpFilteringOn, String ipAddr, Collection<String> ips) {

        if (LoginIpFilteringOn.PROHIBIT.getOn().equals(loginIpFilteringOn)) {
            boolean ipBoolean = Boolean.FALSE;
            if (ips.contains(ipAddr)) {
                ipBoolean = Boolean.TRUE;
            }

            for (String ip : ips) {
                try {
                    SubnetUtils utils = new SubnetUtils(ip);
                    if (utils.getInfo().isInRange(ipAddr)) {
                        ipBoolean = Boolean.TRUE;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ipBoolean) {
                throw new CommonException(AuthErrorCode.AUTH_ERROR_20);
            }

        } else {
            if (!ips.contains(ipAddr)) {
                boolean ipBoolean = Boolean.TRUE;
                for (String ip : ips) {
                    try {
                        SubnetUtils utils = new SubnetUtils(ip);
                        if (utils.getInfo().isInRange(ipAddr)) {
                            ipBoolean = Boolean.FALSE;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (CollectionUtils.isEmpty(ips) || ipBoolean) {
                    throw new CommonException(AuthErrorCode.AUTH_ERROR_20);
                }
            }
        }
    }

    private void checkLoginPassWordAndLocking(PassWordLoginDTO passWordLoginDTO, UserLoginInfo userLoginInfo, String header, String ipAddr, SysUser sysUser, ManageSecurity manageSecurity) {


        // 设置登录用户锁定令牌缓存KEY前缀
        final String prefixLockingUser = String.format("%s%s", CommonConstant.PREFIX_LOCKING_USER, passWordLoginDTO.getUserAccountName());
        // 设置用户锁定次数令牌缓存KEY前缀 */
        final String prefixLockingUserFrequency = String.format("%s%s", CommonConstant.PREFIX_LOCKING_USER_FREQUENCY, passWordLoginDTO.getUserAccountName());

        //获取密码
        String passWord = PasswordUtil.encrypt(passWordLoginDTO.getUserAccountName(), passWordLoginDTO.getUserPassword(), sysUser.getUserPasswdSalt());


        if (LoginLockoutStrategyOn.TURN_ON.getOn().equals(manageSecurity.getAccountLockoutStrategyOn())) {
            Long expire = stringRedisTemplate.opsForValue().getOperations().getExpire(prefixLockingUser);
            if (expire != null && expire > 0) {
                saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_21.getMsg());
                throw new CommonException(new ErrorCode(100500, String.format("账号锁定%s分钟,剩余%s", manageSecurity.getAccountLockoutStrategyMins(), DateUtil.secondToTime(Convert.toInt(expire)))));
            }

            String key = stringRedisTemplate.opsForValue().get(prefixLockingUserFrequency);
            int locking = key == null ? 0 : Convert.toInt(key);
            //判断密码
            if (!passWord.equals(sysUser.getUserPassword())) {

                if (locking < manageSecurity.getAccountLockoutStrategyFrequency()) {
                    stringRedisTemplate.opsForValue().set(prefixLockingUserFrequency, Convert.toStr(++locking), 60, TimeUnit.MINUTES);
                }

                if (locking >= manageSecurity.getAccountLockoutStrategyFrequency()) {
                    if (manageSecurity.getAccountLockoutStrategyFrequency().equals(locking)) {
                        int frequency = ++locking;
                        stringRedisTemplate.opsForValue().set(prefixLockingUser, Convert.toStr(frequency), manageSecurity.getAccountLockoutStrategyMins(), TimeUnit.MINUTES);
                        stringRedisTemplate.opsForValue().set(prefixLockingUserFrequency, Convert.toStr(frequency), manageSecurity.getAccountLockoutStrategyMins(), TimeUnit.MINUTES);
                    }
                    Long frequencyExpire = stringRedisTemplate.opsForValue().getOperations().getExpire(prefixLockingUserFrequency);
                    saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_11.getMsg());
                    throw new CommonException(new ErrorCode(100500, String.format("账号锁定%s分钟,剩余%s", manageSecurity.getAccountLockoutStrategyMins(), DateUtil.secondToTime(Convert.toInt(frequencyExpire)))));
                } else {
                    saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_11.getMsg());
                    throw new CommonException(new ErrorCode(100500, String.format("该用户不存在或密码错误,%s次后锁定", manageSecurity.getAccountLockoutStrategyFrequency() - locking)));
                }

            }
            stringRedisTemplate.delete(prefixLockingUserFrequency);

        } else {
            //判断密码
            if (!passWord.equals(sysUser.getUserPassword())) {
                saveUserLoginInfo(header, ipAddr, userLoginInfo, LoginStatus.FAILURE.getStatus(), LoginAccessType.ACCOUNT_PASSWORD.getType(), AuthErrorCode.AUTH_ERROR_11.getMsg());
                throw new CommonException(AuthErrorCode.AUTH_ERROR_11);
            }
        }


    }

    @Override
    public BaseVO<SysUserItemVO> getUserListByQuery(BaseDTO baseDTO) {
//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        GetListSysUserDTO getListSysUserDTO = new GetListSysUserDTO();
        BeanUtils.copyProperties(baseDTO, getListSysUserDTO);
        getListSysUserDTO.setUserDeleteStatus(UserDeleteStatus.NOT_DELETED.getStatus());
        IPage<SysUserItemVO> listSysUser = getListSysUser(getListSysUserDTO);
//        IPage<SysUser> sysUserIPage = sysUserMapper.selectPage(PageUtils.startPage(baseDTO), queryWrapper);

//        List<UserBasicInfoVO> list = new ArrayList<>();
//
//        for (SysUser record : sysUserIPage.getRecords()) {
//            UserBasicInfoVO userBasicInfoVO = new UserBasicInfoVO();
//            BeanUtils.copyProperties(record,userBasicInfoVO);
//            list.add(userBasicInfoVO);
//        }
        return BuildUtil.buildListVO(listSysUser.getTotal(), listSysUser.getRecords());
    }

    @Override
    public GetBindInfoVO getBindInfo(UserAccountNameDTO userAccountNameDTO) {

        //按账号查询
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserStatusAndUserDeleteStatus(userAccountNameDTO.getUserAccountName(), UserStatus.NORMAL.getStatus(), UserDeleteStatus.NOT_DELETED.getStatus());

        if (sysUser == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }

        if (StringUtils.isEmpty(sysUser.getUserSafePhone()) && StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_45);
        }

        GetBindInfoVO getBindInfoVO = new GetBindInfoVO();
        if (!StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            getBindInfoVO.setEmail(sysUser.getUserSafeEmail().replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "****$4"));
        }
        if (!StringUtils.isEmpty(sysUser.getUserSafePhone())) {
            getBindInfoVO.setPhone(sysUser.getUserSafePhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }

        List<SysServiceSetting> sysServiceSettings = sysServiceSettingMapper.findBySysServiceType(SysServiceType.SECURITY_MANAGE.getType());
        Map<String, String> securityManageMap = sysServiceSettings.stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, SysServiceSetting::getSysServiceValue));
        ManageSecurity manageSecurity = BeanUtil.mapToBean(securityManageMap, ManageSecurity.class, Boolean.TRUE);
        getBindInfoVO.setPasswordStrength(manageSecurity.getPasswordStrength());
        return getBindInfoVO;

    }

    @Override
    public void sendRetrievePwPhoneCaptcha(CheckRetrievePwCaptchaDTO captchaDTO) {
        SysUser sysUser = checkUser(captchaDTO.getUserAccountName());
        String retrievePassWordToken = String.format("%s:%s", CacheConstant.RETRIEVE_PASS_WORD_STRING, captchaDTO.getUserAccountName());
        if (!redisUtil.hasKey(retrievePassWordToken)) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_48);
        }
        if (!captchaDTO.getCaptcha().toLowerCase().equals(redisUtil.get(retrievePassWordToken))) {
            redisUtil.del(retrievePassWordToken);
            throw new CommonException(CommonErrorCode.COMMON_ERROR_46);
        }
        final String templateId = globalClass.getMappingValue("templateId");
        sendMsgUtil.send(templateId, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_RETRIEVE);
        redisUtil.del(retrievePassWordToken);
    }


    @Override
    public void sendRetrievePwEmailCaptcha(CheckRetrievePwCaptchaDTO captchaDTO) {
        String subject = "邮箱找回密码验证码";
        SysUser sysUser = checkUser(captchaDTO.getUserAccountName());
        String retrievePassWordToken = String.format("%s:%s", CacheConstant.RETRIEVE_PASS_WORD_STRING, captchaDTO.getUserAccountName());
        if (!redisUtil.hasKey(retrievePassWordToken)) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_48);
        }
        if (!captchaDTO.getCaptcha().toLowerCase().equals(redisUtil.get(retrievePassWordToken))) {
            redisUtil.del(retrievePassWordToken);
            throw new CommonException(CommonErrorCode.COMMON_ERROR_46);
        }
        mailUtil.send(sysUser.getUserSafeEmail(), subject, CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_RETRIEVE);
        redisUtil.del(retrievePassWordToken);
    }

    private SysUser checkUser(String userAccountName) {

        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserStatusAndUserDeleteStatus(userAccountName, UserStatus.NORMAL.getStatus(), UserDeleteStatus.NOT_DELETED.getStatus());
        if (sysUser == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_00);
        }
        if (StringUtils.isEmpty(sysUser.getUserSafePhone()) && StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_45);
        }
        return sysUser;
    }

    @Override
    public void updatePhonePasswordByCode(RetrievePwDTO retrievePwDTO) {
        //按账号查询
        SysUser sysUser = checkUser(retrievePwDTO.getUserAccountName());

        updatePasswordByCode(retrievePwDTO.getCaptcha(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_RETRIEVE, sysUser.getUserSafePhone(), retrievePwDTO.getNewPassword(), sysUser);

    }

    @Override
    public void updateEmailPasswordByCode(RetrievePwDTO retrievePwDTO) {
        //按账号查询
        SysUser sysUser = checkUser(retrievePwDTO.getUserAccountName());

        updatePasswordByCode(retrievePwDTO.getCaptcha(), CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_RETRIEVE, sysUser.getUserSafeEmail(), retrievePwDTO.getNewPassword(), sysUser);

    }

    private void updatePasswordByCode(Integer captcha, String cache, String way, String newPassword, SysUser sysUser) {
        String key = String.format("%s:%s", cache, way);
        final String prefixClearFrequency = String.format(CacheConstant.VERIFICATION_CODE_INVALIDATION_FREQUENCY, cache, way);
        if (!redisUtil.hasKey(key)) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_55);
        }

        Integer code = Convert.toInt(redisUtil.get(key));
        if (!captcha.equals(code)) {
            Integer frequency = Convert.toInt(redisUtil.get(prefixClearFrequency));
            redisUtil.set(prefixClearFrequency, Convert.toStr(StringUtils.isEmpty(frequency) ? 1 : ++frequency), 3, TimeUnit.MINUTES);
            if (!StringUtils.isEmpty(frequency) && frequency >= CommonConstant.VERIFICATION_CODE_INVALIDATION_FREQUENCY) {
                redisUtil.del(prefixClearFrequency);
                redisUtil.del(key);
            }
            throw new CommonException(ParamErrorCode.PARAM_ERROR_44);
        }
        accountService.updatePassword(sysUser, newPassword);
        redisUtil.del(key);
        redisUtil.del(prefixClearFrequency);
    }


    @Override
    public String getVerify(UserAccountNameDTO userAccountNameDTO) {
        String retrievePassWordString = String.format("%s:%s", CacheConstant.RETRIEVE_PASS_WORD_STRING, userAccountNameDTO.getUserAccountName());

        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(300, 100, 5, 4);

        //将验证码字符序列化存入redis
        redisUtil.set(retrievePassWordString, captcha.getCode(), 600);
        //输出流
        InputStream input = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "jpeg", stream);
            input = new ByteArrayInputStream(stream.toByteArray());
            return ImageUtil.getBase64FromInputStream(input);
        } catch (IOException e) {
            log.info(e);
            throw new CommonException(CommonErrorCode.COMMON_ERROR_47);
        } finally {
            if (stream != null) {

                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public UserLoginVO dingDingAuth(DingDingAuthDTO dingDingAuthDTO, String header, String ipAddr) {

        final SysDingDingConfigVO sysDingDingConfigVO = sysDingDingConfigService.getSysDingDingConfigVO();

        if (StringUtils.isEmpty(sysDingDingConfigVO.getAppKey()) || StringUtils.isEmpty(sysDingDingConfigVO.getAppSecret())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_51);
        }

        final OapiSnsGetuserinfoBycodeResponse userInfo = authSDK.getUserInfo(dingDingAuthDTO.getCode(), sysDingDingConfigVO.getAppKey(), sysDingDingConfigVO.getAppSecret());

        final String unionid = userInfo.getUserInfo().getUnionid();

        String accessToken = dingTalkService.getAccessToken(sysDingDingConfigVO.getAppKey(), sysDingDingConfigVO.getAppSecret());

        final OapiUserGetbyunionidResponse response = userSDK.getUserIdByUnionId(unionid, accessToken);
        final String userid = response.getResult().getUserid();

        final SysUser sysUser = sysUserMapper.findByUserCode(SysDingDingConfigServiceImpl.DING_DING_PRE_CODE + userid);

        if (sysUser != null) {
            ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
            UserLoginInfo userLoginInfo = getUserLoginInfo(sysUser.getUserAccountName(), dingDingAuthDTO.getLoginLocation(), header, ipAddr, sysUser);
            return doUserLogin(header, ipAddr, sysUser, manageSecurity, userLoginInfo);
        } else {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
        }
    }

    @Override
    public DdScanLoginParamVO getDdScanLoginParam() {
        final SysDingDingConfigVO sysDingDingConfigVO = sysDingDingConfigService.getSysDingDingConfigVO();
        final DdScanLoginParamVO ddScanLoginParamVO = new DdScanLoginParamVO();
        ddScanLoginParamVO.setAppId(sysDingDingConfigVO.getAppKey());
        return ddScanLoginParamVO;
    }

}
