package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 添加系统页面权限
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class AddSysPagePermissionDTO implements Serializable {


    /**
     * 父页面资源ID
     */
    private Integer parentPageId;

    /**
     * 页面资源标题
     */
    @NotBlank(message = "页面资源标题不能为空")
    private String pageTitle;

    /**
     * 页面模块类型: 0-基础模块;1-业务模块
     */
    @NotNull(message = "页面模块类型不能为空")
    private Integer sysPageModuleTypeId;

    /**
     * 页面资源路径
     */
    @NotBlank(message = "页面资源路径不能为空")
    private String pageUrl;

    /**
     * 页面重定向地址
     */
    private String pageRedirect;

    /**
     * 页面资源类型：0-一级菜单;1-子菜单;2-接口权限
     */
    @NotNull(message = "页面资源类型不能为空")
    private Integer pageType;

    /**
     * 页面资源编码
     */
    @NotBlank(message = "页面资源编码不能为空")
    private String pageCode;

    /**
     * 页面资源是否可见权限设置:0-不可见:1-可见
     */
    @NotNull(message = "页面资源是否可见权限设置不能为空")
    private Integer pageShow;

    /**
     * 页面资源描述
     */
    private String pageDescription;

    /**
     * 页面资源状态:0-未激活:1-正常;2-过期
     */
    @NotNull(message = "页面资源状态不能为空")
    private Integer pageStatus;

    /**
     * 页面类型 0增 1删 2改 3查
     */
    private Integer abilityType;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;


    /**
     * 是否全员可见 0-否 1-是
     */
    @NotNull(message = "是否全员可见不能为空")
    private Integer visibleToAll;

    /**
     * 是否跳转外部应用 0-否 1-是
     */
    @NotNull(message = "是否跳转外部应用不能为空")
    private Integer isJump;

    /**
     * 页面图标
     */
    private String pageIcon;

    /**
     * 是否自动生成 0-否 1-是
     */
    @NotNull(message = "是否自动生成不能为空")
    private Integer isAuto;

    /**
     * 自动生成json
     */
    private String pageJson;

}