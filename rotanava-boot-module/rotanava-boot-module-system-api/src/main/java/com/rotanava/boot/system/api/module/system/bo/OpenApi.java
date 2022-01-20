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
@ApiModel(value = "com-rotanava-boot-system-api-module-system-bo-OpenApi")
@Data
@TableName(value = "open_api")
public class OpenApi implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * api名称
     */
    @TableField(value = "api_name")
    @ApiModelProperty(value = "api名称")
    private String apiName;

    /**
     * api路径
     */
    @TableField(value = "api_path")
    @ApiModelProperty(value = "api路径")
    private String apiPath;

    /**
     * 请求方式 1GET 2POST
     */
    @TableField(value = "request_method")
    @ApiModelProperty(value = "请求方式 1GET 2POST")
    private Integer requestMethod;

    /**
     * 返回类型 1json
     */
    @TableField(value = "result_type")
    @ApiModelProperty(value = "返回类型 1json")
    private Integer resultType;

    /**
     * sql配置
     */
    @TableField(value = "sql_text")
    @ApiModelProperty(value = "sql配置")
    private String sqlText;

    /**
     * 描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "描述")
    private String remark;

    /**
     * 返回类型 1json
     */
    @TableField(value = "datasource_id")
    @ApiModelProperty(value = "数据源id")
    private Integer datasourceId;


    /**
     * 负责人
     */
    @TableField(value = "charge_person")
    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    /**
     * 状态 1正常 2锁定
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态 1正常 2锁定")
    private Integer status;

    private static final long serialVersionUID = 1L;
}