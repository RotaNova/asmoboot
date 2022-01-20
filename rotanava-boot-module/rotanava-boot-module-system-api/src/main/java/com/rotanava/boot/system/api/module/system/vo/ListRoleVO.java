package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 角色列表
 * @author: jintengzhou
 * @date: 2021-03-03 17:55
 */
@Data
public class ListRoleVO  implements Serializable {
    /**
     * 系统角色ID
     */
    private Integer sysRoleId;

    /**
     * 系统角色编码
     */
    private String roleCode;

    /**
     * 系统角色描述
     */
    private String roleDescription;
}