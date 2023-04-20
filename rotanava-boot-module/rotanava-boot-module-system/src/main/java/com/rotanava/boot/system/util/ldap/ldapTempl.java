package com.rotanava.boot.system.util.ldap;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.PresentFilter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-05-05 09:09
 **/
public class ldapTempl {


    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap();
        contextSource.setUrl("ldap://192.168.0.166:389");
        contextSource.setBase("");
        contextSource.setUserDn("cn=admin,dc=rotanova,dc=top");
        contextSource.setPassword("ldap123");
        //  解决乱码
        config.put("java.naming.ldap.attributes.binary", "objectGUID");

        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(config);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    public LdapTemplate ldapTemplate() {
        LdapTemplate  ldapTemplate = new LdapTemplate(contextSource());
        return ldapTemplate;
    }

    public static void main(String[] args) throws Exception {
        ldapTempl zz = new ldapTempl();
        LdapTemplate ldapTemplate = zz.ldapTemplate();
        ldapTemplate.afterPropertiesSet();

        EqualsFilter filter = new EqualsFilter("cn","test2");
//        ldapTemplate.setIgnorePartialResultException(true);
        boolean ldap123 = ldapTemplate.authenticate("dc=rotanova,dc=top", filter.toString(), "ldap123");

//        List<String> all = ldapTemplate.list("ou=test");

        List<String> all1 = ldapTemplate.list("ou=test,dc=rotanova,dc=top");

        AttributesMapper<JSONObject> attributesMapper = new AttributesMapper<JSONObject>() {
            @Override
            public JSONObject mapFromAttributes(Attributes attributes) throws NamingException {
                JSONObject jsonObject = new JSONObject();
                NamingEnumeration<String> iDs = attributes.getIDs();
                while (iDs.hasMore()){
                    String next = iDs.next();
                    Attribute attribute = attributes.get(next);
                    jsonObject.put(next,attribute.get());
                }
                return jsonObject;
            }
        };
        PresentFilter filter1 = new PresentFilter("uid");
        List<JSONObject> search = ldapTemplate.search(query().base("dc=rotanova,dc=top").filter(filter1),attributesMapper);

       // List<String> search = ldapTemplate.search(query().where("objectClass").is("person"), (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
        System.out.println(search);

    }

}
