package com.rotanava.boot.system.api.module.constant;

/**
 * @description: 类型 1请求参数 2返回参数
 * @author: jintengzhou
 * @date: 2020-07-24 15:01
 */
public enum OpenApiParamType {

    //类型 1请求参数 2返回参数

    /**
     * 1请求参数
     */
    REQUEST_PARAMETERS(1),

    /**
     * 2返回参数
     */
    RETURN_PARAMETERS(2);

    private int type;

    OpenApiParamType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}