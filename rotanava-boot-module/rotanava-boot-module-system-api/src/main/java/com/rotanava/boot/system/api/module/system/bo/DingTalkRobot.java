package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ding_talk_robot")
public class DingTalkRobot implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机器人名称
     */
    @TableField(value = "robot_name")
    private String robotName;

    /**
     * Webhook地址
     */
    @TableField(value = "webhook")
    private String webhook;

    /**
     * 密钥
     */
    @TableField(value = "`key`")
    private String key;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ROBOT_NAME = "robot_name";

    public static final String COL_WEBHOOK = "webhook";

    public static final String COL_KEY = "key";
}