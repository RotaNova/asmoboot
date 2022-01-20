package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访问量统计VO
 *
 * @author WeiQiangMiao
 * @date 2021-04-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TodayData extends VisitorVolumeAndContrastRateVO {


    /**
     * 登录频率
     */
    private Long loginFrequency;

    /**
     * 平均使用时间
     */
    private Long averageUsageTime;

    /**
     * 登录率
     */
    private Double loginRate;

    /**
     * 登录频率对比率
     */
    private Double loginFrequencyContrastRate;

    /**
     * 平均使用时间对比率
     */
    private Double averageUsageTimeContrastRate;

    /**
     * 登录率对比率
     */
    private Double loginRateContrastRate;


}
