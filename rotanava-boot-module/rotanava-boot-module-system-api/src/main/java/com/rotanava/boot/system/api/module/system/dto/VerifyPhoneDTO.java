package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 验证手机dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Data
public class VerifyPhoneDTO {

    /**
     * 电话
     */
    private String phone;
}
