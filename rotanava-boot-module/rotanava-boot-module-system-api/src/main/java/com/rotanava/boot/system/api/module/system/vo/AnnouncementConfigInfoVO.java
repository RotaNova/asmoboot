package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.boot.system.api.module.system.bean.SysDepartmentLabel;
import com.rotanava.boot.system.api.module.system.bean.SysUserLabel;
import com.rotanava.framework.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-11 14:16
 */
@Data
public class AnnouncementConfigInfoVO implements Serializable {

    /**
     * 消息通知配置id
     */
    @ApiModelProperty(value = "消息通知配置id")
    private Integer sysAnnConfigId;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "类型名称  按名称进行分组排序")
    @Dict(dicCode = "ann_category")
    private Integer type;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    @Dict(dicCode = "annTarget")
    private Integer annTarget;

    /**
     * 系统通告通知用户名单信息
     */
    @ApiModelProperty(value = "指定用户")
    private List<SysUserLabel> sysUserLabels;

    @ApiModelProperty(value = "指定部门")
    private List<SysDepartmentLabel> sysDepartmentLabels;

}