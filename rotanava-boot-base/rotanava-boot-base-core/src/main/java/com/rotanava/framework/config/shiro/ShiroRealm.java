package com.rotanava.framework.config.shiro;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.json.JSONUtil;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.enums.SingleSignOn;
import com.rotanava.framework.common.constant.enums.UserStatus;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.util.IPUtils;
import com.rotanava.framework.util.JwtUtil;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.framework.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @version :  1.0
 * @description : shiro鉴权
 * @Author : richenLi
 * @Date : 2020/9/1 13:51
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm implements SmartInitializingSingleton {
    
    private CommonApi commonApi;
    
    @Lazy
    @Autowired
    private CommonApiLazyService commonApiLazyService;
    
    @Lazy
    @Resource
    private RedisUtil redisUtil;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public void afterSingletonsInstantiated() {
        commonApi = commonApiLazyService.getCommonApi();
    }
    
    
    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    
    /**
     * 权限信息认证(包括角色以及权限)是用户访问controller的时候才进行验证(redis存储的此处权限信息)
     * 触发检测用户权限时才会调用此方法，例如checkRole,checkPermission
     *
     * @param principals 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============Shiro权限认证开始============ [ roles、permissions]==========");
        String userAccountName = null;
        if (principals != null) {
            LoginUser sysUser = (LoginUser) principals.getPrimaryPrincipal();
            userAccountName = sysUser.getUserAccountName();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = commonApi.queryUserRoles(userAccountName);
        info.setRoles(roleSet);
        
        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<Permission> permissionSet = commonApi.queryUserAuths(userAccountName);
        //设置资源权限
        info.addObjectPermissions(permissionSet);
        
        log.info("===============Shiro权限认证成功==============");
        return info;
    }
    
    /**
     * 用户信息认证是在用户进行登录的时候进行验证(不存redis)
     * 也就是说验证用户输入的账号和密码是否正确，错误抛出异常
     *
     * @param auth 用户登录的账号密码信息
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.info("===============Shiro身份认证开始============doGetAuthenticationInfo==========");
        String token = (String) auth.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败——————————IP地址:  " + IPUtils.getIpAddr(SpringContextUtils.getHttpServletRequest()));
            throw new AuthenticationException("Token为空!");
        }
        // 校验token有效性
        LoginUser loginUser = this.checkUserTokenIsEffect(token);
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }
    
    /**
     * 校验token的有效性
     *
     * @param token 令牌
     * @return {@link LoginUser }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public LoginUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 解密获得userAccountName，用于和数据库进行对比
        String userAccountName = JwtUtil.getUserAccountName(token);
        if (userAccountName == null) {
            throw new UnsupportedTokenException("认证失效请重新登录");
        }
        
        // 查询用户信息
        log.info("———校验token是否有效————checkUserTokenIsEffect——————— " + token);
        LoginUser loginUser = commonApi.getUserByAccountName(userAccountName);
        if (loginUser == null) {
            throw new UnknownAccountException("用户不存在!");
        }
        // 判断用户状态
        if (!UserStatus.NORMAL.getStatus().equals(loginUser.getUserStatus())) {
            throw new LockedAccountException(String.format("此账号%s", UserStatus.getDescByStatus(loginUser.getUserStatus())));
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        jwtTokenRefresh(token, userAccountName, loginUser.getId(), loginUser.getUserPassword());
        
        return loginUser;
    }
    
    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     * 用户过期时间 = Jwt有效时间 * 2。
     *
     * @param token           令牌
     * @param userAccountName 用户名
     * @param password        密码
     * @return boolean
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public void jwtTokenRefresh(String token, String userAccountName, Integer userId, String password) {
        
        String prefixManageSecurity = Convert.toStr(redisUtil.get(CommonConstant.PREFIX_MANAGE_SECURITY));
        ManageSecurity manageSecurity = JSONUtil.toBean(prefixManageSecurity, ManageSecurity.class);
        
        SingleSignOn singleSignOn = SingleSignOn.findByOn(manageSecurity.getSingleSignOn());
        String prefixOnlineUserToken = String.format("%s%s", singleSignOn.getCache(), SingleSignOn.SHUT_DOWN.equals(singleSignOn) ? token : userAccountName);
        if (redisUtil.hasKey(prefixOnlineUserToken)) {
            // 校验token有效性
            if (JwtUtil.verify(token, userAccountName, userId, password)) {
                // 校验token准确
                if (token.equals(redisUtil.get(prefixOnlineUserToken))) {
                    //刷新 在线token
                    redisUtil.online(token);
                    // 设置超时时间
                    redisUtil.expire(prefixOnlineUserToken, manageSecurity.getSingleLoginValidTime(), TimeUnit.MINUTES);
                    log.debug("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— " + token);
                } else {
                    log.info("您的账号在其他设备登录!logout");
                    commonApi.logout(token);
                    throw new ConcurrentAccessException(AuthErrorCode.AUTH_ERROR_23.getMsg());
                }
            } else {
                log.info("身份认证已过期!logout");
                commonApi.logout(token);
                throw new ExpiredCredentialsException(AuthErrorCode.AUTH_ERROR_05.getMsg());
            }
        } else {
            for (SingleSignOn value : EnumUtil.getEnumMap(SingleSignOn.class).values()) {
                if (!value.equals(singleSignOn)) {
                    Object userToken = redisUtil.get(String.format("%s%s", value.getCache(), SingleSignOn.SHUT_DOWN.equals(value) ? token : userAccountName));
                    if (token.equals(userToken)) {
                        log.info("管理员更新平台设置logout");
                        commonApi.logout(token);
                        throw new ExpiredCredentialsException(AuthErrorCode.AUTH_ERROR_24.getMsg());
                    }
                }
            }
            log.info("身份认证失败logout");
            commonApi.logout(token);
            throw new IncorrectCredentialsException(AuthErrorCode.AUTH_ERROR_03.getMsg());
        }
        
    }
    
    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
    
    /**
     * 是允许的
     *
     * @param principals 校长
     * @param permission 许可
     * @return boolean
     * @author WeiQiangMiao
     * @date 2021-03-17
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser.getIsAdmin()) {
            return true;
        }
        return super.isPermitted(principals, permission);
    }
    
}
