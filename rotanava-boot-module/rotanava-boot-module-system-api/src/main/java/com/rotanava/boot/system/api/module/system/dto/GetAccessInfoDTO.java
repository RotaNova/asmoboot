package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 获得信息dto
 *
 * @author weiqiangmiao
 * @date 2021/07/07
 */
@Data
public class GetAccessInfoDTO{

    /**
     * 访问令牌
     */
    @NotBlank(message = "访问令牌不能为空")
    private String  accessToken;

    /**
     * 用户code
     */
    @NotBlank(message = "用户code不能为空")
    private String userCode;

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
