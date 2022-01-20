package com.rotanava.boot.system.api.module.constant;

/**
 * 报告类型
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
public enum ReportType {

    //报告类型:0-问题反馈;1-改进意见

    /**
     * 0-问题反馈
     */
    FEEDBACK(0),

    /**
     * 1-改进意见
     */
    SUGGESTIONS_FOR_IMPROVEMENT(1);

    private Integer type;

    ReportType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static ReportType findByType(Integer type) {

        ReportType[] enums = ReportType.values();
        for (ReportType reportType : enums) {
            if (reportType.getType().equals(type)) {
                return reportType;
            }
        }
        return null;
    }


}
