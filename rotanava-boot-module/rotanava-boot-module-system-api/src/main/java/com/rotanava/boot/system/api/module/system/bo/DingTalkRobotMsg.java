package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "ding_talk_robot_msg")
public class DingTalkRobotMsg implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 机器人id
     */
    @TableField(value = "robot_id")
    private Integer robotId;


    /**
     * 消息体
     */
    @TableField(value = "msg_text")
    private String msgText;


    /**
     * 标题
     */
    @TableField(value = "msg_title")
    private String msgTitle;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}
