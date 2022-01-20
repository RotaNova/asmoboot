package com.rotanava.boot.system.api.module.system.dto.system;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-08 14:13
 **/
@Data
public class LogBackupDTO {

    /**
     * 定时备份 1开启 0关闭
     */
    private Integer logScheduledBackupOption;

    /**
     * 日志备份频率
     */
    private Integer logBackupFrequency;

    /**
     * 日志备份保存天数
     */
    private Integer logBackupSaveDays;

    /**
     * 日志信息保存天数
     */
    private Integer logInfoSaveDays;

}
