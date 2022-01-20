package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.boot.system.api.module.system.bean.ApiParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 9:46
 */
@Data
public class SqlConfigurationVO implements Serializable {

    @ApiModelProperty(value = "api id")
    private Integer openApiId;

    @ApiModelProperty(value = "sql配置")
    private String sqlText;

    @ApiModelProperty(value = "请求参数")
    private List<ApiParam> requestApiParam;

    @ApiModelProperty(value = "返回参数")
    private List<ApiParam> returnApiParam;

    @ApiModelProperty(value = "数据源id")
    private Integer datasourceId;

    @ApiModelProperty(value = "数据源下拉列表数据")
    private List<OpenDataSourceItem> dataSourceItems;

}