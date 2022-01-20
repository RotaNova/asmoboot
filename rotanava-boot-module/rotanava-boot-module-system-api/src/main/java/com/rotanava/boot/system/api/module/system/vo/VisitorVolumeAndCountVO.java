package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游客数量和签证官
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorVolumeAndCountVO {

    /**
     * 访客量
     */
    private Long visitorVolume;

    /**
     * 日期
     */
    private String time;

}
