package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-06 10:21
 */
@Data
public class SendSysAnnouncementDTO implements Serializable {
    /**
     * 用户id
     */
    @NotNull
    private Integer sysUserId;

    /**
     * 消息id
     */
    @NotNull
    private Integer sysAnnoId;

}