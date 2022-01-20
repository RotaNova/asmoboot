package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 数据驾驶舱签证官
 *
 * @author WeiQiangMiao
 * @date 2021-04-02
 */
@Data
public class DataCockpitVO {


    /**
     * 今日统计VO
     */
    private TodayData todayData;

    /**
     * 本周访客量统计对比
     */
    private VisitorVolumeAndContrastRateVO weekVisitorStatistics;

    /**
     * 本月访客量统计对比
     */
    private VisitorVolumeAndContrastRateVO monthVisitorStatistics;

    /**
     * 7天访客量统计对比
     */
    private List<VisitorVolumeAndContrastRateVO> sevenDaysVisitorStatistics;


    /**
     * 本周访问统计信息
     */
    private List<AccessVO> weekAccessStatistics;

    /**
     * 本月访问统计信息
     */
    private List<AccessVO> monthAccessStatistics;

    /**
     * 本周访问来源统计
     */
    private List<AccessVO> weekAccessSourceStatistics;

    /**
     * 本月访问来源统计
     */
    private List<AccessVO> monthAccessSourceStatistics;

    /**
     * 本周访问设备统计
     */
    private List<AccessVO> weekAccessDeviceStatistics;

    /**
     * 本月访问设备统计
     */
    private List<AccessVO> monthAccessDeviceStatistics;

    /**
     * 运营时长
     */
    private Long operatingTime;

    /**
     * 今天页面浏览量
     */
    private Long todayPageViews;

    /**
     * 磁盘空间 已使用/总量
     */
    private String diskUsage;

}
