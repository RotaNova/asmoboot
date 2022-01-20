package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-05-14 15:13
 */
@Data
public class AuthUserDeptRoleDTO implements Serializable {

    @NotNull
    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "部门角色id")
    private Collection<Integer> deptRoleIdList;

    @ApiModelProperty(value="系统用户标识ID")
    private Integer sysUserId;

}