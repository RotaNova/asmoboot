package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-23 17:05
 */
@Data
public class AnnPublishMqMessage implements Serializable {

    @ApiModelProperty(value = "系统通告发布时间")
    private Long annSendTime;

    private SysAnnouncement sysAnnouncement;

}