package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 安全设置签证官
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
@Data
public class SecuritySettingsVO implements Serializable {

    /**
     * 系统用户邮件
     */
    private String userEmail;

    /**
     * 系统用户联系手机号
     */
    private String userPhone;

    /**
     * 用户安全的电话
     */
    private String userSafePhone;

    /**
     * 用户安全的电子邮件
     */
    private String userSafeEmail;

    /**
     * 密码强度 1-低 2中 3-高
     */
    @Dict(dicCode = "password_strength")
    private Integer passwordStrength;

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



}
