package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 11:54
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-SysPagePermission")
@Data
public class SysPagePermission implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "页面资源ID")
    private Integer id;

    @ApiModelProperty(value = "父页面资源ID")
    private Integer parentPageId;

    @ApiModelProperty(value = "页面资源标题")
    private String pageTitle;

    @ApiModelProperty(value = "页面资源路径")
    private String pageUrl;

    @ApiModelProperty(value = "页面重定向地址")
    private String pageRedirect;

    @ApiModelProperty(value = "页面资源类型：0-一级菜单;1-子菜单;2-接口权限")
    private Integer pageType;

    @ApiModelProperty(value = "页面资源编码")
    private String pageCode;

    @ApiModelProperty(value = "页面资源是否可见权限设置:0-不可见:1-可见")
    private Integer pageShow;

    @ApiModelProperty(value = "页面资源是否为叶子节点:0-不是;1-是")
    private Integer pageLeafFlag;

    @ApiModelProperty(value = "页面资源描述")
    private String pageDescription;

    @ApiModelProperty(value = "页面资源创建人ID")
    private Integer createBy;

    @ApiModelProperty(value = "页面资源创建时间")
    private Date createTime;

    @ApiModelProperty(value = "页面资源更新人ID")
    private Integer updateBy;

    @ApiModelProperty(value = "页面资源更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "页面资源删除状态")
    private Integer pageDeleteStatus;

    @ApiModelProperty(value = "页面资源状态:0-未激活:1-正常;2-过期")
    private Integer pageStatus;

    @ApiModelProperty(value = "页面类型 0增 1删 2改 3查")
    private Integer abilityType;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "页面模块类型id")
    private Integer sysPageModuleTypeId;

    @ApiModelProperty(value = "是否全员可见 0-否 1-是")
    private Integer visibleToAll;

    @ApiModelProperty(value = "是否跳转外部应用 0-否 1-是")
    private Integer isJump;

    @ApiModelProperty(value = "页面图标")
    private String pageIcon;

    /**
     * 是否自动生成 0-否 1-是
     */
    @ApiModelProperty(value = "是否自动生成")
    private Integer isAuto;

    /**
     * 自动生成json
     */
    @ApiModelProperty(value = "自动生成json")
    private String pageJson;


    private static final long serialVersionUID = 1L;
}