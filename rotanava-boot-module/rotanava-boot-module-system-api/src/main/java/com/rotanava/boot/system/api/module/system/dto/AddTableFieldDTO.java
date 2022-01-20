package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 添加表字段签证官
 *
 * @author weiqiangmiao
 * @date 2021/06/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTableFieldDTO {

    /**
     * 字段名
     */
    @NotBlank(message = "字段名不能为空")
    private String fieldName;

    /**
     * 字段编码
     */
    @NotBlank(message = "字段编码不能为空")
    private String fieldCode;

    /**
     * 固定位置 1无 2left 3 right
     */
    @NotNull(message = "固定位置不能为空")
    private Integer fixed;

    /**
     * 宽度
     */
    @NotNull(message = "宽度不能为空")
    private Integer width;

    /**
     * align 1居中 2不居中
     */
    @NotNull(message = "align不能为空")
    private Integer align;

    /**
     * scopedSlots 1需要 2不需要
     */
    @NotNull(message = "scopedSlots不能为空")
    private Integer scopedSlots;

    /**
     * 表格id
     */
    @NotNull(message = "表格id不能为空")
    private Integer tableConfigId;

}
