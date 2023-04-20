package com.rotanava.boot.system.util.ldap;


import com.rotanava.framework.util.StringUtil;
import lombok.extern.log4j.Log4j2;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
@Log4j2
public class LDAPAuthentication {
    private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final String BASEDN = "dc=rotanova,dc=top";
    private final String HOST = "192.168.0.166";
    private final String PORT = "389";
    private final String USER = String.format("cn=admin,%s", BASEDN);
    private final String PASSWORD = "ldap123";
    private final String URL = String.format("ldap://%s:%s/", HOST, PORT);
    private LdapContext ctx = null;
    private final Control[] connCtls = null;

    public static void main(String[] args) throws NamingException {
        LDAPAuthentication ldapAuthentication = new LDAPAuthentication();
        ldapAuthentication.LDAP_connect();

//        ldapAuthentication.authenricate("test2","ldap123");
//        System.out.println(ldapAuthentication.getUserDN("test2"));
//        System.out.println(ldapAuthentication.getUserDN("test1"));

//        NamingEnumeration<NameClassPair> users = ldapAuthentication.ctx.list("ou=test");
//        ldapAuthentication.getList("");
//        System.out.println();

    }



    private void LDAP_connect() {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, URL + "cn=admin,dc=rotanova,dc=top");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=rotanova,dc=top");
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        // 此处若不指定用户名和密码,则自动转换为匿名登录
        try {
            ctx = new InitialLdapContext(env, connCtls);
            System.out.println("登录成功");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("验证失败：" + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search(String key){
        LDAP_connect();
        Attributes matchAttrs = new BasicAttributes(true);
        matchAttrs.put(new BasicAttribute("username", key));

        try {
            NamingEnumeration<SearchResult> answer = ctx.search("", matchAttrs);
            while (answer.hasMore()) {
                SearchResult sr = answer.next();
                Attributes attributes = sr.getAttributes();
                System.out.println(String.valueOf(attributes.get("username").get(0)));
            }
        } catch (NamingException e) {
            try {
                throw new NamingException(e.toString());
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getList(String name){

        try {
            NamingEnumeration<NameClassPair> en = ctx.list(name);
            while (en != null && en.hasMoreElements()) {
                NameClassPair nameClassPair = en.nextElement();
                System.out.println(nameClassPair.getNameInNamespace());
                String key = nameClassPair.getName();
                if (!StringUtil.isNullOrEmpty(name)){
                    key = nameClassPair.getName()+","+name;
                }
                getList(key);

            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("搜索异常");
        }
    }

    private String getUserDN(String uid) {
        StringBuilder userDN = new StringBuilder();
        LDAP_connect();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                System.out.println("未找到该用户");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                SearchResult obj = en.nextElement();
                Attributes attributes = obj.getAttributes();
                if (obj != null) {
                    userDN.append(obj.getName());
                    userDN.append("," + BASEDN);
                } else {
                    System.out.println(obj);
                }
            }
        } catch (Exception e) {
            System.out.println("查找用户时产生异常。");
            e.printStackTrace();
        }

        return userDN.toString();
    }

    public boolean authenricate(String UID, String password) {
        boolean valide = false;
        String userDN = getUserDN(UID);

        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            System.out.println(userDN + " 验证通过");
            valide = true;
        } catch (AuthenticationException e) {
            System.out.println(userDN + " 验证失败");
            System.out.println(e.toString());
        } catch (NamingException e) {
            System.out.println(userDN + " 验证失败");
            valide = false;
        }

        return valide;
    }
}