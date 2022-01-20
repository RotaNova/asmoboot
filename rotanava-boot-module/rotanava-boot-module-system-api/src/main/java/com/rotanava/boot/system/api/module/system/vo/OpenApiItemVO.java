package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 10:51
 */
@Data
public class OpenApiItemVO implements Serializable {

    @ApiModelProperty(value = "openApiId")
    private Integer openApiId;

    @ApiModelProperty(value = "api名称")
    private String apiName;

    @ApiModelProperty(value = "请求方式 1GET 2POST")
    @Dict(dicCode = "requestMethod")
    private Integer requestMethod;

    @ApiModelProperty(value = "api路径")
    private String apiPath;

    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "状态 1正常 2锁定")
    @Dict(dicCode = "openApiStatus")
    private Integer status;

    /**
     * 返回类型 1json
     */
    @ApiModelProperty(value = "返回类型 1json")
    private Integer resultType;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    private static final long serialVersionUID = 1L;

}