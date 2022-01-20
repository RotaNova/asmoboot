package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 校验找回密码验证码DTO
 *
 * @author weiqiangmiao
 * @date 2021/12/08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CheckRetrievePwCaptchaDTO extends UserAccountNameDTO{

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;



}
