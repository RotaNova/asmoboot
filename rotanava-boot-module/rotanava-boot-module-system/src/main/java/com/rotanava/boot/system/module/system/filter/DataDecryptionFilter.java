package com.rotanava.boot.system.module.system.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.module.system.wrapper.BodyRequestWrapper;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.util.AESUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 进行数据的解密
 *
 * @author zjt
 * @date 2020/8/18
 */
@WebFilter(filterName = "dataDecryptionInterceptors", urlPatterns = {
        "/v1/manageUser/passWordLogin", "/v1/setAccount/updatePhonePasswordByCode", "/v1/setAccount/updateEmailPasswordByCode",
        "/v1/setAccount/updatePasswordByOldPassword",
        "/v1/manageUser/passWordLogin",
        "/v1/sysUserManage/restPassword",
        "/v1/departmentManage/restPassword",
        "/v1/myDepartment/restPassword",
        "/v1/manageUser/mobilePhoneIntegrityVerification",
        "/v1/manageUser/mobileEmailIntegrityVerification",
        "/v1/manageUser/sendSecondPhoneVerificationCode",
        "/v1/manageUser/sendSecondaryEmailVerificationCode",
        "/v1/manageUser/secondPhoneLogin",
        "/v1/manageUser/secondEmailLogin",
        "/v1/manageUser/getSecondVerification",
        "/v1/manageUser/updateFirstPassword",
        "/v1/departmentManage/addSysUser",
        "/v1/myDepartment/addSysUser",
        "/v1/sysUserManage/addSysUser",
        "/v1/manageUser/updatePhonePasswordByCode",
        "/v1/manageUser/updateEmailPasswordByCode",
        "/v1/manageUser/getBindInfo",
        "/v1/manageUser/getVerify",
        "/v1/manageUser/sendRetrievePwPhoneCaptcha",
        "/v1/manageUser/sendRetrievePwEmailCaptcha"
})
@Log4j2
public class DataDecryptionFilter implements Filter {

    //
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        switch (servletRequest.getRequestURI()) {
            case "/v1/manageUser/passWordLogin":
                final PassWordLoginDTO loginDTO = getDTO(request, PassWordLoginDTO.class);
                //这边进行加解密后 明文传入
                String userAccountName = AESUtil.Decrypt(loginDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY);
                String userPassword = AESUtil.Decrypt(loginDTO.getUserPassword(), CommonConstant.AES_DECRYPTION_KEY);
//                if (userAccountName != null) {
                    loginDTO.setUserAccountName(userAccountName);
//                }
//                if (userPassword != null) {
                    loginDTO.setUserPassword(userPassword);
//                }
                get(response, chain, servletRequest, JSONUtil.toJsonStr(loginDTO));
                break;
            case "/v1/setAccount/updatePasswordByOldPassword":
                final UpdateNewPsdByOldPsdDTO updateNewPsdByOldPsdDTO = getDTO(request, UpdateNewPsdByOldPsdDTO.class);
                //这边进行加解密后 明文传入
                String oldPassword = AESUtil.Decrypt(updateNewPsdByOldPsdDTO.getOldPassword(), CommonConstant.AES_DECRYPTION_KEY);
                String newPassword = AESUtil.Decrypt(updateNewPsdByOldPsdDTO.getNewPassword(), CommonConstant.AES_DECRYPTION_KEY);
                if (oldPassword != null) {
                    updateNewPsdByOldPsdDTO.setOldPassword(oldPassword);
                }
                if (newPassword != null) {
                    updateNewPsdByOldPsdDTO.setNewPassword(newPassword);
                }
                get(response, chain, servletRequest, JSONUtil.toJsonStr(updateNewPsdByOldPsdDTO));
                break;
            case "/v1/setAccount/updateEmailPasswordByCode":
            case "/v1/setAccount/updatePhonePasswordByCode":
                final UpdatePsdDTO updatePsdDTO = getDTO(request, UpdatePsdDTO.class);
                //这边进行加解密后 明文传入
                updatePsdDTO.setNewPassword(AESUtil.Decrypt(updatePsdDTO.getNewPassword(), CommonConstant.AES_DECRYPTION_KEY));
                get(response, chain, servletRequest, JSONUtil.toJsonStr(updatePsdDTO));
                break;
            case "/v1/manageUser/updatePhonePasswordByCode":
            case "/v1/manageUser/updateEmailPasswordByCode":
                final RetrievePwDTO retrievePwDTO = getDTO(request, RetrievePwDTO.class);
                //这边进行加解密后 明文传入
                retrievePwDTO.setNewPassword(AESUtil.Decrypt(retrievePwDTO.getNewPassword(), CommonConstant.AES_DECRYPTION_KEY));
                retrievePwDTO.setUserAccountName(AESUtil.Decrypt(retrievePwDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY));
                get(response, chain, servletRequest, JSONUtil.toJsonStr(retrievePwDTO));
                break;
            case "/v1/manageUser/getVerify":
            case "/v1/manageUser/getBindInfo":
                final UserAccountNameDTO getBindInfoDTO = getDTO(request, UserAccountNameDTO.class);
                //这边进行加解密后 明文传入
                getBindInfoDTO.setUserAccountName(AESUtil.Decrypt(getBindInfoDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY));
                get(response, chain, servletRequest, JSONUtil.toJsonStr(getBindInfoDTO));
                break;
            case "/v1/manageUser/sendRetrievePwPhoneCaptcha":
            case "/v1/manageUser/sendRetrievePwEmailCaptcha":
                final CheckRetrievePwCaptchaDTO captchaDTO = getDTO(request, CheckRetrievePwCaptchaDTO.class);
                //这边进行加解密后 明文传入
                captchaDTO.setUserAccountName(AESUtil.Decrypt(captchaDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY));
                get(response, chain, servletRequest, JSONUtil.toJsonStr(captchaDTO));
                break;
            case "/v1/sysUserManage/restPassword":
                restPassword(servletRequest, response, chain);
                break;
            case "/v1/departmentManage/restPassword":
                restPassword(servletRequest, response, chain);
                break;
            case "/v1/myDepartment/restPassword":
                restPassword(servletRequest, response, chain);
                break;
            case "/v1/manageUser/mobilePhoneIntegrityVerification":
            case "/v1/manageUser/mobileEmailIntegrityVerification":
            case "/v1/manageUser/sendSecondPhoneVerificationCode":
            case "/v1/manageUser/sendSecondaryEmailVerificationCode":
            case "/v1/manageUser/secondPhoneLogin":
            case "/v1/manageUser/secondEmailLogin":
            case "/v1/manageUser/getSecondVerification":
                String json = IOUtils.toString(request.getInputStream());
                JSONObject jsonObject = JSONUtil.parseObj(json);
                jsonObject.put("userAccountName", AESUtil.Decrypt(Convert.toStr(jsonObject.get("userAccountName")), CommonConstant.AES_DECRYPTION_KEY));
                get(response, chain, servletRequest, JSONUtil.toJsonStr(jsonObject));
                break;
            case "/v1/manageUser/updateFirstPassword":
                final FirstPasswordDTO firstPasswordDTO = getDTO(request, FirstPasswordDTO.class);
                //这边进行加解密后 明文传入
                userAccountName = AESUtil.Decrypt(firstPasswordDTO.getUserAccountName(), CommonConstant.AES_DECRYPTION_KEY);
                String oldUserPassword = AESUtil.Decrypt(firstPasswordDTO.getOldUserPassword(), CommonConstant.AES_DECRYPTION_KEY);
                String newUserPassword = AESUtil.Decrypt(firstPasswordDTO.getNewUserPassword(), CommonConstant.AES_DECRYPTION_KEY);
                if (userAccountName != null) {
                    firstPasswordDTO.setUserAccountName(userAccountName);
                }
                if (oldUserPassword != null) {
                    firstPasswordDTO.setOldUserPassword(oldUserPassword);
                }
                if (newUserPassword != null) {
                    firstPasswordDTO.setOldUserPassword(newUserPassword);
                }
                get(response, chain, servletRequest, JSONUtil.toJsonStr(firstPasswordDTO));
                break;
            default:
                chain.doFilter(request, response);
                break;
        }
    }

    /**
     * 功能: 重置密码
     * 作者: zjt
     * 日期: 2021/3/12 17:12
     * 版本: 1.0
     */
    private void restPassword(HttpServletRequest request, ServletResponse response, FilterChain chain) throws Exception {
        final RestPasswordDTO restPasswordDTO = getDTO(request, RestPasswordDTO.class);
        //这边进行加解密后 明文传入
        restPasswordDTO.setPassword(AESUtil.Decrypt(restPasswordDTO.getPassword(), CommonConstant.AES_DECRYPTION_KEY));
        get(response, chain, request, JSONUtil.toJsonStr(restPasswordDTO));
    }

    private <T> T getDTO(ServletRequest request, Class<T> cls) throws IOException {
        final String json = IOUtils.toString(request.getInputStream());
        return JSONUtil.toBean(json, cls);
    }

    private void get(ServletResponse response, FilterChain chain, HttpServletRequest servletRequest, String json) throws IOException, ServletException {
        if (!StringUtils.isEmpty(json)) {
            final byte[] body = json.getBytes();
            //request无法修改 需要包装下request
            final BodyRequestWrapper bodyRequestWrapper = new BodyRequestWrapper(servletRequest, body);
            bodyRequestWrapper.getInputStream();
            chain.doFilter(bodyRequestWrapper, response);
        }
    }
}
