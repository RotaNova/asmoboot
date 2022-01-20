package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 表单dto
 *
 * @author weiqiangmiao
 * @date 2021/06/26
 */
@Data
public class SysTableConfigVO {

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
     * 表字段
     */
    private List<SysTableFieldVO> tableFields;

}
