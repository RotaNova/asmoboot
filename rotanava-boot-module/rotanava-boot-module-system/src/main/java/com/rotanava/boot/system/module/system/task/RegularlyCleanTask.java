package com.rotanava.boot.system.module.system.task;


import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.module.dao.SysOnlineUserMapper;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.util.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;


/**
 * 定期清洁任务
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@Slf4j
@Component
public class RegularlyCleanTask {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ManageSecurityService manageSecurityService;

    @Autowired
    private SysOnlineUserMapper sysOnlineUserMapper;


    //    @Scheduled(cron = "0 */1 * * * ? ")
    @XxlJob(value = "regularlyCleanUpInactiveUsers")
    public ReturnT<String> regularlyCleanUpInactiveUsers(String data) {

        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        long min = LocalDateTime.now().minusMinutes(manageSecurity.getSingleLoginValidTime()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long max = LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        //获取过期token列表,并清除 在线token
        Set<String> onlineUserTokens = redisUtil.getExpired(min, max);
        List<String> tokens = sysOnlineUserMapper.findSysUserToken();
        tokens.removeAll(onlineUserTokens);

        tokens.forEach(sysUserService::logout);
        return ReturnT.SUCCESS;

    }
}
