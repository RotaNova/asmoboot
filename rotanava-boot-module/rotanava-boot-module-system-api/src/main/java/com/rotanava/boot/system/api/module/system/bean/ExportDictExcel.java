package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.framework.util.excel.annotation.ExcelTag;
import lombok.Data;

/**
 * 导出excel
 *
 * @author weiqiangmiao
 * @date 2021/11/11
 */
@Data
@ExcelTag(tag = "字典列表")
public class ExportDictExcel {

    /**
     * *类型（字典/数据）
     */
    @ExcelTag(tag = "类型",width = 3000 ,index = 0)
    private String type;

    /**
     * 字典名称
     */
    @ExcelTag(tag = "字典名称",width = 3000 ,index = 1)
    private String dictName;

    /**
     * 字典编码
     */
    @ExcelTag(tag = "字典编码",width = 3000 ,index = 2)
    private String dictCode;


    /**
     * 字典项文本
     */
    @ExcelTag(tag = "数据名称",width = 3000 ,index = 3)
    private String itemText;

    /**
     * 字典项值
     */
    @ExcelTag(tag = "数据值",width = 3000 ,index = 4)
    private String itemValue;

    /**
     * 描述
     */
    @ExcelTag(tag = "描述",width = 3000 ,index = 5)
    private String description;

    /**
     * 排序
     */
    @ExcelTag(tag = "排序",width = 3000 ,index = 6)
    private String sort;

    /**
     * 状态
     */
    @ExcelTag(tag = "是否启用",width = 3000 ,index = 7)
    private String status;


}
