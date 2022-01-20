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
public class UpdateDeptRoleDTO extends BaseUpdateRoleDTO{

    /**
     * 系统角色ID
     */
    @NotNull(message = "部门角色ID")
    @ApiModelProperty(value = "部门角色ID")
    private Integer deptRoleId;

    @ApiModelProperty(value = "选择的部门id 当数据范围为4时候需要传")
    private List<Integer> chooseDepartmentIdList;

}