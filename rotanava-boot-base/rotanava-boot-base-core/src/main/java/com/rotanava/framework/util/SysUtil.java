package com.rotanava.framework.util;

import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.config.dubbo.filter.UserSourceUtil;
import com.rotanava.framework.config.shiro.CommonApiLazyService;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.model.LoginUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 系统工具类
 * @author: jintengzhou
 * @date: 2021-03-17 17:03
 */
@Component
public class SysUtil implements ApplicationContextAware {
    @Autowired
    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 功能: 获取当前登陆人员信息
     * 作者: zjt
     * 日期: 2021/3/17 17:05
     * 版本: 1.0
     */
    public static LoginUser getCurrentReqLoginUser() {
        //获取登录用户信息
        LoginUser loginUser = null;
        try {

            loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        } catch (Exception e) {
            loginUser = null;
        }
        try {
            if (loginUser == null) {
                if (!StringUtil.isNullOrEmpty(UserSourceUtil.getUserId())) {
                    CommonApiLazyService bean = context.getBean(CommonApiLazyService.class);
                    loginUser = bean.getCommonApi().getUserById(Integer.parseInt(UserSourceUtil.getUserId()));
                }
            }
        } catch (Exception e) {
            loginUser = null;
        }

        return loginUser;
    }

    /**
     * 功能: 获取当前登陆人员的id
     * 作者: zjt
     * 日期: 2021/3/17 17:07
     * 版本: 1.0
     */
    public static Integer getCurrentReqUserId() {
        final LoginUser currentReqLoginUser = getCurrentReqLoginUser();
        if (currentReqLoginUser != null) {
            return currentReqLoginUser.getId();
        }
        throw new CommonException(SysErrorCode.SYS_ERROR_09);
    }

}