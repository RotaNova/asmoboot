package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.system.dto.CheckRetrievePwCaptchaDTO;
import com.rotanava.boot.system.api.module.system.dto.DingDingAuthDTO;
import com.rotanava.boot.system.api.module.system.dto.FirstPasswordDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessTokenDTO;
import com.rotanava.boot.system.api.module.system.dto.IntegrityVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.ListUserLoginInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.PassWordLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.PhoneLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.RetrievePwDTO;
import com.rotanava.boot.system.api.module.system.dto.SecondaryVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.UserAccountNameDTO;
import com.rotanava.boot.system.api.module.system.dto.VerifyPhoneDTO;
import com.rotanava.boot.system.api.module.system.vo.AccessInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AccessTokenVO;
import com.rotanava.boot.system.api.module.system.vo.AccountSafeSettingVO;
import com.rotanava.boot.system.api.module.system.vo.DdScanLoginParamVO;
import com.rotanava.boot.system.api.module.system.vo.GetBindInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 管理用户控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/v1/manageUser")
public class ManageUserController {
    
    @Autowired
    private SysUserService sysUserService;
    
    
    @AutoLog(value = "密码登录", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/passWordLogin")
    public RetData<UserLoginVO> passWordLogin(@RequestBody PassWordLoginDTO passWordLoginDTO, HttpServletRequest request) {
        
        
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);


        return RetData.ok(sysUserService.passWordLogin(passWordLoginDTO, header, ipAddr));
        
    }
    
    @AutoLog(value = "退出登录", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/logout")
    public RetData<Void> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        
        sysUserService.logout(token);
        
        return RetData.ok();
    }
    
    //    @AutoLog(value = "在线记录",operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/onlineRecord")
    public RetData<Void> onlineRecord(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        sysUserService.onlineRecord(token);
        return RetData.ok();
    }
    
    @AutoLog("获取在线记录列表")
    @AdviceResponseBody
    @PostMapping("/listOnlineRecord")
    public RetData<BaseVO<UserLoginInfoVO>> listOnlineRecord(@RequestBody ListUserLoginInfoDTO listUserLoginInfoDTO) {
        return RetData.ok(sysUserService.listOnlineRecord(listUserLoginInfoDTO));
    }
    
    @AutoLog(value = "下线", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/kickOffTheAssemblyLine")
    public RetData<Void> kickOffTheAssemblyLine(String loginName) {
        sysUserService.kickOffTheAssemblyLine(loginName);
        return RetData.ok();
    }
    
    @AutoLog(value = "发送登录电话验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendLoginPhoneVerificationCode")
    public RetData<Void> sendLoginPhoneVerificationCode(@RequestBody VerifyPhoneDTO verifyPhoneDTO) {
        sysUserService.sendLoginPhoneVerificationCode(verifyPhoneDTO);
        return RetData.ok();
    }
    
    @AutoLog(value = "手机登录", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/phoneLogin")
    public RetData<UserLoginVO> phoneLogin(@RequestBody PhoneLoginDTO phoneLoginDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        return RetData.ok(sysUserService.phoneLogin(phoneLoginDTO, header, ipAddr));
    }
    
    @AutoLog(value = "获取二次验证", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getSecondVerification")
    public RetData<BaseVO<AccountSafeSettingVO>> getSecondVerification(@RequestBody UserAccountNameDTO loginDTO) {
        return RetData.ok(sysUserService.getSecondVerification(loginDTO));
    }
    
    @AutoLog(value = "发送二次手机验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendSecondPhoneVerificationCode")
    public RetData<Void> sendSecondPhoneVerificationCode(@RequestBody UserAccountNameDTO loginDTO) {
        sysUserService.sendSecondPhoneVerificationCode(loginDTO);
        return RetData.ok();
    }
    
    @AutoLog(value = "发送二次邮箱验证码", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/sendSecondaryEmailVerificationCode")
    public RetData<Void> sendSecondaryEmailVerificationCode(@RequestBody UserAccountNameDTO loginDTO) {
        sysUserService.sendSecondaryEmailVerificationCode(loginDTO);
        return RetData.ok();
    }
    
    @AutoLog(value = "手机完整性验证", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/mobilePhoneIntegrityVerification")
    public RetData<UserLoginVO> mobilePhoneIntegrityVerification(@RequestBody IntegrityVerificationLoginDTO integrityVerificationLoginDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        return RetData.ok(sysUserService.mobilePhoneIntegrityVerification(integrityVerificationLoginDTO, header, ipAddr));
    }
    
    @AutoLog(value = "邮件完整性验证", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/mobileEmailIntegrityVerification")
    public RetData<UserLoginVO> mobileEmailIntegrityVerification(@RequestBody IntegrityVerificationLoginDTO integrityVerificationLoginDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        return RetData.ok(sysUserService.mobileEmailIntegrityVerification(integrityVerificationLoginDTO, header, ipAddr));
    }
    
    @AutoLog(value = "二次手机登录", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/secondPhoneLogin")
    public RetData<UserLoginVO> secondPhoneLogin(@RequestBody SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        return RetData.ok(sysUserService.secondPhoneLogin(secondaryVerificationLoginDTO, header, ipAddr));
    }
    
    @AutoLog(value = "二次邮箱登录", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/secondEmailLogin")
    public RetData<UserLoginVO> secondEmailLogin(@RequestBody SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        return RetData.ok(sysUserService.secondEmailLogin(secondaryVerificationLoginDTO, header, ipAddr));
    }
    
    
    @AutoLog(value = "初次密码修改", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateFirstPassword")
    public RetData<Void> updateFirstPassword(@RequestBody FirstPasswordDTO firstPasswordDTO) {
        sysUserService.updateFirstPassword(firstPasswordDTO);
        return RetData.ok();
        
    }
    
    @AutoLog(value = "获取用户信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getUserInfo")
    public RetData<UserLoginVO> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        
        return RetData.ok(sysUserService.getUserInfo(token));
        
    }
    
    @AutoLog(value = "获取访问令牌", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getAccessToken")
    public RetData<AccessTokenVO> getAccessToken(@RequestBody GetAccessTokenDTO getAccessTokenDTO) {
        return RetData.ok(sysUserService.getAccessToken(getAccessTokenDTO));
        
    }
    
    @AutoLog(value = "获取访问信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getAccessInfo")
    public RetData<AccessInfoVO> getAccessInfo(@RequestBody GetAccessInfoDTO getAccessInfoDTO) {
        return RetData.ok(sysUserService.getAccessInfo(getAccessInfoDTO));
        
    }
    
    
    @AutoLog(value = "获取绑定信息", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getBindInfo")
    public RetData<GetBindInfoVO> getBindInfo(@RequestBody UserAccountNameDTO userAccountNameDTO) {
        return RetData.ok(sysUserService.getBindInfo(userAccountNameDTO));
    }
    
    
    @AutoLog(value = "发送手机号找回密码验证码", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/sendRetrievePwPhoneCaptcha")
    public RetData<Void> sendRetrievePwPhoneCaptcha(@RequestBody CheckRetrievePwCaptchaDTO captchaDTO) {
        sysUserService.sendRetrievePwPhoneCaptcha(captchaDTO);
        return RetData.ok();
    }
    
    @AutoLog(value = "发送邮箱找回密码验证码", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/sendRetrievePwEmailCaptcha")
    public RetData<Void> sendRetrievePwEmailCaptcha(@RequestBody CheckRetrievePwCaptchaDTO captchaDTO) {
        sysUserService.sendRetrievePwEmailCaptcha(captchaDTO);
        return RetData.ok();
    }
    
    
    @AutoLog(value = "校验手机验证码成功修改密码", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/updatePhonePasswordByCode")
    public RetData<Void> updatePhonePasswordByCode(@RequestBody RetrievePwDTO retrievePwDTO) {
        sysUserService.updatePhonePasswordByCode(retrievePwDTO);
        return RetData.ok();
    }
    
    
    @AutoLog(value = "校验邮件验证码成功修改密码", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/updateEmailPasswordByCode")
    public RetData<Void> updateEmailPasswordByCode(@RequestBody RetrievePwDTO retrievePwDTO) {
        sysUserService.updateEmailPasswordByCode(retrievePwDTO);
        return RetData.ok();
    }
    
    
    @AutoLog(value = "生成图片验证码", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/getVerify")
    public RetData<String> getVerify(@RequestBody UserAccountNameDTO userAccountNameDTO) {
        
        return RetData.ok(sysUserService.getVerify(userAccountNameDTO));
    }
    
    /**
     * 功能: 钉钉扫码auth
     * 作者: zjt
     * 日期: 2021/12/28 14:35
     * 版本: 1.0
     */
    @AutoLog(value = "钉钉扫码登陆", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/dingDingAuth")
    public RetData<UserLoginVO> dingDingAuth(@RequestBody DingDingAuthDTO dingDingAuthDTO, HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.USER_AGENT);
        String ipAddr = IPUtils.getIpAddr(request);
        final UserLoginVO userLoginVO = sysUserService.dingDingAuth(dingDingAuthDTO, header, ipAddr);
        return RetData.ok(userLoginVO);
    }
    
    /**
     * 功能: 获取钉钉扫码登陆参数
     * 作者: zjt
     * 日期: 2021/12/28 18:48
     * 版本: 1.0
     */
    @AutoLog(value = "获取钉钉扫码登陆参数", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getDdScanLoginParam")
    public RetData<DdScanLoginParamVO> getDdScanLoginParam() {
        final DdScanLoginParamVO ddScanLoginParamVO = sysUserService.getDdScanLoginParam();
        return RetData.ok(ddScanLoginParamVO);
    }
}