package com.rotanava.boot.system.api.module.system.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 9:54
 */
@Data
public class ApiParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "接口id")
    private Integer apiId;

    @NotNull
    @ApiModelProperty(value = "接口配置id")
    private Integer apiParamId;

    @ApiModelProperty(value = "参数编码")
    private String paramCode;

    @ApiModelProperty(value = "编码别名")
    private String codeAlias;

    @ApiModelProperty(value = "是否必填 1必填 2非必填")
    private Integer isFill;

    @ApiModelProperty(value = "请求类型 1文本 2数值 3数组")
    private Integer paramType;

    @ApiModelProperty(value = "描述")
    private String remark;

}