package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetBindInfoDTO extends UserAccountNameDTO{

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String captcha;

}
