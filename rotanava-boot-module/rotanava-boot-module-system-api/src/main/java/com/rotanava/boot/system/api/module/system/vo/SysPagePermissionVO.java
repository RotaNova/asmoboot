package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 添加系统页面权限
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class SysPagePermissionVO implements Serializable {

    /**
     * 父页面资源ID
     */
    private Integer parentPageId;

    /**
     * 系统页面资源
     */
    private Integer sysPageId;

    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 页面模块类型id
     */
    @ApiModelProperty(value = "页面模块类型id")
    private Integer sysPageModuleTypeId;

    /**
     * 页面资源路径
     */
    private String pageUrl;

    /**
     * 页面重定向地址
     */
    private String pageRedirect;

    /**
     * 页面资源类型：0-一级菜单;1-子菜单;2-接口权限
     */
    @Dict(dicCode = "page_type")
    private Integer pageType;

    /**
     * 页面资源编码
     */
    private String pageCode;

    /**
     * 页面资源是否可见权限设置:0-不可见:1-可见
     */
    @Dict(dicCode = "page_show")
    private Integer pageShow;

    /**
     * 页面资源是否为叶子节点:0-不是;1-是
     */
//    @Dict(dicCode = "page_leaf_flag")
    private Integer pageLeafFlag;

    /**
     * 页面资源描述
     */
    private String pageDescription;

    /**
     * 页面资源状态:0-未激活:1-正常;2-过期
     */
    @Dict(dicCode = "page_status")
    private Integer pageStatus;

    /**
     * 页面类型 0增 1删 2改 3查
     */
//    @Dict(dicCode = "ability_type")
    private Integer abilityType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否全员可见 0-否 1-是
     */
//    @Dict(dicCode = "visible_to_all")
    private Integer visibleToAll;

    /**
     * 是否跳转外部应用 0-否 1-是
     */
//    @Dict(dicCode = "is_jump")
    private Integer isJump;

    /**
     * 页面图标
     */
    private String pageIcon;

    /**
     * 是否自动生成 0-否 1-是
     */
    private Integer isAuto;

    /**
     * 自动生成json
     */
    private String pageJson;

}