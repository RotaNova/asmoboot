package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 18:23
 */
@Data
public class SaveAnnouncementReceiveConfigDTO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    @NotNull
    private Integer sysAnnConfigId;

    @ApiModelProperty(value = "开关 0-不通知(关) 1-通知(开)")
    @NotNull
    @Range(min = 0, max = 1)
    private Integer noticeSwitch;

}