package com.rotanava.boot.system.api.module.system.dto.rolemanage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 15:08
 */
@Data
public class UpdateSysRoleDTO extends BaseUpdateRoleDTO {

    /**
     * 系统角色ID
     */
    @NotNull(message = "系统角色ID不能为空")
    @ApiModelProperty(value = "系统角色ID")
    private Integer sysRoleId;

    /**
     * 角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门
     */
    @NotNull(message = "角色数据权限范围不能为空")
    @Range(min = 0, max = 4)
    @ApiModelProperty(value = "角色数据权限范围", required = true)
    private Integer roleDataScope;

    @ApiModelProperty(value = "选择的部门id 当数据范围为4时候需要传")
    private List<Integer> chooseDepartmentIdList;

}