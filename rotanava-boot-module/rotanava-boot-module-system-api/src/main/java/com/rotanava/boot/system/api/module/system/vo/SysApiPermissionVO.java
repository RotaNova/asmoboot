package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 接口资源VO
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class SysApiPermissionVO implements Serializable {

    /**
     * 系统接口id
     */
    private Integer sysApiId;

    /**
     * 系统页面id
     */
    private Integer sysPageId;

    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口路径
     */
    private String apiUrl;

    /**
     * 接口请求方式 1get 2post 3put 4delete
     */
    @Dict(dicCode = "api_method")
    private Integer apiMethod;

    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    @Dict(dicCode = "api_auth_type")
    private Integer apiAuthType;

    /**
     * 系统页面模块类型id
     */
    private Integer sysPageModuleTypeId;
}