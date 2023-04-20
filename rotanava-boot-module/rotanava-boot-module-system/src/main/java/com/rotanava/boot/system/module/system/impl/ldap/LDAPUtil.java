package com.rotanava.boot.system.module.system.impl.ldap;

import com.alibaba.fastjson.JSONObject;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.SysErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-05-05 14:05
 **/
@Component
@Log4j2
public class LDAPUtil {

    private LdapTemplate ldapTemplate;


    public LdapContextSource contextSource(String url, String base, String dn, String pwd) {
        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap();
        contextSource.setUrl(url);
        contextSource.setBase(base);
        contextSource.setUserDn(dn);
        contextSource.setPassword(pwd);
        //  解决乱码
        config.put("java.naming.ldap.attributes.binary", "objectGUID");

        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(config);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    public LdapTemplate resetLdapTemplate(String url, String base, String dn, String pwd) {
        this.ldapTemplate = new LdapTemplate(contextSource(url, base, dn, pwd));
        return this.ldapTemplate;
    }


    public LdapTemplate getLdapTemplate() {
        if (this.ldapTemplate == null) {
            throw new CommonException(SysErrorCode.SYS_ERROR_13);
        }
        return this.ldapTemplate;
    }

    public boolean testConnection(String url, String base, String dn, String pwd) {
        try {
           return LDAP_connect(url,base,dn,pwd);
        } catch (Exception e) {
            log.error("测试连接异常", e);
        }
        return false;
    }


    public boolean authenticate(String userName, String pwd, String base) {
        LdapTemplate ldapTemplate = getLdapTemplate();
        EqualsFilter filter = new EqualsFilter("cn", userName);
        try {
            boolean authenticate = ldapTemplate.authenticate(base, filter.toString(), pwd);
            return authenticate;
        } catch (Exception e) {
            log.error("ldap鉴权失败", e);
            throw new CommonException(SysErrorCode.SYS_ERROR_14);
        }
    }


    private boolean LDAP_connect(String url, String base, String dn, String pwd) {
        LdapContext ctx = null;
        final Control[] connCtls = null;
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url +"/"+ dn);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, pwd);
        // 此处若不指定用户名和密码,则自动转换为匿名登录
        try {
            ctx = new InitialLdapContext(env, connCtls);
            return true;
        } catch (javax.naming.AuthenticationException e) {
            log.error("验证失败：" + e.toString());
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }


    public List<JSONObject> getUserListByBase(String base){
        AttributesMapper<com.alibaba.fastjson.JSONObject> attributesMapper = attributes -> {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            NamingEnumeration<String> iDs = attributes.getIDs();
            while (iDs.hasMore()){
                String next = iDs.next();
                Attribute attribute = attributes.get(next);
                jsonObject.put(next,attribute.get());
            }
            return jsonObject;
        };

        List<JSONObject> search = getLdapTemplate().search(query().base(base).where("objectClass").is("person"),attributesMapper);
        return search;

    }


}
