package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.boot.system.api.module.system.bean.ApiParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 9:46
 */
@Data
public class SaveSqlConfigurationDTO implements Serializable {

    @NotNull
    @ApiModelProperty(value = "api id")
    private Integer openApiId;

    @ApiModelProperty(value = "sql配置")
    @NotBlank
    private String sqlText;

    @ApiModelProperty(value = "数据源id")
    private Integer datasourceId;

    @ApiModelProperty(value = "请求参数")
    private List<ApiParam> requestApiParam;

    @ApiModelProperty(value = "返回参数")
    private List<ApiParam> returnApiParam;

}