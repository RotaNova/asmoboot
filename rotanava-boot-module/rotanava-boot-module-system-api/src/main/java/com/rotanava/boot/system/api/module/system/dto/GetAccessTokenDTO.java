package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 获得访问令牌签证官
 *
 * @author weiqiangmiao
 * @date 2021/07/07
 */
@Data
public class GetAccessTokenDTO {
    /**
     * agentId
     */
    @NotBlank(message = "agentId不能为空")
    private String agentId;

    /**
     * appKey
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /**
     * appSecret
     */
    @NotBlank(message = "appSecret不能为空")
    private String appSecret;

    /**
     * 时间戳
     */
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

    /**
     * 签名
     */
    @NotBlank(message = "签名不能为空")
    private String signature;

}
