package com.rotanava.boot.system.api.module.system.dto.system;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-01 14:27
 **/
@Data
public class SystemBackupDTO {

    /**
     * 定时备份 1开启 2关闭
     */
    private Integer scheduledBackUpOption;

    /**
     * 备份频率
     */
    private Integer backupFrequency;

    /**
     * 备份保存天数
     */
    private Integer backupSaveDays;
}
