package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-31 10:13
 */
@Data
public class GetAnnouncementWinItemPageDTO extends BaseDTO {

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    private Integer annCategory;


}