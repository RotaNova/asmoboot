package com.rotanava.boot.system.api.module.constant;


/**
 * 报告状态
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
public enum ReportStatus {

    //报告状态:0-待受理;1-待确认;2-已处理;3-驳回;4-撤销

    /**
     * 0-待受理
     */
    PENDING(0),

    /**
     * 1-待确认
     */
    TO_BE_CONFIRMED(1),

    /**
     * 2-已处理
     */
    PROCESSED(2),

    /**
     * 3-驳回
     */
    OVERRULE(3),

    /**
     * 4-撤销
     */
    REVOKE(4);

    private Integer status;

    ReportStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static ReportStatus findByStatus(Integer status) {

        ReportStatus[] enums = ReportStatus.values();
        for (ReportStatus enu : enums) {
            if (enu.getStatus().equals(status)) {
                return enu;
            }
        }
        return null;
    }

}