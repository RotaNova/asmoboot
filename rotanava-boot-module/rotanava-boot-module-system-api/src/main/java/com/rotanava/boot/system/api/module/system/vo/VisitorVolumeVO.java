package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访客量VO
 *
 * @author WeiQiangMiao
 * @date 2021-04-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorVolumeVO {

    /**
     * 新访客量
     */
    private Long newVisitorVolume;

    /**
     * 旧访客量
     */
    private Long oldVisitorVolume;

    /**
     * 访客量对比率
     */
    private Double visitorVolumeContrastRate;
}
