package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 修改系统接口资源
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateSysApiPermissionDTO extends AddSysApiPermissionDTO implements Serializable {


    /**
     * 系统接口id
     */
    @NotNull(message = "系统接口id不能为空")
    private Integer sysApiId;



}