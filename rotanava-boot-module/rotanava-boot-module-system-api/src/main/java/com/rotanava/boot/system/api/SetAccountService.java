package com.rotanava.boot.system.api;

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
import com.rotanava.framework.model.BaseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 设置账户服务
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Validated
public interface SetAccountService {


    /**
     * 更新用户基本信息
     *
     * @param updateUserBasicInfoDTO 更新用户基本信息
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateUserBasicInfo(@Valid UpdateUserBasicInfoDTO updateUserBasicInfoDTO,@RequestParam(required = false) MultipartFile multipartFile);

    /**
     * 获取用户基本信息
     *
     * @return {@link BaseVO<UserBasicInfoVO> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<UserBasicInfoVO> getUserBasicInfo();


    /**
     * 上传头像
     *
     * @param file 文件
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void uploadAvatar(MultipartFile file);

    /**
     * 获取安全设置
     *
     * @return {@link BaseVO<SecuritySettingsVO> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<SecuritySettingsVO> getSecuritySettings();

    /**
     * 登录二级认证
     *
     * @param addAccountSafeSettingDTO 添加账户安全设置dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void loginSecondaryAuthentication(@Valid AddAccountSafeSettingDTO addAccountSafeSettingDTO);

    /**
     * 通过旧密码更新密码
     *
     * @param updateNewPsdByOldPsdDTO 更新新psd按老psd dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updatePasswordByOldPassword(@Valid UpdateNewPsdByOldPsdDTO updateNewPsdByOldPsdDTO);

    /**
     * 发送绑定手机验证码
     *
     * @param verifyPhoneDTO 手机验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendBindPhoneVerificationCode(@Valid VerifyPhoneDTO verifyPhoneDTO);

    /**
     * 发送绑定邮件验证码
     *
     * @param verifyMailboxDTO 验证邮箱dto
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendBindEmailVerificationCode(@Valid VerifyMailboxDTO verifyMailboxDTO);


    /**
     * 发送修改密码手机验证码
     *
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendUpdatePhoneVerificationCode();

    /**
     * 发送修改密码邮箱验证码
     *
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendUpdateEmailVerificationCode();


    /**
     * 发送解绑手机验证码
     *
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendUnbindPhoneVerificationCode();


    /**
     * 发送解绑邮件验证码
     *
     * @author WeiQiangMiao
     * @date 2021-03-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendUnbindEmailVerificationCode();


    /**
     * 检查手机验证码
     *
     * @param phoneVerificationCodeDTO 手机验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void checkUpdatePhoneVerificationCode(@Valid PhoneVerificationCodeDTO phoneVerificationCodeDTO);

    /**
     * 检查电子邮件验证码
     *
     * @param mailboxVerificationCodeDTO 邮箱验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void checkUpdateEmailVerificationCode(@Valid MailboxVerificationCodeDTO mailboxVerificationCodeDTO);

    /**
     * 校验手机验证码成功修改密码
     *
     * @param updatePsdDTO 更新psd dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updatePhonePasswordByCode(@Valid UpdatePsdDTO updatePsdDTO);

    /**
     * 校验邮件验证码成功修改密码
     *
     * @param updatePsdDTO 更新psd dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateEmailPasswordByCode(@Valid UpdatePsdDTO updatePsdDTO);


    /**
     * 绑定手机
     *
     * @param phoneVerificationCodeDTO 手机验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void bindPhone(@Valid PhoneVerificationCodeDTO phoneVerificationCodeDTO);

    /**
     * 解开手机
     *
     * @param phoneVerificationCodeDTO 手机验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void unbindPhone(@Valid PhoneVerificationCodeDTO phoneVerificationCodeDTO);


    /**
     * 绑定邮箱
     *
     * @param mailboxVerificationCodeDTO 邮箱验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void bindMailbox(@Valid MailboxVerificationCodeDTO mailboxVerificationCodeDTO);

    /**
     * 解开邮箱
     *
     * @param mailboxVerificationCodeDTO 邮箱验证码dto
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void unbindMailbox(@Valid MailboxVerificationCodeDTO mailboxVerificationCodeDTO);

    /**
     * 检查验证码
     *
     * @param way              道路
     * @param verificationCode 验证码
     * @param cache            缓存
     * @param cacheSuccess     缓存成功
     * @param sysUser          系统用户
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void checkVerificationCode(String way, Integer verificationCode, String cache, String cacheSuccess, SysUser sysUser);

    /**
     * 判断验证码是否正确,正确更新密码
     *
     * @param newPassword  新密码
     * @param way          道路
     * @param cache        缓存
     * @param cacheSuccess 缓存成功
     * @param sysUser      系统用户
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updatePasswordByCode(String newPassword, String way, String cache, String cacheSuccess, SysUser sysUser);

    /**
     * 功能: 重置密码
     * 作者: zjt
     * 日期: 2021/3/26 14:04
     * 版本: 1.0
     */
    void updatePassword(SysUser sysUser, String newPassword);

    /**
     * 检查频率
     *
     * @param way              的方式
     * @param verificationCode 验证码
     * @param cache            缓存
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void checkFrequency(String way, Integer verificationCode, String cache);
}
