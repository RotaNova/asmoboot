package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.common.annotation.valid.CodeNotBlank;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加表单dto
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
public class AddFormDTO {

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

}
