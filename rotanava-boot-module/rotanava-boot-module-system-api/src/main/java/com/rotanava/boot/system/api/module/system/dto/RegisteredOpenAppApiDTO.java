package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 17:51
 */
@Data
public class RegisteredOpenAppApiDTO implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "api名称")
    private String apiName;

    @NotBlank
    @ApiModelProperty(value = "api路径")
    private String apiPath;

    @NotNull
    @ApiModelProperty(value = "请求方式 1GET 2POST")
    private Integer requestMethod;

    @NotNull
    @ApiModelProperty(value = "返回类型 1json")
    private Integer resultType;


    @ApiModelProperty(value = "描述")
    private String remark;

    @NotBlank
    @ApiModelProperty(value = "负责人")
    private String chargePerson;

}