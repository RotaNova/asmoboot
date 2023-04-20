package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 钉钉机器人签证官
 *
 * @author weiqiangmiao
 * @date 2022/03/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingTalkRobotVO implements Serializable {

    /**
     * 钉钉机器人id
     */
    private Integer dingTalkRobotId;

    /**
     * 机器人名称
     */
    private String robotName;

    /**
     * Webhook地址
     */
    private String webhook;

    /**
     * 密钥
     */
    private String key;

}