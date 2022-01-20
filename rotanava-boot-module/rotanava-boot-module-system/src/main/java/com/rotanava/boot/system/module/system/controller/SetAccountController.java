package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SetAccountService;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.SecuritySettingsVO;
import com.rotanava.boot.system.api.module.system.vo.UserBasicInfoVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



/**
 * 设置帐户控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@RestController
@RequestMapping("/v1/setAccount")
public class SetAccountController {


    @Autowired
    private SetAccountService setAccountService;

    @AutoLog(value = "基本信息更新", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/updateUserBasicInfo")
    public RetData<Void> updateUserBasicInfo(UpdateUserBasicInfoDTO updateUserBasicInfoDTO, @RequestParam(name = "MultipartFile",required = false) MultipartFile multipartFile) {

        setAccountService.updateUserBasicInfo(updateUserBasicInfoDTO,multipartFile);
        return RetData.ok();

    }

    @AutoLog(value = "获取基本信息")
    @AdviceResponseBody
    @GetMapping("/getUserBasicInfo")
    public RetData<BaseVO<UserBasicInfoVO>> getUserBasicInfo() {
        return RetData.ok(setAccountService.getUserBasicInfo());
    }

    @AutoLog(value = "上传头像",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/uploadAvatar")
    public RetData<Void> uploadAvatar(@RequestParam (value = "file") MultipartFile file) {
        setAccountService.uploadAvatar(file);
        return RetData.ok();

    }

    @AutoLog(value = "获取安全设置")
    @AdviceResponseBody
    @GetMapping("/getSecuritySettings")
    public RetData<BaseVO<SecuritySettingsVO>> getSecuritySettings() {
        return RetData.ok(setAccountService.getSecuritySettings());
    }



    @AutoLog(value = "登录二次认证", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/loginSecondaryAuthentication")
    public RetData<Void> loginSecondaryAuthentication(@RequestBody AddAccountSafeSettingDTO addAccountSafeSettingDTO) {
        setAccountService.loginSecondaryAuthentication(addAccountSafeSettingDTO);
        return RetData.ok();
    }


    @AutoLog(value = "通过旧密码更新密码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PutMapping("/updatePasswordByOldPassword")
    public RetData<Void> updatePasswordByOldPassword(@RequestBody UpdateNewPsdByOldPsdDTO updateNewPsdByOldPsdDTO) {
        setAccountService.updatePasswordByOldPassword(updateNewPsdByOldPsdDTO);
        return RetData.ok();
    }


    @AutoLog(value = "发送绑定手机验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendBindPhoneVerificationCode")
    public RetData<Void> sendBindPhoneVerificationCode(@RequestBody VerifyPhoneDTO verifyPhoneDTO) {
        setAccountService.sendBindPhoneVerificationCode(verifyPhoneDTO);
        return RetData.ok();
    }

    @AutoLog(value = "发送绑定邮箱验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendBindEmailVerificationCode")
    public RetData<Void> sendBindEmailVerificationCode(@RequestBody VerifyMailboxDTO verifyMailboxDTO) {
        setAccountService.sendBindEmailVerificationCode(verifyMailboxDTO);
        return RetData.ok();
    }

    @AutoLog(value = "发送修改密码手机验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendUpdatePhoneVerificationCode")
    public RetData<Void> sendUpdatePhoneVerificationCode() {
        setAccountService.sendUpdatePhoneVerificationCode();
        return RetData.ok();
    }

    @AutoLog(value = "发送修改密码邮箱验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendUpdateEmailVerificationCode")
    public RetData<Void> sendUpdateEmailVerificationCode() {
        setAccountService.sendUpdateEmailVerificationCode();
        return RetData.ok();
    }

    @AutoLog(value = "发送解绑手机验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendUnbindPhoneVerificationCode")
    public RetData<Void> sendUnbindPhoneVerificationCode() {
        setAccountService.sendUnbindPhoneVerificationCode();
        return RetData.ok();
    }

    @AutoLog(value = "发送解绑邮箱验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendUnbindEmailVerificationCode")
    public RetData<Void> sendUnbindEmailVerificationCode() {
        setAccountService.sendUnbindEmailVerificationCode();
        return RetData.ok();
    }


    @AutoLog(value = "校验更新密码手机验证码")
    @AdviceResponseBody
    @PostMapping("/checkUpdatePhoneVerificationCode")
    public RetData<Void> checkUpdatePhoneVerificationCode(@RequestBody PhoneVerificationCodeDTO phoneVerificationCodeDTO) {
        setAccountService.checkUpdatePhoneVerificationCode(phoneVerificationCodeDTO);
        return RetData.ok();
    }

    @AutoLog(value = "校验更新邮箱验证码")
    @AdviceResponseBody
    @PostMapping("/checkUpdateEmailVerificationCode")
    public RetData<Void> checkUpdateEmailVerificationCode(@RequestBody MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {
        setAccountService.checkUpdateEmailVerificationCode(mailboxVerificationCodeDTO);
        return RetData.ok();
    }

    @AutoLog(value = "校验手机验证码成功修改密码", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updatePhonePasswordByCode")
    public RetData<Void> updatePhonePasswordByCode(@RequestBody UpdatePsdDTO updatePsdDTO) {
        setAccountService.updatePhonePasswordByCode(updatePsdDTO);
        return RetData.ok();
    }


    @AutoLog(value = "校验邮件验证码成功修改密码", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateEmailPasswordByCode")
    public RetData<Void> updateEmailPasswordByCode(@RequestBody UpdatePsdDTO updatePsdDTO) {
        setAccountService.updateEmailPasswordByCode(updatePsdDTO);
        return RetData.ok();
    }

    @AutoLog(value = "绑定手机", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/bindPhone")
    public RetData<Void> bindPhone(@RequestBody PhoneVerificationCodeDTO phoneVerificationCodeDTO) {
        setAccountService.bindPhone(phoneVerificationCodeDTO);
        return RetData.ok();
    }

    @AutoLog(value = "解绑手机", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/unbindPhone")
    public RetData<Void> unbindPhone(@RequestBody PhoneVerificationCodeDTO phoneVerificationCodeDTO) {
        setAccountService.unbindPhone(phoneVerificationCodeDTO);
        return RetData.ok();

    }


    @AutoLog(value = "绑定邮箱", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/bindMailbox")
    public RetData<Void> bindMailbox(@RequestBody MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {
        setAccountService.bindMailbox(mailboxVerificationCodeDTO);
        return RetData.ok();

    }


    @AutoLog(value = "解绑邮箱", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/unbindMailbox")
    public RetData<Void> unbindMailbox(@RequestBody MailboxVerificationCodeDTO mailboxVerificationCodeDTO) {
        setAccountService.unbindMailbox(mailboxVerificationCodeDTO);
        return RetData.ok();

    }


}
