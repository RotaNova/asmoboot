package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 完整度验证
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IntegrityVerificationLoginDTO extends UserAccountNameDTO {

    /**
     * 电话
     */
    private String phone;


    /**
     * 邮箱
     */
    private String mailbox;


    /**
     * 登录的位置
     */
    private String loginLocation;

}
