package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-16 10:41
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysPageModuleType")
@Data
public class SysPageModuleTypeVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "页面模块类型id")
    private Integer sysPageModuleTypeId;

    /**
     * 页面模块类型
     */
    @ApiModelProperty(value = "页面模块类型名称")
    private String sysPageModuleTypeName;

    private static final long serialVersionUID = 1L;
}