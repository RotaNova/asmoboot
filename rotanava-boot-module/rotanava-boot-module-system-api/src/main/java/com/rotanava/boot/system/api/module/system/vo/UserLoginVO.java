package com.rotanava.boot.system.api.module.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户VO
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class UserLoginVO implements Serializable {
    /**
     * 令牌
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    /**
     * 头像地址
     */
    private String userPhotoUrl;
    
    /**
     * 是否管理员
     */
    private Integer userSysrole;
    
    /**
     * 账号名
     */
    private String userAccountName;


    /**
     * 用户名
     */
    private String userName;

    /**
     * logo url
     */
    private String logoUrl;

    /**
     * bannerUrl
     */
    private String bannerUrl;

    /**
     * banner选项开关  1关闭 2开启
     */
    private Integer bannerOption;

    /**
     * banner显示开关 1关闭 2开启
     */
    private Integer bannerShowOption;

    /**
     * banner是否可关闭选项
     */
    private Integer bannerCloseOption;

    /**
     * 密码过期 true-过期 FALSE-有效
     */
    private Boolean passwordExpired;

    /**
     * 密码强度
     */
    private Integer passwordStrength;

    /**
     * 二次验证 0-关闭 1-开启
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer secondaryVerification;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 用户代码
     */
    private String userCode;

}
