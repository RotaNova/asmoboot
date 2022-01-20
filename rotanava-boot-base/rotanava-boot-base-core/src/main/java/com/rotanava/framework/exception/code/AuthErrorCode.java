package com.rotanava.framework.exception.code;


import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:鉴权错误
 * 类别码 05
 * @author: richenLi
 * @create: 2020-07-06 09:46
 **/
public interface AuthErrorCode {

    ErrorCode AUTH_ERROR_00=new ErrorCode(100500,"token无效");
    ErrorCode AUTH_ERROR_01=new ErrorCode(100501,"没有该模块权限");
    ErrorCode AUTH_ERROR_02=new ErrorCode(100502,"授权过期");
    ErrorCode AUTH_ERROR_03=new ErrorCode(100500,"身份认证失效，请重新登录!");

    ErrorCode AUTH_ERROR_04=new ErrorCode(100504,"您的权限级别对此操作或对请求的资源的访问被拒绝");
    ErrorCode AUTH_ERROR_05=new ErrorCode(100500,"身份认证已过期，请重新登录!");
    ErrorCode AUTH_ERROR_06=new ErrorCode(100505,"用户不存在");
    ErrorCode AUTH_ERROR_07=new ErrorCode(100506,"app页面资源关联已存在");
    ErrorCode AUTH_ERROR_08=new ErrorCode(100507,"页面资源不存在");
    ErrorCode AUTH_ERROR_09=new ErrorCode(100507,"已选择了公共区");
    ErrorCode AUTH_ERROR_10=new ErrorCode(100500,"账号锁定10分钟");
    ErrorCode AUTH_ERROR_11=new ErrorCode(100508,"账号或密码错误");
    ErrorCode AUTH_ERROR_12=new ErrorCode(100509,"此账号已过期");
    ErrorCode AUTH_ERROR_13=new ErrorCode(100510,"接口资源不能关联多个页面资源");
    ErrorCode AUTH_ERROR_14=new ErrorCode(100511,"接口资源不存在");
    ErrorCode AUTH_ERROR_15=new ErrorCode(100512,"页面资源不存在");
    ErrorCode AUTH_ERROR_16=new ErrorCode(100513,"接口资源已存在");
    ErrorCode AUTH_ERROR_17=new ErrorCode(100514,"页面资源已存在");
    ErrorCode AUTH_ERROR_18=new ErrorCode(100515,"密码错误");
    ErrorCode AUTH_ERROR_19=new ErrorCode(100516,"密码强度没有达到要求");

    ErrorCode AUTH_ERROR_20=new ErrorCode(100516,"ip被禁止登录");
    ErrorCode AUTH_ERROR_21=new ErrorCode(100516,"用户已被登录锁定");
    ErrorCode AUTH_ERROR_22=new ErrorCode(100517,"该账号当前已登录，如非本人操作，请联系管理员修改密码或在成功登录后修改密码。");
    ErrorCode AUTH_ERROR_23=new ErrorCode(100500,"您的账号在其他设备登录，如果这不是您的操作，请联系管理员，及时修改您的登录密码。");
    ErrorCode AUTH_ERROR_24=new ErrorCode(100500,"管理员更新平台设置,请重新登录!");

    ErrorCode AUTH_ERROR_25=new ErrorCode(1005018,"没有此权限！");
    ErrorCode AUTH_ERROR_26=new ErrorCode(1005018,"没有此分享权限！");


}
