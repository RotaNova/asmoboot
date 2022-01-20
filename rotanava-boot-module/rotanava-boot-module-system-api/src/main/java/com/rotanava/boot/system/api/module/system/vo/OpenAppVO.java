package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class OpenAppVO implements Serializable {

    @ApiModelProperty(value = "应用名id")
    private Integer openAppId;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "app logo url")
    private String appImageUrl;

    @ApiModelProperty(value = "应用描述")
    private String remark;

    @ApiModelProperty(value = "负责人")
    private String chargePerson;

    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

    @ApiModelProperty(value = "agentId")
    private String agentId;

    @ApiModelProperty(value = "AppKey")
    private String appKey;

    @ApiModelProperty(value = "Appsecret")
    private String appSecret;

//    @ApiModelProperty(value = "创建时间")
//    private Long createTime;
//
//    @ApiModelProperty(value = "更新时间")
//    private Long updateTime;


    private static final long serialVersionUID = 1L;
}