package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysApiPermission")
@Data
@TableName(value = "sys_api_permission")
public class SysApiPermission implements Serializable {
    /**
     * 接口ID
     */
    @TableId(type = IdType.AUTO)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value="接口ID")
    private Integer id;

    /**
     * 接口名称
     */
    @TableField(value = "api_name", whereStrategy = FieldStrategy.NOT_EMPTY)
    @ApiModelProperty(value="接口名称")
    private String apiName;

    /**
     * 接口编码
     */
    @TableField(value = "api_code", whereStrategy = FieldStrategy.NOT_EMPTY)
    @ApiModelProperty(value="接口编码")
    private String apiCode;

    /**
     * 接口路径
     */
    @TableField(value = "api_url")
    @ApiModelProperty(value="接口路径")
    private String apiUrl;

    /**
     * 接口请求方式
     */
    @TableField(value = "api_method", whereStrategy = FieldStrategy.NOT_EMPTY)
    @ApiModelProperty(value="接口请求方式")
    private Integer apiMethod;

    /**
     * 接口创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="接口创建人")
    private Integer createBy;

    /**
     * 接口创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="接口创建时间")
    private Date createTime;

    /**
     * 接口更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="接口更新人")
    private Integer updateBy;

    /**
     * 接口更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="接口更新时间")
    private Date updateTime;

    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    @TableField(value = "api_auth_type", whereStrategy = FieldStrategy.NOT_EMPTY)
    @ApiModelProperty(value="接口鉴权方式:0-不鉴权;1-Token鉴权")
    private Integer apiAuthType;

    /**
     * 接口能力类型
     */
    @TableField(value = "api_ability_type")
    @ApiModelProperty(value="接口能力类型")
    private Integer apiAbilityType;

    private static final long serialVersionUID = 1L;
}