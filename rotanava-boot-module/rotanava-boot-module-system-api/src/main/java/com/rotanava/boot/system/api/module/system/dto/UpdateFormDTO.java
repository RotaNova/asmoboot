package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.common.annotation.valid.CodeNotBlank;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 表单dto
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
public class UpdateFormDTO {

    @NotNull(message = "表格id不能为空")
    private Integer tableConfigId;

    /**
     * 表格名
     */
    @NotBlank(message = "表格名不能为空")
    private String tableName;

    /**
     * 表格编码
     */
    @CodeNotBlank
    private String tableCode;

    /**
     * 表字段
     */
    private List<SysTableFieldDTO> tableFields;

    /**
     * 删除字段id集合
     */
    private List<Integer> removeTableFieldIds;

}
