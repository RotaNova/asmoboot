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
@ApiModel(value="com-rotanava-boot-system-module-dao-dd-SysUserAnnouncement")
@Data
@TableName(value = "sys_user_announcement")
public class SysUserAnnouncement implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Integer id;

    /**
     * 系统用户标识ID
     */
    @TableField(value = "sys_user_id")
    @ApiModelProperty(value="系统用户标识ID")
    private Integer sysUserId;

    /**
     * 系统通告编号
     */
    @TableField(value = "sys_announcement_id")
    @ApiModelProperty(value="系统通告编号")
    private Integer sysAnnouncementId;

    /**
     * 系统通告阅读状态:0-未读;1-已读
     */
    @TableField(value = "ann_read_flag")
    @ApiModelProperty(value="系统通告阅读状态:0-未读;1-已读")
    private Integer annReadFlag;

    /**
     * 系统通告阅读时间
     */
    @TableField(value = "ann_read_time")
    @ApiModelProperty(value="系统通告阅读时间")
    private Date annReadTime;

    private static final long serialVersionUID = 1L;
}