package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rotanava.boot.system.api.module.system.group.Department;
import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-10 16:40
 */
@Data
public class GetListSysUserDTO extends BaseDTO {

    @ApiModelProperty("系统用户当前是否被删除:0-已删除;1-未删除")
    private Integer userDeleteStatus;

    @ApiModelProperty("部门id")
    @NotNull(groups = {Department.class})
    private Integer deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 系统用户的账号身份:0-成员;1-管理员
     */
    @ApiModelProperty(value = "系统用户的账号身份:0-成员;1-管理员")
    private Integer userSysrole;
}