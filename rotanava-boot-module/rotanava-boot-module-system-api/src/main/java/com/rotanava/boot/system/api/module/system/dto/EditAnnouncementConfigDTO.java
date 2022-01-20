package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-07-07 18:13
 */
@Data
public class EditAnnouncementConfigDTO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    @NotNull
    private Integer sysAnnConfigId;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    @NotBlank
    private String configName;

    /**
     * 类型 0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value="0-通知消息;1-系统消息;2-告警消息")
    @NotNull
    @Min(value = 0)
    @Max(value = 2)
    private Integer type;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @NotNull
    @Range(min = 0, max = 2)
    private Integer annTarget;

    /**
     * 指定用户的时候需要传 用户id
     */
    private List<Integer> sysUserIdList;

    /**
     * 指定部门的时候需要传 部门id
     */
    private List<Integer> sysDeptIdList;
}