package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加系统报告dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@Data
public class AddSysReportDTO implements Serializable {

    /**
     * 报告类型:0-问题反馈;1-改进意见
     */
    @NotNull(message = "报告类型不能为空")
    private Integer reportType;

    /**
     * 报告标题
     */
    @NotBlank(message = "报告标题不能为空")
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

}
