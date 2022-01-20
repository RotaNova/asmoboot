package com.rotanava.boot.system.module.system.mq;

import cn.hutool.core.convert.Convert;
import com.rotanava.boot.system.api.SysPlatformDeployService;
import com.rotanava.boot.system.api.module.system.bo.SysTimingTask;
import com.rotanava.boot.system.module.dao.SysTimingTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 系统定时listenter
 *
 * @author WeiQiangMiao
 * @date 2021-04-20
 */
@Slf4j
@Component
public class SysTimingListenter {

    /**
     * 定时重启过期时间监听
     */
    public static final String SYS_TIMING_QUEUE = "sys_timing_queue";

    @Autowired
    private SysTimingTaskMapper sysTimingTaskMapper;
    @Autowired
    private SysPlatformDeployService sysPlatformDeployService;


    /**
     * 系统定时队列
     *
     * @param message 消息
     * @author WeiQiangMiao
     * @date 2021-04-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @RabbitListener(queuesToDeclare = @Queue(SYS_TIMING_QUEUE))
    public void sysTimingQueueListenter(String message) {
        final Integer sysTimingTaskId = Convert.toInt(message);

        final SysTimingTask sysTimingTask = sysTimingTaskMapper.selectById(sysTimingTaskId);
        if (sysTimingTask != null) {
            if (sysTimingTask.getTiming().getTime() <= System.currentTimeMillis()) {
                sysPlatformDeployService.systemReboot();
            }
        }

        sysTimingTaskMapper.deleteByTimingLessThanEqual(new Date());
    }

}