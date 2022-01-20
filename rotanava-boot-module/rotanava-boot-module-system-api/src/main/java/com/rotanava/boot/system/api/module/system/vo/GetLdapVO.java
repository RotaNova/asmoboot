package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;


/**
 * ldap签证官
 *
 * @author WeiQiangMiao
 * @date 2021-04-23
 */
@Data
public class GetLdapVO implements Serializable {


    /**
     * LDAP地址
     */
    private String ldapAddress;
    /**
     * 绑定DN
     */
    private String ldapBindDn;

    /**
     * 密码
     */
    private String ldapPassword;

    /**
     * 用户OU
     */
    private String ldapUserOu;

    /**
     * 用户过滤器
     */
    private String ldapUserFilter;

    /**
     * LDAP属性映射
     */
    private String ldapAttributeMapping;

    /**
     * 启用LDAP认证
     */
    private Integer ldapEnableAuth;



}