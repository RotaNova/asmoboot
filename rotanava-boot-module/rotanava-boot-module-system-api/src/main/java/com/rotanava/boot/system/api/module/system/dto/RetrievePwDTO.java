package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 找回密码dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RetrievePwDTO extends UserAccountNameDTO{

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private Integer captcha;

}
