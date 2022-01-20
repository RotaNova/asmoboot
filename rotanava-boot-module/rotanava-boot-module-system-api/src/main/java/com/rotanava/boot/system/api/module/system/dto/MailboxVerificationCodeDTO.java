package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 验证码
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MailboxVerificationCodeDTO extends VerifyMailboxDTO implements Serializable {

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private Integer verificationCode;


}
