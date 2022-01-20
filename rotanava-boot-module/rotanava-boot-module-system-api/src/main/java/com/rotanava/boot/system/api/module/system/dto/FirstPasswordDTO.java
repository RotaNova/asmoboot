package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 密码登录dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-03
 */
@Data
public class FirstPasswordDTO implements Serializable {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空！")
    private String userAccountName;

    /**
     * 密码
     */
    @NotBlank(message = "旧密码不能为空！")
    private String oldUserPassword;

    /**
     * 密码
     */
    @NotBlank(message = "新密码不能为空！")
    private String newUserPassword;

}
