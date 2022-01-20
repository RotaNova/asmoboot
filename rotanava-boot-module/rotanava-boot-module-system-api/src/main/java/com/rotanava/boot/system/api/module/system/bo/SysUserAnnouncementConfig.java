package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 16:59
 */
@ApiModel(value = "com-rotanava-boot-system-module-dao-dd-SysUserAnnouncementConfig")
@Data
@TableName(value = "sys_user_announcement_config")
public class SysUserAnnouncementConfig implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private Integer sysUserId;

    @ApiModelProperty(value = "")
    private Integer sysAnnConfigId;

    private static final long serialVersionUID = 1L;
}