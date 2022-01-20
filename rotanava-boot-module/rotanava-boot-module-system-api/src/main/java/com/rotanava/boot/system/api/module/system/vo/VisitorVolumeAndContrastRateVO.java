package com.rotanava.boot.system.api.module.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class VisitorVolumeAndContrastRateVO {

    /**
     * 访客量
     */
    private Long visitorVolume;

    /**
     * 时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String time;

    /**
     * 访客量对比率
     */
    private Double visitorVolumeContrastRate;

    public VisitorVolumeAndContrastRateVO(Long visitorVolume, Double visitorVolumeContrastRate) {
        this.visitorVolume = visitorVolume;
        this.visitorVolumeContrastRate = visitorVolumeContrastRate;
    }
}
