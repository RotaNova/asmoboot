package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-26 14:22
 */
@Data
public class OpenAppApiDTO implements Serializable {

    @NotNull
    private Integer openApiId;
}