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
public class AnnouncementInfoVO implements Serializable {

    /**
     * 系统通告编号
     */
    @ApiModelProperty(value = "系统通告编号")
    private Integer sysAnnoId;

    /**
     * 系统通告标题
     */
    @ApiModelProperty(value = "系统通告标题")
    private String annTitle;

    /**
     * 系统通告摘要信息
     */
    @ApiModelProperty(value = "系统通告摘要信息")
    private String annMsgAbstract;

    /**
     * 系统通告内容
     */
    @ApiModelProperty(value = "系统通告内容")
    private String annContent;

    /**
     * 系统通告对象类型:0-全体用户;1-指定用户;2-指定部门
     */
    @ApiModelProperty(value = "系统通告对象类型:0-全体用户;1-指定用户;2-指定部门")
    @Dict(dicCode = "annTarget")
    private Integer annTarget;

    /**
     * 系统通告打开方式:0-跳转;1-新开页
     */
    @ApiModelProperty(value = "系统通告打开方式:0-跳转;1-新开页")
    @Dict(dicCode = "annOpenType")
    private Integer annOpenType;

    /**
     * 系统通告起始时间
     */
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
    @Dict(dicCode = "annPriority")
    private Integer annPriority;

    /**
     * 系统通告发布状态:0-未发布;1-已发布;2-已撤销
     */
    @ApiModelProperty(value = "系统通告发布状态:0-未发布;1-已发布;2-已撤销")
    @Dict(dicCode = "ann_send_status")
    private Integer annSendStatus;

    /**
     * 系统通告类型:0-通知消息;1-系统消息;2-告警消息
     */
    @ApiModelProperty(value = "系统通告类型:0-通知消息;1-系统消息;2-告警消息")
    @Dict(dicCode = "ann_category")
    private Integer annCategory;


    /**
     * 系统通告创建人
     */
    @ApiModelProperty(value = "系统通告创建人")
    private String createBy;

    /**
     * 系统通告创建时间
     */
    @ApiModelProperty(value = "系统通告创建时间")
    private Long createTime;

    /**
     * 系统通告发布人
     */
    @ApiModelProperty(value = "系统通告发布人")
    private String annSender;

    /**
     * 系统通告发布时间
     */
    @ApiModelProperty(value = "系统通告发布时间")
    private Long annSendTime;

    /**
     * 系统通告撤销人
     */
    @ApiModelProperty(value = "系统通告撤销人")
    private String annCancel;

    /**
     * 系统通告撤销时间
     */
    @ApiModelProperty(value = "系统通告撤销时间")
    private Long annCancelTime;

    /**
     * 系统通告更新人
     */
    @ApiModelProperty(value = "系统通告更新人")
    private String updateBy;

    /**
     * 系统通告更新时间
     */
    @ApiModelProperty(value = "系统通告更新时间")
    private Long updateTime;

    /**
     * 系统通告通知用户名单信息
     */
    @ApiModelProperty(value = "指定用户")
    private List<SysUserLabel> sysUserLabels;

    @ApiModelProperty(value = "指定部门")
    private List<SysDepartmentLabel> sysDepartmentLabels;

}