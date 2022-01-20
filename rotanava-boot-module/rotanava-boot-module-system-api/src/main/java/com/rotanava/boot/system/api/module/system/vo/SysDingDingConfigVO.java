package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-10-26 15:41
 */
@Data
public class SysDingDingConfigVO implements Serializable {

    private String agentId;

    private String appKey;

    private String appSecret;

    //https://open-dev.dingtalk.com/fe/app?spm=a2q3p.21071111.0.0.381f1cfa4aU590#/appMgr/inner/h5/1336191150/9
    private String aesKey;

    private String token;

    private String reqAddress;

}