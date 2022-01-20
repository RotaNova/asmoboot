package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

/**
 * @description: 平台配置VO
 * @author: richenLi
 * @create: 2021-03-23 15:43
 **/
@Data
public class PlatformSettingVO {

    /**
     * logo选项
     */
    private Integer logoOption;

    /**
     * logo url
     */
    private String logoUrl;

    /**
     * bannerUrl
     */
    private String bannerUrl;

    /**
     * banner选项
     */
    private Integer bannerOption;

    /**
     * banner是否可关闭选项
     */
    private Integer bannerCloseOption;


    /**
     * banner显示频率
     */
    private Integer bannerFrequency;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点名称选项
     */
    private Integer siteOption;



}
