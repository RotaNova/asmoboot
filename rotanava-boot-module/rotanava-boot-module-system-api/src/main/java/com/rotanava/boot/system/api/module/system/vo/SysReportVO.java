package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 报告签证官
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@Data
public class SysReportVO implements Serializable {

    /**
     * 报告ID
     */
    private Integer sysReportId;

    /**
     * 报告编码
     */
    private String reportCode;

    /**
     * 报告状态:0-待受理;1-待确认;2-已处理;3-驳回;4-撤销
     */
    @Dict(dicCode = "report_stat")
    private Integer reportStat;

    /**
     * 报告类型:0-问题反馈;1-改进意见
     */
    @Dict(dicCode = "report_type")
    private Integer reportType;

    /**
     * 报告名称
     */
    private String reportTile;

    /**
     * 报告内容
     */
    private String reportBody;

    /**
     * 报告人联系方式
     */
    private String reportPhone;

    /**
     * 报告地址
     */
    private String reportAddress;

    /**
     * 报告人
     */
    private String reporter;

    /**
     * 报告人账号
     */
    private String reporterUserAccountName;

    /**
     * 报告日期
     */
    private Long reportTime;

    /**
     * 报告信息更新人
     */
    private String updateUserName;

    /**
     * 报告更新人账号
     */
    private String updateUserAccountName;

    /**
     * 报告信息更新时间
     */
    private Long updateTime;

    /**
     * 回复内容
     */
    private String replyBody;


}