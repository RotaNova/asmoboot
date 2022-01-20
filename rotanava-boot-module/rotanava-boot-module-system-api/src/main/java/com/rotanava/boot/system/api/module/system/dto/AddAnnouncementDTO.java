package com.rotanava.boot.system.api.module.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-11 10:20
 */
@Data
public class AddAnnouncementDTO implements Serializable {

    /**
     * 系统通告标题
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "系统通告标题")
    private String annTitle;

    /**
     * 系统通告摘要信息
     */
    @NotBlank
    @Size(max = 30)
    @ApiModelProperty(value = "系统通告摘要信息")
    private String annMsgAbstract;

    /**
     * 系统通告内容
     */
    @ApiModelProperty(value = "系统通告内容")
    private String annContent;

    /**
     * 系统通告起始时间
     */
    @NotNull
    @ApiModelProperty(value = "系统通告起始时间")
    private Long annStartTime;

    /**
     * 系统通告结束时间
     */
    @ApiModelProperty(value = "系统通告结束时间")
    private Long annEndTime;

    /**
     * 系统通告优先级
     */
    @ApiModelProperty(value = "系统通告优先级")
    @NotNull
    private Integer annPriority;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    private Integer annTarget;

    /**
     * 指定用户的时候需要传 用户id
     */
    @ApiModelProperty(value = "指定用户的时候需要传 用户id")
    private Collection<Integer> sysUserIdList;

    /**
     * 指定部门的时候需要传 部门id
     */
    @ApiModelProperty(value = "指定部门的时候需要传 部门id")
    private Collection<Integer> sysDeptIdList;

    /**
     * 系统通告打开方式:0-跳转;1-新开页
     */
    @NotNull
    @ApiModelProperty(value = "系统通告打开方式:0-跳转;1-新开页")
    private Integer annOpenType;

    private Integer annConfigId;
}