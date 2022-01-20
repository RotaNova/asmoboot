package com.rotanava.boot.system.api.module.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统表字段dto
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTableFieldDTO implements Serializable {

    /**
     * 表字段id
     */
    @NotBlank(message = "表字段id不能为空")
    private Integer tableFieldId;

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
    @NotBlank(message = "固定位置不能为空")
    private Integer fixed;

    /**
     * 宽度
     */
    @NotBlank(message = "宽度不能为空")
    private Integer width;

    /**
     * align 1居中 2不居中
     */
    @NotBlank(message = "align不能为空")
    private Integer align;

    /**
     * scopedSlots 1需要 2不需要
     */
    @NotBlank(message = "scopedSlots不能为空")
    private Integer scopedSlots;


}