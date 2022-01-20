package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-24 15:03
 **/
@Data
public class PlatformSettingDTO {

    /**
     * logo选项
     */
    private Integer logoOption;

    /**
     * logo
     */
    private String logoImage;

    /**
     * banner
     */
    private String bannerImage;

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
