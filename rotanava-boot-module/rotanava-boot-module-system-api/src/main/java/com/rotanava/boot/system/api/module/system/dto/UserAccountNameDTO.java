package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 用户帐户名dto
 *
 * @author weiqiangmiao
 * @date 2021/12/08
 */
@Data
public class UserAccountNameDTO implements Serializable {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空！")
    private String userAccountName;


}
