package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 添加系统表字段dto
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTableFieldVO implements Serializable {

    /**
     * 表字段id
     */
    private Integer tableFieldId;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 固定位置 1无 2left 3 right
     */
    @Dict(dicCode = "fixed")
    private Integer fixed;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * align 1居中 2不居中
     */
    @Dict(dicCode = "align")
    private Integer align;

    /**
     * scopedSlots 1需要 2不需要
     */
    @Dict(dicCode = "scoped_slots")
    private Integer scopedSlots;


}