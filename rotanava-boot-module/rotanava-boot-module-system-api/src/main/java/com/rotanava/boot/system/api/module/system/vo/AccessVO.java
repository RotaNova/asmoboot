package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问签证官
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessVO {

    /**
     * 排名
     */
    private Long rank;

    /**
     * 名称
     */
    private String name;

    /**
     * 次数
     */
    private Long frequency;

}
