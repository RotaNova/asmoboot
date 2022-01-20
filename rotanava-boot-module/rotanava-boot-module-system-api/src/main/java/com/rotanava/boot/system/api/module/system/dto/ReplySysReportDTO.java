package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 回复系统报告dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@Data
public class ReplySysReportDTO implements Serializable {

    /**
     * 系统报告Id
     */
    @NotNull(message = "系统报告Id不能为空")
    private Integer sysReportId;

    /**
     * 回复内容
     */
    @NotBlank(message = "回复内容不能为空")
    private String replyBody;
}
