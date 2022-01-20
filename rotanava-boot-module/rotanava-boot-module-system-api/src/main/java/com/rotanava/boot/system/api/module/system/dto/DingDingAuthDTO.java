package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-29 10:50
 */
@Data
public class DingDingAuthDTO implements Serializable {
    
    /**
     * 钉钉授权的code
     */
    @NotBlank
    private String code;
    
    /**
     * 登录的位置
     */
    private String loginLocation;
    
}