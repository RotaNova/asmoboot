package com.rotanava.boot.system.api.module.constant;

/**
 * banner显示频率
 *
 * @author WeiQiangMiao
 * @date 2021-03-31
 */
public enum BannerFrequency {

    //banner显示频率 1-每日首次登陆 2-一直

    /**
     * 1-每日首次登陆
     */
    FIRST_LOGIN(1),

    /**
     * 2-一直
     */
    ALWAYS(2)
    ;
    private Integer frequency;

    BannerFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
