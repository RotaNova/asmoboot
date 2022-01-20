package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-02 9:50
 */
@Data
public class AnnouncementReceiveConfigVO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    private Integer sysAnnConfigId;

    @ApiModelProperty(value = "开关 0-不通知(关) 1-通知(开)")
    private Integer noticeSwitch;

    @ApiModelProperty(value = "通知提醒项")
    private String configName;

    @ApiModelProperty(value = "通告类型")
    @Dict(dicCode = "ann_category")
    private Integer type;

}