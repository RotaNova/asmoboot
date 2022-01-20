package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.boot.system.api.module.constant.RecordMessageType;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-20 15:43
 */
@Data
public class RecordMessage implements Serializable {

    private String uid;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 消息
     */
    private String message;

    /**
     * 错误消息
     */
    private String errorMsg;

    private Boolean isLast;

    private RecordMessageType recordMessageType;
}