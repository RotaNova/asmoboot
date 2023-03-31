package com.rotanava.framework.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.CommonConstant;

import com.rotanava.framework.common.constant.RequestMethod;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysBehaviorLog;
import com.rotanava.framework.module.base.service.BaseLogService;
import com.rotanava.framework.util.IPUtils;
import com.rotanava.framework.util.SpringContextUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description: 自动记录日志
 * @author: richenLi
 * @create: 2021-03-01 17:47
 **/
@Aspect
@Component
public class AutoLogAspect {

    @DubboReference
    private BaseLogService baseLogService;

    @Pointcut("@annotation(com.rotanava.framework.common.aspect.annotation.AutoLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time, result);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object obj) {
        if (baseLogService==null){
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysBehaviorLog dto = new SysBehaviorLog();
        AutoLog syslog = method.getAnnotation(AutoLog.class);
        if(syslog != null){
            String content = syslog.value();

            //注解上的描述,操作日志内容
            dto.setOperateType(syslog.operateType().getType());
            dto.setSyslogType(syslog.logType());
            //默认为基本信息
            dto.setSyslogBehaviorType(0);
            dto.setSyslogContent(content);
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        dto.setOperateMethod(className + "." + methodName + "()");


        //设置操作类型
        if (dto.getSyslogType() == CommonConstant.LOG_TYPE_2) {
            dto.setOperateType(syslog.operateType().getType());
        }

        //获取request
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        //请求的参数
        dto.setOperateParam(getReqestParams(request,joinPoint));
        //设置IP地址
        dto.setOperateIp(IPUtils.getIpAddr(request));
        //设置请求路径
        dto.setOperateUrl(request.getRequestURI());
        //设置请求类型
        dto.setOperateRequestType(RequestMethod.getRequestType(request.getMethod()).getRequestMethod());
        //获取登录用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null){
            //dto.setOperateBy(sysUser.getUserAccoutName());
            dto.setOperateBy(sysUser.getUserAccountName());

        }
        //耗时
        dto.setOperateCostTime(time+"ms");
        dto.setOperateCreateTime(new Date());
        dto.setSyslogTime(new Date());
        //保存系统日志
        baseLogService.addLog(dto);
    }


//    /**
//     * 获取操作类型
//     */
//    private int getOperateType(String methodName,int operateType) {
//        if (operateType > 0) {
//            return operateType;
//        }
//        if (methodName.startsWith("list")) {
//            return CommonConstant.OPERATE_TYPE_1;
//        }
//        if (methodName.startsWith("add")) {
//            return CommonConstant.OPERATE_TYPE_2;
//        }
//        if (methodName.startsWith("edit")) {
//            return CommonConstant.OPERATE_TYPE_3;
//        }
//        if (methodName.startsWith("delete")) {
//            return CommonConstant.OPERATE_TYPE_4;
//        }
//        if (methodName.startsWith("import")) {
//            return CommonConstant.OPERATE_TYPE_5;
//        }
//        if (methodName.startsWith("export")) {
//            return CommonConstant.OPERATE_TYPE_6;
//        }
//        return CommonConstant.OPERATE_TYPE_1;
//    }

    /**
     * @Description: 获取请求参数
     * @author: scott
     * @date: 2020/4/16 0:10
     * @param request:  request
     * @param joinPoint:  joinPoint
     * @Return: java.lang.String
     */
    private String getReqestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            // java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //  https://my.oschina.net/mengzhang6/blog/2395893
            Object[] arguments  = new Object[paramsArray.length];
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = paramsArray[i];
            }
            //update-begin-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
            PropertyFilter profilter = new PropertyFilter() {
                @Override
                public boolean apply(Object o, String name, Object value) {
                    if(value!=null && value.toString().length()>1000){
                        return false;
                    }
                    return true;
                }
            };
            params = JSONObject.toJSONString(arguments, profilter);
            //update-end-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        return params;
    }

//    /**
//     * online日志内容拼接
//     * @param obj
//     * @param content
//     * @return
//     */
//    private String getOnlineLogContent(Object obj, String content){
//        if (Result.class.isInstance(obj)){
//            Result res = (Result)obj;
//            String msg = res.getMessage();
//            String tableName = res.getOnlTable();
//            if(oConvertUtils.isNotEmpty(tableName)){
//                content+=",表名:"+tableName;
//            }
//            if(res.isSuccess()){
//                content+= ","+(oConvertUtils.isEmpty(msg)?"操作成功":msg);
//            }else{
//                content+= ","+(oConvertUtils.isEmpty(msg)?"操作失败":msg);
//            }
//        }
//        return content;
//    }

}
