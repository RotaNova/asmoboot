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
@ApiModel(value = "com-rotanava-boot-system-api-module-system-bo-OpenApiParam")
@Data
@TableName(value = "open_api_param")
public class OpenApiParam implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 参数编码
     */
    @TableField(value = "param_code")
    @ApiModelProperty(value = "参数编码")
    private String paramCode;

    /**
     * 编码别名
     */
    @TableField(value = "code_alias")
    @ApiModelProperty(value = "编码别名")
    private String codeAlias;

    /**
     * 是否必填 1必填 2非必填
     */
    @TableField(value = "is_fill")
    @ApiModelProperty(value = "是否必填 1必填 2非必填")
    private Integer isFill;

    /**
     * 请求类型 1文本 2数值 3数组
     */
    @TableField(value = "param_type")
    @ApiModelProperty(value = "请求类型 1文本 2数值 3数组")
    private Integer paramType;

    /**
     * 描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "描述")
    private String remark;

    /**
     * 接口id
     */
    @TableField(value = "api_id")
    @ApiModelProperty(value = "接口id")
    private Integer apiId;

    /**
     * 类型 1请求参数 2返回参数
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型 1请求参数 2返回参数")
    private Integer type;

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

    private static final long serialVersionUID = 1L;
}