package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 17:54
 */
@Data
public class UpdateOpenAppApiDTO implements Serializable {

    @NotNull
    @ApiModelProperty(value = "api id")
    private Integer openApiId;

    @ApiModelProperty(value = "api名称")
    private String apiName;

    @ApiModelProperty(value = "api路径")
    private String apiPath;

    @ApiModelProperty(value = "请求方式 1GET 2POST")
    private Integer requestMethod;

    @ApiModelProperty(value = "返回类型 1json")
    private Integer resultType;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "负责人")
    private String chargePerson;

}