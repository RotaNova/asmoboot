package com.rotanava.boot.system.api.module.system.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClusterTokenVO {


    /**
     * 访问令牌
     */
    private String access_token;

    /**
     * 令牌类型
     */
    private String token_type;

    /**
     * 刷新令牌
     */
    private String refresh_token;

    /**
     * 在到期
     */
    private Integer expires_in;
}
