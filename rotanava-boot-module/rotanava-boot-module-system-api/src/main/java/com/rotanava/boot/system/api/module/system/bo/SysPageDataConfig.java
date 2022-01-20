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
public class SysPageDataConfig implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 页面id
     */
    private Integer pageId;

    /**
     * 数据前缀中文名
     */
    private String dataPrefix;

    /**
     * 后缀单位值
     */
    private String dataSuffix;

    /**
     * 唯一标识码
     */
    private String dataCode;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PAGE_ID = "page_id";

    public static final String COL_DATA_PREFIX = "data_prefix";

    public static final String COL_DATA_SUFFIX = "data_suffix";

    public static final String COL_DATA_CODE = "data_code";

    public static final String COL_CREATE_TIME = "create_time";
}