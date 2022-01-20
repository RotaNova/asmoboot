package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 性能签证官
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@Data
public class PerformanceVO  implements Serializable {


    /**
     * cpu使用率
     */
    private String cpuUsage;

    /**
     * 内存使用情况 已使用/总量
     */
    private String ramUsage;

    /**
     * 磁盘使用情况 已使用/总量
     */
    private String diskUsage;
}
