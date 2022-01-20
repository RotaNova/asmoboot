package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 本地mq事务消息表
 * @author: jintengzhou
 * @date: 2020-11-17 10:27
 */
@ApiModel(value = "com-rotanava-face-common-daoModel-MqTransactionalMessage")
@Data
public class MqTransactionalMessage implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 交换机
     */
    @ApiModelProperty(value = "交换机")
    private String exchange;

    /**
     * 路由建
     */
    @ApiModelProperty(value = "路由建")
    private String routingkey;

    /**
     * 消息提
     */
    @ApiModelProperty(value = "消息提")
    private String msgBody;

    /**
     * 状态 0未发送 1发送
     */
    @ApiModelProperty(value = "状态 0未发送 1发送")
    private Integer status;

    /**
     * 延迟时间  单位毫秒
     */
    @ApiModelProperty(value = "延迟时间 单位毫秒")
    private Long delayTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}