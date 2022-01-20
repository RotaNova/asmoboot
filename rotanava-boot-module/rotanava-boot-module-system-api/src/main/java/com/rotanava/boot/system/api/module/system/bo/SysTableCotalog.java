package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统具备的表单总览目录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTableCotalog implements Serializable {
    /**
     * 表单信息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表单名称
     */
    private String tableName;

    /**
     * 表单描述
     */
    private String tableDescription;

    /**
     * 表单编码
     */
    private String tableCode;

    /**
     * 表单关联表编码
     */
    private String tableLink;

    /**
     * 表单关联关系：0-1:1;1-1:n;2-n:n
     */
    private Integer tableRelationship;

    /**
     * 表单创建人
     */
    private String createBy;

    /**
     * 表单创建时间
     */
    private Date createTime;

    /**
     * 表单更新人
     */
    private String updateBy;

    /**
     * 表单更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_TABLE_NAME = "table_name";

    public static final String COL_TABLE_DESCRIPTION = "table_description";

    public static final String COL_TABLE_CODE = "table_code";

    public static final String COL_TABLE_LINK = "table_link";

    public static final String COL_TABLE_RELATIONSHIP = "table_relationship";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_UPDATE_TIME = "update_time";
}