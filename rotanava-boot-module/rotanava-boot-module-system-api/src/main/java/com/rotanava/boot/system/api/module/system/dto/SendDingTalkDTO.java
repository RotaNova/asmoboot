package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.boot.system.api.module.constant.AnnCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 发送钉钉信息
 *
 * @author weiqiangmiao
 * @date 2022/03/23
 */
@Data
public class SendDingTalkDTO implements Serializable {


    @NotNull
    private AnnCategory annCategory;

    /**
     * 配置id
     */
    @NotNull
    private Integer sysAnnConfigId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String abstractContent;

    /**
     * 消息内容
     */
    @NotBlank
    private String content;
}