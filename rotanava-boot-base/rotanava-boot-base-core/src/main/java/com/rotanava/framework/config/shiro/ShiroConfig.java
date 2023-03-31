package com.rotanava.framework.config.shiro;

import cn.hutool.core.convert.Convert;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.config.shiro.filters.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @version :  1.0
 * @description :shiro配置类
 * @Author : richenLi
 * @Date : 2020/9/1 13:51
 */

@Slf4j
@Configuration
public class ShiroConfig {
    
    @Autowired
    private RedisProperties redisProperties;
    
    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ConfigurableApplicationContext context;
    
    
    /**
     * Filter Chain定义说明
     * <p>
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/sys/cas/client/validateLogin", "anon"); //cas验证登录
        filterChainDefinitionMap.put("/sys/randomImage/**", "anon"); //登录验证码接口排除
        filterChainDefinitionMap.put("/sys/checkCaptcha", "anon"); //登录验证码接口排除
        filterChainDefinitionMap.put("/sys/login", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/sys/mLogin", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/sys/logout", "anon"); //登出接口排除
        filterChainDefinitionMap.put("/sys/thirdLogin/**", "anon"); //第三方登录
        filterChainDefinitionMap.put("/sys/getEncryptedString", "anon"); //获取加密串
        filterChainDefinitionMap.put("/sys/sms", "anon");//短信验证码
        filterChainDefinitionMap.put("/sys/phoneLogin", "anon");//手机登录
        filterChainDefinitionMap.put("/sys/user/checkOnlyUser", "anon");//校验用户是否存在
        filterChainDefinitionMap.put("/sys/user/register", "anon");//用户注册
        filterChainDefinitionMap.put("/sys/user/querySysUser", "anon");//根据手机号获取用户信息
        filterChainDefinitionMap.put("/sys/user/phoneVerification", "anon");//用户忘记密码验证手机号
        filterChainDefinitionMap.put("/sys/user/passwordChange", "anon");//用户更改密码
        filterChainDefinitionMap.put("/auth/2step-code", "anon");//登录验证码
        filterChainDefinitionMap.put("/sys/common/static/**", "anon");//图片预览 &下载文件不限制token
        filterChainDefinitionMap.put("/sys/common/pdf/**", "anon");//pdf预览
        filterChainDefinitionMap.put("/generic/**", "anon");//pdf预览需要文件
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.html", "anon");
        filterChainDefinitionMap.put("/**/*.svg", "anon");
        filterChainDefinitionMap.put("/**/*.pdf", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.ico", "anon");
        
        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀
        filterChainDefinitionMap.put("/**/*.ttf", "anon");
        filterChainDefinitionMap.put("/**/*.woff", "anon");
        filterChainDefinitionMap.put("/**/*.woff2", "anon");
        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀
        
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/demo", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        //用户管理
        filterChainDefinitionMap.put("/v1/manageUser/passWordLogin", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/onlineRecord", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/logout", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/sendLoginPhoneVerificationCode", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/phoneLogin", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getSecondVerification", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/sendSecondPhoneVerificationCode", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/sendSecondaryEmailVerificationCode", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/mobilePhoneIntegrityVerification", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/mobileEmailIntegrityVerification", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/secondPhoneLogin", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/secondEmailLogin", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/updateFirstPassword", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getAccessInfo", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getAccessToken", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/sendRetrievePwPhoneCaptcha", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/sendRetrievePwEmailCaptcha", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/checkRetrievePwPhoneCaptcha", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/checkRetrievePwEmailCaptcha", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/updatePhonePasswordByCode", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/updateEmailPasswordByCode", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getToken", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getVerify", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getBindInfo", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/dingDingAuth", "anon");
        filterChainDefinitionMap.put("/v1/manageUser/getDdScanLoginParam", "anon");
        
        filterChainDefinitionMap.put("/open/**", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/v1/accessDevice/getUrl", "anon");
        filterChainDefinitionMap.put("/v1/cloudVideo/getMp4", "anon");
        filterChainDefinitionMap.put("/v1/systemAuth/getAiAuthInfo", "anon");

        
        //filterChainDefinitionMap.put("/v1/ManagePermission/**", "anon");
//        filterChainDefinitionMap.put("/v1/sysDict/**", "anon");
        filterChainDefinitionMap.put("/v1/index/**", "anon");
//        filterChainDefinitionMap.put("/v1/sysLog/**", "anon");
//        filterChainDefinitionMap.put("/v1/common/**", "anon");
//        filterChainDefinitionMap.put("/v1/managePermission/**", "anon");
//        filterChainDefinitionMap.put("/v1/platformSetting/**", "anon");
//        filterChainDefinitionMap.put("/v1/sysSetting/**", "anon");
        filterChainDefinitionMap.put("/v1/logManage/**", "anon");
//        filterChainDefinitionMap.put("/v1/project/**", "anon");
//        filterChainDefinitionMap.put("/v1/projectMap/**", "anon");
        filterChainDefinitionMap.put("/auth/**", "anon");
        filterChainDefinitionMap.put("/openApi/**", "anon");
        //        "/v1/manageUser/mobilePhoneIntegrityVerification",
        //        "/v1/manageUser/mobileEmailIntegrityVerification",
        //        "/v1/manageUser/sendSecondPhoneVerificationCode",
        //        "/v1/manageUser/sendSecondaryEmailVerificationCode",
        //        "/v1/manageUser/secondPhoneLogin",
        //        "/v1/manageUser/secondEmailLogin"
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        //如果cloudServer为空 则说明是单体 需要加载跨域配置
        Object cloudServer = env.getProperty(CommonConstant.CLOUD_SERVER_KEY);
        filterMap.put("jwt", new JwtFilter(cloudServer == null,applicationName,context));
        shiroFilterFactoryBean.setFilters(filterMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");


//        filterChainDefinitionMap.put("/v1/managePermission/**", "anon");
        
        //性能监控  TODO 存在安全漏洞
//        filterChainDefinitionMap.put("/actuator/**", "anon");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 开启授权缓存管理
        myRealm.setAuthorizationCachingEnabled(true);
        // 设置授权缓存管理的名字
        myRealm.setAuthorizationCacheName(CommonConstant.AUTHORIZATION_CACHE);
        
        securityManager.setRealm(myRealm);
        
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
//        ThreadContext.bind(securityManager);
        return securityManager;
    }
    
    /**
     * 下面的代码是添加注解支持
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        /**
         * 解决重复代理问题 github#994
         * 添加前缀判断 不匹配 任何Advisor
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
        return defaultAdvisorAutoProxyCreator;
    }
    
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        log.info("===============(1)创建缓存管理器RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
//        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }
    
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public IRedisManager redisManager() {
        log.info("===============(2)创建RedisManager,连接Redis..");
        IRedisManager manager;
        
        final RedisProperties.Cluster cluster = redisProperties.getCluster();
        
        // redis 单机支持，在集群为空，或者集群无机器时候使用 add by jzyadmin@163.com
        if (cluster == null || cluster.getNodes().isEmpty()) {
            RedisManager redisManager = new RedisManager();
            redisManager.setHost(redisProperties.getHost());
            redisManager.setPort(redisProperties.getPort());
            redisManager.setTimeout(0);
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                redisManager.setPassword(redisProperties.getPassword());
            }
            redisManager.setDatabase(redisProperties.getDatabase());
            manager = redisManager;
        } else {
            // redis 集群支持，优先使用集群配置	add by jzyadmin@163.com
            RedisClusterManager redisManager = new RedisClusterManager();
            Set<HostAndPort> portSet = new HashSet<>();
            cluster.getNodes().forEach(node -> portSet.add(new HostAndPort(node.split(":")[0], Convert.toInt(node.split(":")[1]))));
            JedisCluster jedisCluster = new JedisCluster(portSet);
            redisManager.setJedisCluster(jedisCluster);
            redisManager.setDatabase(redisProperties.getDatabase());
            manager = redisManager;
        }
        return manager;
    }
    
}
