package com.rotanava.boot.system.api.module.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 欢迎页签证官
 *
 * @author weiqiangmiao
 * @date 2021/07/07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WelcomePagesVO {
    /**
     * 系统页面id
     */
    private Integer sysPageId;
    /**
     * 父页面id
     */
    private Integer parentPageId;
    /**
     * 页面标题
     */
    private String pageTitle;
    /**
     * 页面重定向
     */
    private String pageRedirect;
    /**
     * 页面的url
     */
    private String pageUrl;
    /**
     * 数据的前缀
     */
    private String dataPrefix;
    /**
     * 数据后缀
     */
    private String dataSuffix;
    /**
     * 数据值
     */
    private Object dataValue;

    /**
     * 是否跳转外部应用 0-否 1-是
     */
    private Integer isJump;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 页面图标
     */
    private String pageIcon;

    /**
     * 是否自动生成 0-否 1-是
     */
    private Integer isAuto;

}