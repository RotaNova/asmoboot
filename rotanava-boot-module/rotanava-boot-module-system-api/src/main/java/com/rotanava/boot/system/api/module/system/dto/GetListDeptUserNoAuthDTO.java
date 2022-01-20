package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-26 17:09
 */
@Data
public class GetListDeptUserNoAuthDTO extends BaseDTO {

    @NotNull
    private Integer deptRoleId;

}