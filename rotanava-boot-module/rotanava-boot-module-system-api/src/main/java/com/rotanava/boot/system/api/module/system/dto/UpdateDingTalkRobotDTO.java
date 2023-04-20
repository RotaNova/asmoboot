package com.rotanava.boot.system.api.module.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UpdateDingTalkRobotDTO implements Serializable {

    /**
     * 配置id
     */
    @NotNull(message = "配置id不能为空")
    private Integer sysAnnConfigId;

    /**
     * 钉钉机器人id
     */
    @NotNull(message = "钉钉机器人id不能为空")
    private Integer dingTalkRobotId;

    /**
     * 机器人名称
     */
    @NotBlank(message = "机器人名称不能为空")
    @Length(max = 12,message = "不能超过12个字符")
    private String robotName;

    /**
     * Webhook地址
     */
    @NotBlank(message = "Webhook地址不能为空")
    private String webhook;

    /**
     * 密钥
     */
    private String key;

}