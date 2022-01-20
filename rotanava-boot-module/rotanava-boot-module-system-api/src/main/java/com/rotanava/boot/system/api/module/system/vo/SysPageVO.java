package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 11:40
 */
@Data
public class SysPageVO implements Serializable {

    /**
     * 页面id
     */
    private Integer pageId;

    /**
     * 父页面id
     */
    private Integer parentPageId;

    /**
     * 页面资源标题
     */
    private Integer pageTitle;

    /**
     * 是否勾选
     */
    private Boolean choose;
}