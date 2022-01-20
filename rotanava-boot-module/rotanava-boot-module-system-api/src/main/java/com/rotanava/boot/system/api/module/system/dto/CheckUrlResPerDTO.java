package com.rotanava.boot.system.api.module.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 检查网址DTO
 *
 * @author WeiQiangMiao
 * @date 2020/8/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckUrlResPerDTO implements Serializable {

    /**
     * 网址
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * token
     */
    private String token;

    public CheckUrlResPerDTO(String url, String requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }
}
