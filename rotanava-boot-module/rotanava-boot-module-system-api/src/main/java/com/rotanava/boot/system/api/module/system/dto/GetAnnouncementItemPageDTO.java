package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.framework.common.aspect.annotation.Dict;
import com.rotanava.framework.model.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-15 17:30
 */
@Data
public class GetAnnouncementItemPageDTO extends BaseDTO {

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    private Integer annCategory;

}