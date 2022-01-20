package com.rotanava.framework.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-08 17:37
 **/
@Data
@TableName(value = "sys_dict")
public class SysDict {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 排序类型 0不排序 1排序
     */
    private Integer sortType;

    /**
     * 0-已删除;1-未删除
     */
    private Integer deleteStatus = 1;
}
