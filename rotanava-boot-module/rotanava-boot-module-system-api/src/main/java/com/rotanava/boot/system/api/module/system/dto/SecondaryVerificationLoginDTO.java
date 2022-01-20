package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 完整度验证
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecondaryVerificationLoginDTO extends UserAccountNameDTO {


    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private Integer verificationCode;


    /**
     * 登录的位置
     */
    private String loginLocation;

}
