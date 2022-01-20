package com.rotanava.framework.common.constant.enums;

import com.rotanava.framework.util.StringUtil;

/**
 * 映射类型
 *
 * @author WeiQiangMiao
 * @date 2021-05-08
 */
public enum MappingEnum {
    //

    /**
     * 系统页面权限的页面资源编码
     */
    SYS_PAGE_PERMISSION_PAGE_CODE("GtZdwqmH","sys_page_permission","EHftTbAG","page_code"),

    /**
     * 系统接口权限的接口资源编码
     */
    SYS_API_PERMISSION_API_CODE("zrkFxOpF","sys_api_permission","uIrxAGUu","api_code")


    ;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 隐藏表名
     */
    private String hideTableName;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 隐藏字段名
     */
    private String hideFieldName;

    MappingEnum(String tableName, String hideTableName, String fieldName, String hideFieldName) {
        this.tableName = tableName;
        this.hideTableName = hideTableName;
        this.fieldName = fieldName;
        this.hideFieldName = hideFieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getHideTableName() {
        return hideTableName;
    }

    public void setHideTableName(String hideTableName) {
        this.hideTableName = hideTableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHideFieldName() {
        return hideFieldName;
    }

    public void setHideFieldName(String hideFieldName) {
        this.hideFieldName = hideFieldName;
    }

    public static MappingEnum getMappingEnum(String tableName, String fieldName) {
        for (MappingEnum value : values()) {
            if (value.getTableName().equals(tableName) && value.getFieldName().equals(fieldName)) {
                return value;
            }
        }
        return null;
    }
}
