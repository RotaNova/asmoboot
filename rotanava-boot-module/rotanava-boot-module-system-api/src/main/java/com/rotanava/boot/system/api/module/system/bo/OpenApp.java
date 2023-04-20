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
 * @date: 2021-04-19 14:48
 */
@ApiModel(value = "com-rotanava-boot-system-api-module-system-bo-OpenApp")
@Data
@TableName(value = "open_app")
public class OpenApp implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 应用名
     */
    @TableField(value = "app_name")
    @ApiModelProperty(value = "应用名")
    private String appName;

    @TableField(value = "logo_bucket_name")
    @ApiModelProperty(value = "")
    private String logoBucketName;

    @TableField(value = "logo_object_name")
    @ApiModelProperty(value = "")
    private String logoObjectName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 负责人
     */
    @TableField(value = "charge_person")
    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    /**
     * 联系方式
     */
    @TableField(value = "contact_phone")
    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

    @TableField(value = "agent_id")
    @ApiModelProperty(value = "")
    private String agentId;

    /**
     * AppKey
     */
    @TableField(value = "app_key")
    @ApiModelProperty(value = "AppKey")
    private String appKey;

    /**
     * Appsecret
     */
    @TableField(value = "app_secret")
    @ApiModelProperty(value = "Appsecret")
    private String appSecret;

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
     * 创建人
     */
    @TableField(value = "module_id")
    @ApiModelProperty(value = "模块id")
    private Integer moduleId;


    /**
     * 是否启用 0-关闭 1-启用
     */
    @TableField(value = "is_switch")
    @ApiModelProperty(value = "是否启用")
    private Integer isSwitch;

    private static final long serialVersionUID = 1L;
}