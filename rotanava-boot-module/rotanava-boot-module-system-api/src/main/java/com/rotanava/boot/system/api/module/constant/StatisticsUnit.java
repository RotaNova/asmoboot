package com.rotanava.boot.system.api.module.constant;

/**
 * 统计单位
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
public enum  StatisticsUnit {

    //时间单位

    DAY(1),
    WEEK(2),
    MONTH(3),
    QUARTERLY(4),
    YEAR(5)
    ;

    private Integer unit;

    StatisticsUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public static StatisticsUnit findByUnit(Integer unit) {

        StatisticsUnit[] enums = StatisticsUnit.values();
        for (StatisticsUnit statisticsUnit : enums) {
            if (statisticsUnit.getUnit().equals(unit)) {
                return statisticsUnit;
            }
        }
        return null;
    }
}
