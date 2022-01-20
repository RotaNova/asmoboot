package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-12 15:00
 */
@Data
public class DepartmentUnlinkUserDTO implements Serializable {

    /**
     * 部门id
     */
    @NotNull
    private Integer deptId;

    /**
     * 用户id
     */
    private List<Integer> sysUserIdList;

}