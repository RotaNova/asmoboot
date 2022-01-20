package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 获取系统接口资源列表
 *
 * @author WeiQiangMiao
 * @date 2021-03-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListSysApiPermissionDTO extends BaseDTO implements Serializable {


    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 页面资源标题
     */
    private String pageTitle;

    /**
     * 接口请求方式
     */
    private Integer apiMethod;

    /**
     * 接口鉴权方式:0-不鉴权;1-Token鉴权
     */
    private Integer apiAuthType;


}
