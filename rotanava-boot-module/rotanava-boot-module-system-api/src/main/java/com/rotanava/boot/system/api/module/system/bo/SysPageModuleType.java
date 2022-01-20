package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-04-16 10:41
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysPageModuleType")
@Data
@TableName(value = "sys_page_module_type")
public class SysPageModuleType implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 页面模块类型
     */
    @ApiModelProperty(value="页面模块类型")
    private String name;

    private static final long serialVersionUID = 1L;
}