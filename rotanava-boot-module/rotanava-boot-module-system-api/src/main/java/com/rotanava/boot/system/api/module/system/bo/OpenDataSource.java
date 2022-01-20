package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rotanava.framework.common.aspect.annotation.Dict;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName(value = "open_datasource")
public class OpenDataSource implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    Integer id;

    @TableField
    String datasourceName;


    @TableField
    String url;

    @TableField
    String userName;

    @TableField
    String userPassword;

    @Dict(dicCode = "db_type")
    @TableField(value = "db_type")
    Integer dbType;

    @TableField
    String driver;

    @TableField(value = "table_sql")
    String tableSql;

    @TableField(value = "remark")
    String remark;


    @TableField(value = "create_time")
    Date createTime;

    @TableField(value = "update_time")
    Date updateTime;

}
