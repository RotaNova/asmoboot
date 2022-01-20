package com.rotanava.boot.system.api.module.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;


/**
 * 账户安全设置vo
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
@Data
public class AccountSafeSettingVO implements Serializable {


    /**
     * 手机安全保护类型:0-无:1-验证号码完整性;2-验证验证码
     */
    @Dict(dicCode = "phone_safe_type")
    private Integer phoneSafeType;

    /**
     * 邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码
     */
    @Dict(dicCode = "email_safe_type")
    private Integer emailSafeType;

    /**
     * 用户的电子邮件
     */
    @JsonInclude
    private String userEmail;

    /**
     * 用户的电话
     */
    @JsonInclude
    private String userPhone;


}