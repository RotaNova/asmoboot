package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 更新密码dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-20
 */
@Data
public class UpdatePsdDTO {

    /**
     * 新密码
     */
    private String newPassword;

}
