package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-07-06 15:59
 */
@Data
public class EnableAppDTO implements Serializable {

    /**
     * true开启 false关闭
     */
    @NotNull
    private Boolean isSwitch;

    /**
     * 开放应用id
     */
    @NotNull
    private Integer openAppId;

}