package com.rotanava.framework.model.bo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 账户安全设置
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@Data
public class ManageSecurity implements Serializable {


    /**
     * 密码过期时间(天)
     */
    private Integer accountPassOutMins;

    /**
     * 密码过期是否开启:0-关闭;1-开启
     */
    private Integer accountPassOutOn;

    /**
     * 账户锁定策略: 0-管理;1-开启
     */
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
    private Integer passwordStrength;

    /**
     * 管理员登录IP过滤是否开启：0-开启;1-关闭
     */
    private Integer adminLoginIpFilteringOn;

    /**
     * 管理员登录IP过滤
     */
    private String adminLoginIpFiltering;

    /**
     * 成员登录IP过滤是否开启：0-开启;1-关闭
     */
    private Integer memberLoginIpFilteringOn;

    /**
     * 成员登录IP过滤
     */
    private String memberLoginIpFiltering;

    /**
     * 上次登录信息提示开关:0-关闭;1-开启
     */
    private Integer lastLoginInfoPromptOn;

    /**
     * 单点登录开关:0-关闭;1-开启
     */
    private Integer singleSignOn;

    /**
     * 初始密码强制修改开关:0-关闭;1-开启
     */
    private Integer passMandatoryModificationOn;

    /**
     * 单次登录有效时间
     */
    @NotNull(message = "单次登录有效时间不能为空")
    private Integer singleLoginValidTime;
}