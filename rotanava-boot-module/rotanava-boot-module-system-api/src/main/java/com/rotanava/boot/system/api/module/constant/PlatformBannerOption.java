package com.rotanava.boot.system.api.module.constant;

/**
 * 平台横幅选项
 *
 * @author WeiQiangMiao
 * @date 2021-05-14
 */
public enum PlatformBannerOption {

    //banner选项 1默认 2自定义
    /**
     * 默认的
     */
    DEFAULT(1),

    /**
     * 自定义
     */
    CUSTOM(2)
    ;

    private Integer option;

    PlatformBannerOption(Integer option) {
        this.option = option;
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }
}
