package com.rotanava.boot.system.module.service.impl;

import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysBehaviorLog;
import com.rotanava.framework.module.dao.BaseLogMapper;
import com.rotanava.framework.module.base.service.BaseLogService;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DubboService
public class BaseLogServiceImpl implements BaseLogService {

    @Resource
    private BaseLogMapper baseLogMapper;

    @Override
    public void addLog(SysBehaviorLog logDTO) {
        baseLogMapper.insert(logDTO);
    }

    @Override
    public void addLog(String logContent, Integer logType, Integer operatetype, LoginUser user) {
//        LogDTO sysLog = new LogDTO();
//        sysLog.setId(String.valueOf(IdWorker.getId()));
//        //注解上的描述,操作日志内容
//        sysLog.setLogContent(logContent);
//        sysLog.setLogType(logType);
//        sysLog.setOperateType(operatetype);
//        try {
//            //获取request
//            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
//            //设置IP地址
//            sysLog.setIp(IPUtils.getIpAddr(request));
//        } catch (Exception e) {
//            sysLog.setIp("127.0.0.1");
//        }
//        //获取登录用户信息
//        if(user==null){
//            try {
//                user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//        }
//        if(user!=null){
//            sysLog.set(user.getUsername());
//        }
//        sysLog.setCreateTime(new Date());
        //保存系统日志
//        baseLogMapper.insert(sysLog);
    }

    @Override
    public void addLog(String logContent, Integer logType, Integer operateType) {
        addLog(logContent, logType, operateType, null);
    }



}
