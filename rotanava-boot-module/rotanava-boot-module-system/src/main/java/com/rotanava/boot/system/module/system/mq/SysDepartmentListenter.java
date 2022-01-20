package com.rotanava.boot.system.module.system.mq;

import cn.hutool.core.convert.Convert;
import com.rotanava.boot.system.api.module.constant.DeptStatus;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import com.rotanava.boot.system.module.dao.SysDepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 部门监听
 * @author: jintengzhou
 * @date: 2021-03-23 16:48
 */
@Slf4j
@Component
public class SysDepartmentListenter {

    /**
     * 部门过期时间监听
     */
    public static final String EXPIRATION_TIME_QUEUE = "expiration_time_queue";

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    /**
     * 功能: 部门过期时间监听
     * 作者: zjt
     * 日期: 2021/3/23 16:50
     * 版本: 1.0
     */
    @RabbitListener(queuesToDeclare = @Queue(EXPIRATION_TIME_QUEUE))
    public void expirationTimeQueueListenter(String message) {
        final Integer deptId = Convert.toInt(message);

        final SysDepartment sysDepartment = sysDepartmentMapper.selectById(deptId);
        if (sysDepartment != null) {
            final Date deptValidTime = sysDepartment.getDeptValidTime();
            if (deptValidTime.getTime() <= System.currentTimeMillis()) {
                sysDepartment.setDeptStatus(DeptStatus.EXPIRED.getStatus());
                sysDepartmentMapper.updateById(sysDepartment);
            }
        }
    }

}