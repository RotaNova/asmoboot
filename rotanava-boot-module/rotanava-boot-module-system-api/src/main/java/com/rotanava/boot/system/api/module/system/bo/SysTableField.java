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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysTableField implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
    private Integer fixed;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 1居中 2不居中
     */
    private Integer align;

    /**
     * 1需要 2不需要
     */
    private Integer scopedSlots;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 表配置id
     */
    private Integer tableConfigId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_FIELD_NAME = "field_name";

    public static final String COL_FIELD_CODE = "field_code";

    public static final String COL_FIXED = "fixed";

    public static final String COL_WIDTH = "width";

    public static final String COL_ALIGN = "align";

    public static final String COL_SCOPED_SLOTS = "scoped_slots";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_TABLE_CONFIG_ID = "table_config_id";
}