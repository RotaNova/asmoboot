package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 账户安全设置dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@Data
public class ManageSecurityDTO implements Serializable {

    /**
     * 密码过期时间(天)
     */
    private Integer accountPassOutMins;

    /**
     * 密码过期是否开启:0-关闭;1-开启
     */
    @NotNull(message = "密码过期开关不能为空")
    private Integer accountPassOutOn;

    /**
     * 账户锁定策略: 0-管理;1-开启
     */
    @NotNull(message = "账户锁定策略开关不能为空")
    private Integer  accountLockoutStrategyOn;


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
    @NotNull(message = "密码强度开关不能为空")
    private Integer passwordStrength;

    /**
     * 管理员登录IP过滤是否开启：0-开启;1-关闭
     */
    @NotNull(message = "管理员登录IP过滤开关不能为空")
    private Integer adminLoginIpFilteringOn;

    /**
     * 管理员登录IP过滤
     */
    private String adminLoginIpFiltering;

    /**
     * 成员登录IP过滤是否开启：0-开启;1-关闭
     */
    @NotNull(message = "成员登录IP过滤开关不能为空")
    private Integer memberLoginIpFilteringOn;

    /**
     * 成员登录IP过滤
     */
    private String memberLoginIpFiltering;


    /**
     * 上次登录信息提示开关:0-关闭;1-开启
     */
    @NotNull(message = "上次登录信息提示开关不能为空")
    private Integer lastLoginInfoPromptOn;

    /**
     * 单点登录开关:0-关闭;1-开启
     */
    @NotNull(message = "单点登录开关不能为空")
    private Integer singleSignOn;

    /**
     * 初始密码强制修改开关:0-关闭;1-开启
     */
    @NotNull(message = "初始密码强制修改开关不能为空")
    private Integer passMandatoryModificationOn;

    /**
     * 单次登录有效时间
     */
    @NotNull(message = "单次登录有效时间不能为空")
    private Integer singleLoginValidTime;

}