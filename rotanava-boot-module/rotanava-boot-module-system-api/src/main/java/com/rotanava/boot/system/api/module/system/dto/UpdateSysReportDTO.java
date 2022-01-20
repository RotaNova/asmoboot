package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 编辑系统报告dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateSysReportDTO extends AddSysReportDTO implements Serializable {

    /**
     * 系统报告Id
     */
    @NotNull(message = "系统报告Id不能为空")
    private Integer sysReportId;

}
