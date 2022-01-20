package com.rotanava.framework.config.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;

import java.io.Serializable;


/**
 * url资源验证
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
@Data
@AllArgsConstructor
@Log4j2
public class UrlPermission implements Permission, Serializable {
    /*在 Realm 的授权方法中,由数据库查询出来的权限字符串*/
    /**
     *
     * 路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    @Override
    public boolean implies(Permission permission) {
        if(!(permission instanceof UrlPermission)){
            return false;
        }

        UrlPermission urlPermission = (UrlPermission)permission;
        PatternMatcher patternMatcher = new AntPathMatcher();

        boolean urlBoolean = patternMatcher.matches(this.url, urlPermission.url);
        boolean requestMethodBoolean = patternMatcher.matches(this.requestMethod,urlPermission.requestMethod);

        return urlBoolean && requestMethodBoolean;
    }
}
