package com.rotanava.boot.system.api.module.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 验证邮箱dto
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Data
public class VerifyMailboxDTO {

    /**
     * 邮箱
     */
    private String mailbox;
}
