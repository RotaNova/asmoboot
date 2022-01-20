package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * sys列表页面权限dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class ListSysPagePermissionDTO implements Serializable {


    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 页面资源类型：0-一级菜单;1-子菜单;2-接口权限
     */
    private Integer pageType;

    /**
     * 页面资源编码
     */
    private String pageCode;

    /**
     * 页面资源状态:0-未激活:1-正常;2-过期
     */
    private Integer pageStatus;


    /**
     * 页面模块类型: 0-基础模块;1-业务模块
     */
    private Integer pageModuleType;

}