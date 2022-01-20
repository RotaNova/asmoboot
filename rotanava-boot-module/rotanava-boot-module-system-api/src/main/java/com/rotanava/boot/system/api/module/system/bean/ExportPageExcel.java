package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Data
@ExcelTag(tag = "页面权限列表")
public class ExportPageExcel implements Serializable {

    /**
     * 所属模块
     */
    @ExcelTag(tag = "所属模块",width = 3000,index = 0)
    private String sysPageModuleTypeName;

    /**
     * *页面名称
     */
    @ExcelTag(tag = "页面名称",width = 3000,index = 1)
    private String pageTitle;

    /**
     * *页面编码
     */
    @ExcelTag(tag = "页面编码",width = 3000,index = 2)
    private String pageCode;

    /**
     * 页面资源路径
     */
    @ExcelTag(tag = "页面资源路径",width = 3000,index = 3)
    private String pageUrl;

    /**
     * 页面重定向地址
     */
    @ExcelTag(tag = "页面重定向地址",width = 3000,index = 4)
    private String pageRedirect;

    /**
     * 页面图标
     */
    @ExcelTag(tag = "页面图标",width = 3000,index = 5)
    private String pageIcon;

    /**
     * *是否可见（可见/不可见）
     */
    @ExcelTag(tag = "是否可见",width = 3000,index = 6)
    private String pageShow;

    /**
     * 是否全员可见（是/否）
     */
    @ExcelTag(tag = "是否全员可见",width = 3000,index =7)
    private String visibleToAll;

    /**
     * *是否外链（是/否）
     */
    @ExcelTag(tag = "是否外链",width = 3000,index = 8)
    private String isJump;

    /**
     * 页面资源状态（未激活/正常/过期）
     */
    @ExcelTag(tag = "页面资源状态",width = 3000,index = 9)
    private String pageStatus;

    /**
     *页面资源类型（一级菜单/子菜单/接口权限）
     */
    @ExcelTag(tag = "页面资源类型",width = 3000,index = 10)
    private String pageType;

    /**
     * 页面类型（增/删/改/查，页面资源类型为接口权限时需填写此项）
     */
    @ExcelTag(tag = "页面类型",width = 3000,index = 11)
    private String abilityType;

    /**
     * 父节点（填写父节点的页面编码，页面资源类型为一级菜单时，此内容无效）
     */
    @ExcelTag(tag = "父节点",width = 3000,index = 12)
    private String parentPageCode;

    /**
     * 页面资源描述
     */
    @ExcelTag(tag = "页面资源描述",width = 3000,index = 13)
    private String pageDescription;


    /**
     * *是否自动生成（是/否）
     */
    @ExcelTag(tag = "是否自动生成",width = 3000,index = 14)
    private String isAuto;

    /**
     * *代码（是否自动生成为否时，此项无需填写）
     */
    @ExcelTag(tag = "代码",width = 3000,index = 15)
    private String pageJson;


    /**
     * *排序（仅可填正整数）
     */
    @ExcelTag(tag = "排序",width = 3000,index = 16)
    private String sort;








}