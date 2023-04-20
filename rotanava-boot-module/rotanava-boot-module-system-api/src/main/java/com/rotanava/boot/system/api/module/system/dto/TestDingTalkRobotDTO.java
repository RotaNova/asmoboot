package com.rotanava.boot.system.api.module.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class TestDingTalkRobotDTO implements Serializable {

    /**
     * 配置id
     */
    @NotNull(message = "配置id不能为空")
    private Integer sysAnnConfigId;

    /**
     * 钉钉机器人id
     */
    @NotNull(message = "钉钉机器人id不能为空")
    Integer dingTalkRobotId;



}