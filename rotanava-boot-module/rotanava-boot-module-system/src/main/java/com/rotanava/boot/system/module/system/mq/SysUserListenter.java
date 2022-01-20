package com.rotanava.boot.system.module.system.mq;

import cn.hutool.core.convert.Convert;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.common.constant.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 用户监听
 * @author: jintengzhou
 * @date: 2021-04-13 10:20
 */
@Slf4j
@Component
public class SysUserListenter {

    /**
     * 用户过期时间监听
     */
    public static final String EXPIRATION_TIME_QUEUE = "user_expiration_time_queue";

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 功能: 用户过期时间监听
     * 作者: zjt
     * 日期: 2021/3/23 16:50
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(EXPIRATION_TIME_QUEUE))
    public void expirationTimeQueueListenter(String message) {
        final Integer sysUserId = Convert.toInt(message);

        final SysUser sysUser = sysUserMapper.selectById(sysUserId);

        if (sysUser != null) {
            final Date userValidTime = sysUser.getUserValidTime();
            if (userValidTime.getTime() <= System.currentTimeMillis() && !sysUser.getUserStatus().equals(UserStatus.INACTIVATED.getStatus())) {
                sysUser.setUserStatus(UserStatus.EXPIRED.getStatus());
                sysUserMapper.updateById(sysUser);
            }
        }
    }

    
}