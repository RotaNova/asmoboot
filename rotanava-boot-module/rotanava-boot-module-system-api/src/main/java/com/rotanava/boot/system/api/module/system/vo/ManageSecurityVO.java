package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 账户安全设置签证官
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@Data
public class ManageSecurityVO implements Serializable {

    /**
     * 密码过期时间(天)
     */
    private Integer accountPassOutMins;

    /**
     * 密码过期是否开启:0-关闭;1-开启
     */
    @Dict(dicCode = "account_pass_out_on")
    private Integer accountPassOutOn;

    /**
     * 账户锁定策略: 0-管理;1-开启
     */
    @Dict(dicCode = "account_lockout_strategy_on")
    private Integer accountLockoutStrategyOn;

    /**
     * 账户锁定策略设置次数
     */
    private Integer accountLockoutStrategyFrequency;

    /**
     * 账户锁定策略时间(分钟)
     */
    private Integer accountLockoutStrategyMins;

    /**
     * 密码强度:1-低;2-中;3-高
     */
    @Dict(dicCode = "password_strength")
    private Integer passwordStrength;


    /**
     * 管理员登录IP过滤是否开启：0-开启;1-关闭
     */
    @Dict(dicCode = "admin_login_ip_filtering_on")
    private Integer adminLoginIpFilteringOn;

    /**
     * 管理员登录IP过滤
     */
    private String adminLoginIpFiltering;

    /**
     * 成员登录IP过滤是否开启：0-开启;1-关闭
     */
    @Dict(dicCode = "member_login_ip_filtering_on")
    private Integer memberLoginIpFilteringOn;

    /**
     * 成员登录IP过滤
     */
    private String memberLoginIpFiltering;


    /**
     * 上次登录信息提示开关:0-关闭;1-开启
     */
    @Dict(dicCode = "last_login_info_prompt_on")
    private Integer lastLoginInfoPromptOn;

    /**
     * 单点登录开关:0-关闭;1-开启
     */
    @Dict(dicCode = "single_sign_on")
    private Integer singleSignOn;

    /**
     * 初始密码强制修改开关:0-关闭;1-开启
     */
    @Dict(dicCode = "pass_mandatory_modification_on")
    private Integer passMandatoryModificationOn;

    /**
     * 单次登录有效时间
     */
    @NotNull(message = "单次登录有效时间不能为空")
    private Integer singleLoginValidTime;

}