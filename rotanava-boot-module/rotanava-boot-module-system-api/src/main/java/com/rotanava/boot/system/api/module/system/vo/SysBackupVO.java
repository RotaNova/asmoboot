package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-04-06 14:30
 **/
@Data
public class SysBackupVO {

    /**
     * 系统备份ID
     */
    private Integer id;

    /**
     * 系统备份名称
     */
    private String bakName;

    /**
     * 系统备份编码
     */
    private String bakCode;

    /**
     * 系统备份类型 1自动备份 2手动备份
     */
    @Dict(dicCode = "backupType")
    private Integer bakType;

    /**
     * 系统备份完成时间
     */
    private Date bakTime;

    /**
     * 系统备份创建人ID
     */
    private Integer createBy;

    /**
     * 系统备份创建时间
     */
    private Date createTime;

    /**
     * 备份文件大小
     */
    private String fileSize;

    /**
     * 文件链接
     */
    private String fileUrl;


    /**
     * 业务类型 1系统备份 2日志备份
     */
    private Integer serviceType;

}
