package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-29 10:35
 */
@Data
public class DdScanLoginParamVO implements Serializable {
    
     /**
      * 钉钉扫码appId 如果为空 提示用户 请配置钉钉扫码登陆
      */
    private String appId;
    
}