package com.rotanava.boot.system.api.module.constant;

/**
 * banner显示频率
 *
 * @author WeiQiangMiao
 * @date 2021-03-31
 */
public enum BannerOption {

    //banner选项 1关闭 2开启

    /**
     * 1-关闭
     */
    SHUT_DOWN(1),

    /**
     * 2开启
     */
    TURN_ON(2)
    ;
    private Integer option;

    BannerOption(Integer option) {
        this.option = option;
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }
}
