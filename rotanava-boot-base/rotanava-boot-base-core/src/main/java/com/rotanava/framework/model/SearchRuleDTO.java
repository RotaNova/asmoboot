package com.rotanava.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 高级搜索规则bean
 * @author: richenLi
 * @create: 2021-03-12 14:20
 **/
@Data
public class SearchRuleDTO implements Serializable {

    /**
     * 规则
     */
    @ApiModelProperty(value = "规则 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String rule;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String type;

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String filed;

    /**
     * 字段值
     */
    @ApiModelProperty(value = "字段值 如果需要看文档 http://rep.rotanova.top/organization/repository/editor?id=5&mod=116&itf=850")
    private String value;
}
