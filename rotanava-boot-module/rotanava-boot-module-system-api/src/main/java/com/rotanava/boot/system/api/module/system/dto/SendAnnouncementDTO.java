package com.rotanava.boot.system.api.module.system.dto;

import com.rotanava.boot.system.api.module.constant.AnnCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-02 17:41
 */
@Data
public class SendAnnouncementDTO implements Serializable {

    /**
     * 用户id
     */
    @NotNull
    private Integer sysUserId;

    @NotNull
    private AnnCategory annCategory;

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