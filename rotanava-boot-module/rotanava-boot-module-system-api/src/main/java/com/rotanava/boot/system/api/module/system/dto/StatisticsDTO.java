package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 统计dto
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
@Data
public class StatisticsDTO implements Serializable {

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Long startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Long endTime;

    /**
     * 单位
     */
    private Integer unit;

}
