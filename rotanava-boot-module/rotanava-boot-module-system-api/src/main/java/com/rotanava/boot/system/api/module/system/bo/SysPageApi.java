package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysPageApi")
@Data
@TableName(value = "sys_page_api")
@NoArgsConstructor
@AllArgsConstructor
public class SysPageApi implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value="")
    private Integer id;

    /**
     * 接口id
     */
    @TableField(value = "sys_api_id")
    @ApiModelProperty(value="接口id")
    private Integer sysApiId;

    /**
     * 页面id
     */
    @TableField(value = "sys_page_id")
    @ApiModelProperty(value="页面id")
    private Integer sysPageId;

    private static final long serialVersionUID = 1L;

    public SysPageApi(Integer sysApiId, Integer sysPageId) {
        this.sysApiId = sysApiId;
        this.sysPageId = sysPageId;
    }
}