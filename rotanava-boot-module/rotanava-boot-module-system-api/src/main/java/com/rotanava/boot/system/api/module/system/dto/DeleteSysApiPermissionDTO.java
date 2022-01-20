package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 删除系统接口资源
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class DeleteSysApiPermissionDTO implements Serializable {


    /**
     * 系统接口id集合
     */
    @NotNull(message = "系统接口id集合不能为空")
    private List<Integer> sysApiIds;



}