package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 删除系统页面资源
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
public class DeleteSysPagePermissionDTO implements Serializable {


    /**
     * 系统页面资源id集合
     */
    @NotNull(message = "系统页面资源id集合不能为空")
    private List<String> sysPageIds;



}