package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本信息签证官
 *
 * @author WeiQiangMiao
 * @date 2021-03-11
 */
@Data
public class BasicInfoVO  implements Serializable {

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统编号
     */
    private String systemNumber;

    /**
     * 系统型号
     */
    private String systemModel;

    /**
     * 系统服务版本
     */
    private String systemServiceVersion;

    /**
     * 系统服务商
     */
    private String systemServiceProvider;

    /**
     * 生产日期
     */
    private String productionDate;

    /**
     * 产地
     */
    private String origin;

    /**
     * WEB版本
     */
    private String webVersion;

    /**
     * cpu名称
     */
    private String cpuName;

    /**
     * 物理内存总量
     */
    private Long ramTotal;

    /**
     * 磁盘大小
     */
    private Long diskSize;



}
