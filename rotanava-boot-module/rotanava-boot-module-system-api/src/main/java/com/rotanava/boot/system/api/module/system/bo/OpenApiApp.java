package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 14:48
 */
@ApiModel(value = "com-rotanava-boot-system-api-module-system-bo-OpenApiApp")
@Data
@TableName(value = "open_api_app")
public class OpenApiApp implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 应用id
     */
    @TableField(value = "app_id")
    @ApiModelProperty(value = "应用id")
    private Integer appId;

    /**
     * 接口id
     */
    @TableField(value = "api_id")
    @ApiModelProperty(value = "接口id")
    private Integer apiId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    private static final long serialVersionUID = 1L;
}