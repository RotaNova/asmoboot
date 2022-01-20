package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 添加系统api dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class AddSysApiPermissionDTO implements Serializable {


    /**
     * 系统页面id
     */
    @NotNull(message = "系统页面id不能为空")
    private Integer sysPageId;

    /**
     * 接口名称
     */
    @NotBlank(message = "接口名称不能为空")
    private String apiName;

    /**
     * 接口编码
     */
    @NotBlank(message = "接口编码不能为空")
    private String apiCode;

    /**
     * 接口路径
     */
    @NotBlank(message = "接口路径不能为空")
    private String apiUrl;

    /**
     * 接口请求方式
     */
    @NotNull(message = "接口请求方式不能为空")
    private Integer apiMethod;

    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    @NotNull(message = "接口鉴权方式不能为空")
    private Integer apiAuthType;

    /**
     * 接口能力类型
     */
    private Integer apiAbilityType;

}