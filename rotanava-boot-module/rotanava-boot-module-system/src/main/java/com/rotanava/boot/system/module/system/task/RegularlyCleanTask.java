package com.rotanava.boot.system.module.system.task;


import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.system.bo.SysOnlineUser;
import com.rotanava.boot.system.module.dao.SysOnlineUserMapper;
import com.rotanava.boot.system.module.dao.UserLoginInfoMapper;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.util.JwtUtil;
import com.rotanava.framework.util.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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



//    @Scheduled(cron = "0 */1 * * * ? ")
    @XxlJob(value = "regularlyCleanUpInactiveUsers")
    public ReturnT<String> regularlyCleanUpInactiveUsers(String data) {

        ManageSecurity manageSecurity = manageSecurityService.getManageSecurity();

        //获取过期token列表,并清除 在线token
        redisUtil.getExpired(Duration.ofMinutes(manageSecurity.getSingleLoginValidTime())).forEach(sysUserService::logout);
        return ReturnT.SUCCESS;

    }
}
