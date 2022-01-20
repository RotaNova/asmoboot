package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-DataSet")
@Data
@TableName(value = "data_set")
public class DataSet implements Serializable {
    /**
     * 数据集ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "数据集ID")
    private Integer id;

    /**
     * 数据集名称
     */
    @TableField(value = "data_set_name")
    @ApiModelProperty(value = "数据集名称")
    private String dataSetName;

    /**
     * 数据集标识
     */
    @TableField(value = "data_set_code")
    @ApiModelProperty(value = "数据集标识")
    private String dataSetCode;

    /**
     * 数据集创建人
     */
    @TableField(value = "data_set_create_by")
    @ApiModelProperty(value = "数据集创建人")
    private Integer dataSetCreateBy;

    /**
     * 数据集创建时间
     */
    @TableField(value = "data_set_create_time")
    @ApiModelProperty(value = "数据集创建时间")
    private Date dataSetCreateTime;

    /**
     * 数据集信息更新人
     */
    @TableField(value = "data_set_update_by")
    @ApiModelProperty(value = "数据集信息更新人")
    private Integer dataSetUpdateBy;

    /**
     * 数据集信息更新时间
     */
    @TableField(value = "data_set_update_time")
    @ApiModelProperty(value = "数据集信息更新时间")
    private Date dataSetUpdateTime;

    private static final long serialVersionUID = 1L;
}