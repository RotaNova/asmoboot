package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
/**
    * 系统备份信息情况
    */
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysBackup")
@Data
@TableName(value = "sys_backup")
public class SysBackup implements Serializable {
    /**
     * 系统备份ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="系统备份ID")
    private Integer id;

    /**
     * 系统备份名称
     */
    @TableField(value = "bak_name")
    @ApiModelProperty(value="系统备份名称")
    private String bakName;

    /**
     * 系统备份编码
     */
    @TableField(value = "bak_code")
    @ApiModelProperty(value="系统备份编码")
    private String bakCode;

    /**
     * 系统备份类型 1自动备份 2手动备份
     */
    @TableField(value = "bak_type")
    @ApiModelProperty(value="系统备份类型 1自动备份 2手动备份")
    private Integer bakType;

    /**
     * 系统备份完成时间
     */
    @TableField(value = "bak_time")
    @ApiModelProperty(value="系统备份完成时间")
    private Date bakTime;

    /**
     * 系统备份创建人ID
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="系统备份创建人ID")
    private Integer createBy;

    /**
     * 系统备份创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="系统备份创建时间")
    private Date createTime;

    /**
     * 备份文件大小
     */
    @TableField(value = "file_size")
    @ApiModelProperty(value="备份文件大小")
    private String fileSize;

    /**
     * 文件桶名
     */
    @TableField(value = "bucket_name")
    @ApiModelProperty(value="文件桶名")
    private String bucketName;

    /**
     * 文件名
     */
    @TableField(value = "object_name")
    @ApiModelProperty(value="文件名")
    private String objectName;


    /**
     * 业务类型 1系统备份 2日志备份
     */
    @TableField(value = "service_type")
    @ApiModelProperty(value="业务类型 1系统备份 2日志备份")
    private Integer serviceType;


    private static final long serialVersionUID = 1L;
}