package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用时间签证官
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageTimeVO {

    /**
     * 平均使用时间
     */
    private Long averageUsageTime;

    /**
     * 最小使用时间
     */
    private Long minUsageTime;

    /**
     * 最大使用时间
     */
    private Long maxUsageTime;

    /**
     * 时间
     */
    private String time;


}
