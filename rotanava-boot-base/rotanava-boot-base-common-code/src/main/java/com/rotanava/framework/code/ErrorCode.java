package com.rotanava.framework.code;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-07-03 16:04
 **/
@Data
public class ErrorCode implements Serializable {
    //错误码
    private int code = 200;
    //返回信息
    private String msg;
    //英文消息 预留
    private String enMsg;

    public ErrorCode() {

    }

    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
