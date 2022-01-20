package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户登录信息表
 * </p>
 *
 * @author WeiQiangMiao
 * @since 2021-03-04
 */
@Data
public class UserLoginInfoVO implements Serializable {

    /**
     * 登录用户的ID
     */
    private Integer loginUserId;

    /**
     * 登录的用户名
     */
    private String loginName;

    /**
     * 登录的IP地址
     */
    private String loginIp;

    /**
     * 登录的物理地址
     */
    private String loginLocation;

    /**
     * 登录的浏览器媒介
     */
    private String loginBrowser;

    /**
     * 登录的操作系统
     */
    private String loginOs;

    /**
     * 登录信息的描述
     */
    private String loginDescription;


    /**
     * 登录鉴权方式：0-匿名;1-账号密码;2-验证码;
     */
    @Dict(dicCode = "login_access_type")
    private Integer loginAccessType;

    /**
     * 在线时间
     */
    private Long onlineTime;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录状态
     */
    @Dict(dicCode = "login_status")
    private Integer loginStatus;

}
