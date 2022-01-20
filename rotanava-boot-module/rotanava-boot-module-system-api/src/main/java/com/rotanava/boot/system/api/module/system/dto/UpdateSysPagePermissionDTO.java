package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 修改系统页面资源
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateSysPagePermissionDTO extends AddSysPagePermissionDTO implements Serializable {


    /**
     * 系统页面资源id
     */
    @NotNull(message = "系统页面资源id不能为空")
    private Integer sysPageId;



}