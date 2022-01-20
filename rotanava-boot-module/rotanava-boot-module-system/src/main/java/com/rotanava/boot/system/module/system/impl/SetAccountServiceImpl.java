package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.SetAccountService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.PasswordStrength;
import com.rotanava.boot.system.api.module.constant.SysServiceType;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.boot.system.api.module.system.bo.AccountSafeSetting;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.dto.AddAccountSafeSettingDTO;
import com.rotanava.boot.system.api.module.system.dto.MailboxVerificationCodeDTO;
import com.rotanava.boot.system.api.module.system.dto.PhoneVerificationCodeDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateNewPsdByOldPsdDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdatePsdDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateUserBasicInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.VerifyMailboxDTO;
import com.rotanava.boot.system.api.module.system.dto.VerifyPhoneDTO;
import com.rotanava.boot.system.api.module.system.vo.SecuritySettingsVO;
import com.rotanava.boot.system.api.module.system.vo.UserBasicInfoVO;
import com.rotanava.boot.system.module.dao.AccountSafeSettingMapper;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.constant.CacheConstant;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.*;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.boot.system.util.MailUtil;
import com.rotanava.framework.util.PasswordUtil;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.boot.system.util.sms.SendMsgUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 设置帐户服务impl
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Service
public class SetAccountServiceImpl implements SetAccountService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private SysServiceSettingMapper sysServiceSettingMapper;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private AccountSafeSettingMapper accountSafeSettingMapper;
    @DubboReference
    CommonApi commonApi;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ManageSecurityService manageSecurityService;
    @Autowired
    private SendMsgUtil sendMsgUtil;
    @Autowired
    @Lazy
    private GlobalClass globalClass;

    @Override
    public void updateUserBasicInfo(UpdateUserBasicInfoDTO updateUserBasicInfoDTO, MultipartFile multipartFile) {

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser != null) {
            SysUser sysUser = new SysUser();
            sysUser.setId(loginUser.getId());
            BeanUtils.copyProperties(updateUserBasicInfoDTO, sysUser);
            Optional.ofNullable(updateUserBasicInfoDTO.getUserBirthday()).map(Date::new).ifPresent(sysUser::setUserBirthday);
            if (multipartFile != null) {
                uploadAvatar(multipartFile);
            }
            sysUserMapper.updateById(sysUser);
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s%s", CommonConstant.PREFIX_ONLINE_USER_INFO, sysUser.getUserAccountName()));
            commonApi. getUserByAccountName(loginUser.getUserAccountName());
        }

    }

    @Override
    public BaseVO<UserBasicInfoVO> getUserBasicInfo() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        String userPhotoUrl = fileUploadUtil.getObjUrl(BucketNamePool.HEAD_URL_BUCKET, "DEFAULT.jpg", 666);
        if (!StringUtils.isEmpty(sysUser.getPhotoObjectName())) {
            userPhotoUrl = fileUploadUtil.getObjUrl(BucketNamePool.HEAD_URL_BUCKET, sysUser.getPhotoObjectName(), 666);
        }
        UserBasicInfoVO userBasicInfoVO = new UserBasicInfoVO();
        BeanUtils.copyProperties(sysUser, userBasicInfoVO);
        userBasicInfoVO.setUserPhotoUrl(userPhotoUrl);
        Optional.ofNullable(sysUser.getUserBirthday()).map(Date::getTime).ifPresent(userBasicInfoVO::setUserBirthday);
        Optional.ofNullable(sysUser.getUserValidTime()).map(Date::getTime).ifPresent(userBasicInfoVO::setUserValidTime);

        return BuildUtil.buildListVO(userBasicInfoVO);
    }

    @Override
    public void uploadAvatar(MultipartFile file) {

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        float returnValue = 0F;
        SysUser sysUser = new SysUser();


        try {
            BigDecimal fileSize = new BigDecimal(file.getInputStream().available());
            BigDecimal megabyte = new BigDecimal(1024 * 1024);
            returnValue = fileSize.divide(megabyte, 1, BigDecimal.ROUND_DOWN).floatValue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (returnValue >= CommonConstant.PICTURE_UNIVERSITY_RESTRICTIONS) {
            throw new CommonException(FileErrorCode.FILE_ERROR_05);
        }


        try {
            final String objName = BaseUtil.getUId() + ".jpg";
            UploadResultBean uploadResultBean = fileUploadUtil.uploadImages(BucketNamePool.HEAD_URL_BUCKET, objName, file.getInputStream());
            sysUser.setId(loginUser.getId());
            sysUser.setPhotoBucketName(uploadResultBean.getBucketName());
            sysUser.setPhotoObjectName(uploadResultBean.getObjName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public BaseVO<SecuritySettingsVO> getSecuritySettings() {

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SecuritySettingsVO securitySettingsVO = new SecuritySettingsVO();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        BeanUtils.copyProperties(sysUser, securitySettingsVO);

        List<SysServiceSetting> sysServiceSettings = sysServiceSettingMapper.findBySysServiceType(SysServiceType.SECURITY_MANAGE.getType());
        AccountSafeSetting accountSafeSetting = accountSafeSettingMapper.findBySysUserId(loginUser.getId());

        Map<String, String> securityManageMap = sysServiceSettings.stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, SysServiceSetting::getSysServiceValue));
        ManageSecurity manageSecurity = BeanUtil.mapToBean(securityManageMap, ManageSecurity.class, Boolean.TRUE);

        PasswordStrength passwordStrength = PasswordStrength.findByType(manageSecurity.getPasswordStrength());
        securitySettingsVO.setPasswordStrength(passwordStrength.getType());

        if (accountSafeSetting != null) {
            securitySettingsVO.setEmailSafeType(accountSafeSetting.getEmailSafeType());
            securitySettingsVO.setPhoneSafeType(accountSafeSetting.getPhoneSafeType());
        }

        return BuildUtil.buildListVO(securitySettingsVO);
    }

    @Override
    public void loginSecondaryAuthentication(AddAccountSafeSettingDTO addAccountSafeSettingDTO) {

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        AccountSafeSetting accountSafeSetting = new AccountSafeSetting();
        accountSafeSetting.setSysUserId(loginUser.getId());
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        if(sysUser.getUserSafePhone() != null){
            accountSafeSetting.setPhoneSafeType(addAccountSafeSettingDTO.getPhoneSafeType());
        }
        if(sysUser.getUserSafeEmail() != null){
            accountSafeSetting.setEmailSafeType(addAccountSafeSettingDTO.getEmailSafeType());
        }
        AccountSafeSetting setting = accountSafeSettingMapper.findBySysUserId(loginUser.getId());
        if (setting != null) {
            accountSafeSettingMapper.updateBySysUserId(accountSafeSetting, loginUser.getId());
        } else {
            accountSafeSettingMapper.insert(accountSafeSetting);
        }
    }

    @Override
    public void updatePasswordByOldPassword(UpdateNewPsdByOldPsdDTO updateNewPsdByOldPsdDTO) {
        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        //生成旧密码
        String oldPassword = PasswordUtil.encrypt(
                sysUser.getUserAccountName(), updateNewPsdByOldPsdDTO.getOldPassword(), sysUser.getUserPasswdSalt());

        if (!sysUser.getUserPassword().equals(oldPassword)) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_18);
        }

        updatePassword(sysUser, updateNewPsdByOldPsdDTO.getNewPassword());
    }

    @Override
    public void sendBindPhoneVerificationCode(VerifyPhoneDTO verifyPhoneDTO) {
        final String templateId = globalClass.getMappingValue("templateId");
        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        List<SysUser> userPhones = new ArrayList<>();
        if(!StringUtils.isEmpty(verifyPhoneDTO.getPhone())){
            userPhones =  sysUserMapper.findByUserSafePhone(verifyPhoneDTO.getPhone());
        }

        if (!StringUtils.isEmpty(sysUser.getUserSafePhone()) || userPhones.size() > 0) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_27);
        }

        if (!StringUtils.isEmpty(verifyPhoneDTO.getPhone())) {
            sendMsgUtil.send(templateId, verifyPhoneDTO.getPhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_BIND);
        } else {
            if (StringUtils.isEmpty(sysUser.getUserPhone())) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_31);
            }
            sendMsgUtil.send(templateId, sysUser.getUserPhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_BIND);
        }
    }


    @Override
    public void sendBindEmailVerificationCode(VerifyMailboxDTO verifyMailboxDTO) {
        String subject = "邮箱绑定验证码";

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        List<SysUser> userEmails = new ArrayList<>();
        if(!StringUtils.isEmpty(verifyMailboxDTO.getMailbox())){
            userEmails =  sysUserMapper.findByUserSafeEmail(verifyMailboxDTO.getMailbox());
        }

        if (!StringUtils.isEmpty(sysUser.getUserSafeEmail()) || userEmails.size() > 0) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_25);
        }

        if (!StringUtils.isEmpty(verifyMailboxDTO.getMailbox())) {
            mailUtil.send(verifyMailboxDTO.getMailbox(), subject, CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_BIND);
        } else {
            if (StringUtils.isEmpty(sysUser.getUserEmail())) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_30);
            }
            mailUtil.send(sysUser.getUserEmail(), subject, CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_BIND);
        }
    }


    @Override
    public void sendUpdatePhoneVerificationCode() {
        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        final String templateId = globalClass.getMappingValue("templateId");
        if (StringUtils.isEmpty(sysUser.getUserSafePhone())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_28);
        }


        sendMsgUtil.send(templateId, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UPDATE);

    }

    @Override
    public void sendUpdateEmailVerificationCode() {
        String subject = "邮箱密码修改验证码";

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        if (StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_26);
        }


        mailUtil.send(sysUser.getUserSafeEmail(), subject, CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UPDATE);

    }

    @Override
    public void sendUnbindPhoneVerificationCode() {

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        final String templateId = globalClass.getMappingValue("templateId");
        if (StringUtils.isEmpty(sysUser.getUserSafePhone())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_28);
        }

        sendMsgUtil.send(templateId, sysUser.getUserSafePhone(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UNBIND);
    }

    @Override
    public void sendUnbindEmailVerificationCode() {

        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        if (StringUtils.isEmpty(sysUser.getUserSafeEmail())) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_26);
        }

        mailUtil.send(sysUser.getUserSafeEmail(), "邮箱解绑验证码", CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UNBIND);

    }


    @Override
    public void checkUpdatePhoneVerificationCode(PhoneVerificationCodeDTO phoneVerificationCodeDTO) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        checkVerificationCode(
                sysUser.getUserSafePhone(),
                phoneVerificationCodeDTO.getVerificationCode(),
                CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UPDATE,
                CacheConstant.VERIFICATION_CODE_CACHE_PHONE_SUCCESS_UPDATE,
                sysUser
        );
    }

    @Override
    public void checkUpdateEmailVerificationCode(MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        checkVerificationCode(
                sysUser.getUserSafeEmail(),
                mailboxVerificationCodeDTO.getVerificationCode(),
                CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UPDATE,
                CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_SUCCESS_UPDATE,
                sysUser
        );
    }


    @Override
    public void updatePhonePasswordByCode(UpdatePsdDTO updatePsdDTO) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        updatePasswordByCode(
                updatePsdDTO.getNewPassword(),
                sysUser.getUserSafePhone(),
                CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UPDATE,
                CacheConstant.VERIFICATION_CODE_CACHE_PHONE_SUCCESS_UPDATE,
                sysUser);
    }

    @Override
    public void updateEmailPasswordByCode(UpdatePsdDTO updatePsdDTO) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
        updatePasswordByCode(
                updatePsdDTO.getNewPassword(),
                sysUser.getUserSafeEmail(),
                CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UPDATE,
                CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_SUCCESS_UPDATE,
                sysUser);
    }


    @Override
    public void bindPhone(PhoneVerificationCodeDTO phoneVerificationCodeDTO) {


        if (!StringUtils.isEmpty(phoneVerificationCodeDTO.getVerificationCode())) {
            checkVerificationCode(
                    phoneVerificationCodeDTO.getPhone(),
                    phoneVerificationCodeDTO.getVerificationCode(),
                    CacheConstant.VERIFICATION_CODE_CACHE_PHONE_BIND
            );
        } else {
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

            if (StringUtils.isEmpty(sysUser.getUserEmail())) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_31);
            }

            checkVerificationCode(
                    sysUser.getUserPhone(),
                    phoneVerificationCodeDTO.getVerificationCode(),
                    CacheConstant.VERIFICATION_CODE_CACHE_PHONE_BIND
            );
        }


    }

    @Override
    public void unbindPhone(PhoneVerificationCodeDTO phoneVerificationCodeDTO) {
        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        unBind(sysUser.getUserSafePhone(), phoneVerificationCodeDTO.getVerificationCode(), CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UNBIND, sysUser.getId());
    }

    @Override
    public void bindMailbox(MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {

        if (!StringUtils.isEmpty(mailboxVerificationCodeDTO.getMailbox())) {

            checkVerificationCode(
                    mailboxVerificationCodeDTO.getMailbox(),
                    mailboxVerificationCodeDTO.getVerificationCode(),
                    CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_BIND);

        } else {
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            SysUser sysUser = sysUserMapper.selectById(loginUser.getId());
            if (StringUtils.isEmpty(sysUser.getUserEmail())) {
                throw new CommonException(CommonErrorCode.COMMON_ERROR_30);
            }
            checkVerificationCode(
                    sysUser.getUserEmail(),
                    mailboxVerificationCodeDTO.getVerificationCode(),
                    CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_BIND);
        }


    }

    @Override
    public void unbindMailbox(MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {
        final LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final SysUser sysUser = sysUserMapper.selectById(loginUser.getId());

        unBind(sysUser.getUserSafeEmail(), mailboxVerificationCodeDTO.getVerificationCode(), CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UNBIND, sysUser.getId());
    }

    @Override
    public void checkFrequency(String way, Integer verificationCode, String cache) {
        String key = String.format("%s:%s", cache, way);
        final String prefixClearFrequency = String.format(CacheConstant.VERIFICATION_CODE_INVALIDATION_FREQUENCY,cache,way);
        if (!redisUtil.hasKey(key)) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_55);
        }

        Integer code = Convert.toInt(redisUtil.get(key));
        if ( !verificationCode.equals(code)) {
            Integer frequency = Convert.toInt(redisUtil.get(prefixClearFrequency));
            redisUtil.set(prefixClearFrequency, Convert.toStr(StringUtils.isEmpty(frequency) ? 1 : ++frequency),3, TimeUnit.MINUTES);
            if (!StringUtils.isEmpty(frequency) && frequency >= CommonConstant.VERIFICATION_CODE_INVALIDATION_FREQUENCY) {
                redisUtil.del(prefixClearFrequency);
                redisUtil.del(key);
            }
            throw new CommonException(ParamErrorCode.PARAM_ERROR_44);
        }
        redisUtil.del(prefixClearFrequency);
        redisUtil.del(key);
    }

    /**
     * 验证邮箱
     *
     * @param way              邮件或手机 的方式
     * @param verificationCode 验证码
     * @param cache            验证缓存位置,
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void checkVerificationCode(String way, Integer verificationCode, String cache) {
        checkFrequency(way, verificationCode, cache);

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SysUser updateSysUser = new SysUser();
        updateSysUser.setId(loginUser.getId());
        updateSysUser.setUpdateTime(new Date());

        if (CacheConstant.VERIFICATION_CODE_CACHE_PHONE_BIND.equals(cache)) {
            updateSysUser.setUserSafePhone(way);
            updateSysUser.setUserPhone(way);
        } else if (CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_BIND.equals(cache)) {
            updateSysUser.setUserSafeEmail(way);
            updateSysUser.setUserEmail(way);
        }

        sysUserMapper.updateById(updateSysUser);
    }


    /**
     * 检查验证码
     *
     * @param way              邮件或手机 的方式
     * @param verificationCode 验证码
     * @param cache            验证缓存位置
     * @param cacheSuccess     验证成功缓存位置
     * @param sysUser          用户
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public void checkVerificationCode(String way, Integer verificationCode, String cache, String cacheSuccess, SysUser sysUser) {

        checkFrequency(way, verificationCode, cache);

        String success = String.format("%s:%s", cacheSuccess, sysUser.getUserAccountName());
        redisUtil.set(success, sysUser.getId(), 300);
    }

    /**
     * 判断验证码是否正确,正确更新密码
     *
     * @param way          邮件或手机 的方式
     * @param cache        验证缓存位置
     * @param cacheSuccess 验证成功缓存位置
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public void updatePasswordByCode(String newPassword, String way, String cache, String cacheSuccess, SysUser sysUser) {

        String key = String.format("%s:%s", cache, way);
        String success = String.format("%s:%s", cacheSuccess, sysUser.getUserAccountName());
        if (redisUtil.hasKey(success)) {
            updatePassword(sysUser, newPassword);
            redisUtil.del(key);
            redisUtil.del(success);
        } else {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_52);
        }
    }

    /**
     * 更新密码
     *
     * @param sysUser     系统用户
     * @param newPassword 新密码
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public void updatePassword(SysUser sysUser, String newPassword) {

        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();
        PasswordStrength passwordStrength = PasswordStrength.findByType(manageSecurity.getPasswordStrength());
        if (!Pattern.compile(passwordStrength.getCompile()).matcher(newPassword).matches()) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_19);
        }

        AccountSafeSetting accountSafeSetting = new AccountSafeSetting();
        accountSafeSetting.setAccountPrePasswd(sysUser.getUserPassword());
        accountSafeSetting.setAccountPrePasswdTime(new Date());
        accountSafeSettingMapper.updateBySysUserId(accountSafeSetting, sysUser.getId());

        //生成新密码
        String newPasswordEncryption = PasswordUtil.encrypt(
                sysUser.getUserAccountName(), newPassword, sysUser.getUserPasswdSalt());

        if (sysUser.getUserPassword().equals(newPasswordEncryption)) {
            throw new CommonException(AppErrorCode.NEW_AND_OLD_PASSWORDS_SAME_ERROR);
        }

        SysUser updateSysUser = new SysUser();
        updateSysUser.setId(sysUser.getId());
        updateSysUser.setUserPassword(newPasswordEncryption);
        updateSysUser.setUpdateTime(new Date());
        updateSysUser.setUserStatus(UserStatus.NORMAL.getStatus());


        sysUserMapper.updateById(updateSysUser);


    }

    /**
     * 解绑
     *
     * @param way              邮件或手机 的方式
     * @param verificationCode 验证码
     * @param cache            验证缓存位置
     * @param sysUserId        用户id
     * @author WeiQiangMiao
     * @date 2021-03-22
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void unBind(String way, Integer verificationCode, String cache, Integer sysUserId) {


        checkFrequency(way, verificationCode, cache);

        if (CacheConstant.VERIFICATION_CODE_CACHE_PHONE_UNBIND.equals(cache)) {
            sysUserService.unbindPhone(sysUserId);
        } else if (CacheConstant.VERIFICATION_CODE_CACHE_EMAIL_UNBIND.equals(cache)) {
            sysUserService.unbindEmail(sysUserId);
        }

    }

}
