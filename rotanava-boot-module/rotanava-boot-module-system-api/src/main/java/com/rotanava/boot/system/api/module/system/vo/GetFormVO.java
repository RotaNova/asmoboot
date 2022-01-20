package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 得到的形式签证官
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
public class GetFormVO {

    /**
     * 表格id
     */
    private Integer tableConfigId;

    /**
     * 表格名
     */
    private String tableName;

    /**
     * 表格编码
     */
    private String tableCode;

    /**
     * 字段名
     */
    private List<String> fieldNames;

}
