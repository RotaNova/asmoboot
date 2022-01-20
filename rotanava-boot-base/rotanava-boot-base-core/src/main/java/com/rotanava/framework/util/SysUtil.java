package com.rotanava.framework.util;

import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.model.LoginUser;
import org.apache.shiro.SecurityUtils;

/**
 * @description: 系统工具类
 * @author: jintengzhou
 * @date: 2021-03-17 17:03
 */
public class SysUtil {

    /**
     * 功能: 获取当前登陆人员信息
     * 作者: zjt
     * 日期: 2021/3/17 17:05
     * 版本: 1.0
     */
    public static LoginUser getCurrentReqLoginUser() {
        //获取登录用户信息
        try {
            return (LoginUser) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {
            return null;
        }
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