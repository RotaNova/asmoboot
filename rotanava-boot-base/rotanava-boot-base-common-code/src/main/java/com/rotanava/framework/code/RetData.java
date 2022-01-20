package com.rotanava.framework.code;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author richenli
 * @date 2020/8/10
 */
public class RetData<T> implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code = 200;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "如果是错误码 这边显示的是原因")
    private String msg;

    public RetData(T data) {

        this.data = data;
    }

    public RetData() {
    }

    public RetData(int code, T data, String msg) {

        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public RetData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 请求成功: 空参数返回 <br><br/>
     *
     * @return com.rotanava.face.common.model.RetData<T>
     * @return com.rotanava.face.common.model.RetData<T>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public static RetData<Void> ok() {
        return new RetData<>(200, null, "请求成功");
    }

    /**
     * 请求成功: 带参数返回 <br><br/>
     *
     * @param data 参数
     * @return com.rotanava.face.common.model.RetData<T>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public static <T> RetData<T> ok(T data) {
        return new RetData<>(200, data, "请求成功");
    }

    /**
     * 请求失败: 空参数返回 <br><br/>
     *
     * @return com.rotanava.face.common.model.RetData<java.lang.Void>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public static RetData<Void> error() {
        return new RetData<>(500, null, "请求失败");
    }

    /**
     * 请求失败: 带参数返回 <br><br/>
     *
     * @param data 参数
     * @return com.rotanava.face.common.model.RetData<T>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public static <T> RetData<T> error(T data) {
        return new RetData<>(500, data, "请求失败");
    }

    /**
     * 请求失败: 带参数返回 <br><br/>
     *
     * @param errorCode 错误代码
     * @return {@link RetData<T> }
     * @author WeiQiangMiao
     * @date 2021-06-02
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static <T> RetData<T> error(ErrorCode errorCode) {
        return new RetData<>(errorCode.getCode(), null, errorCode.getMsg());
    }

    /**
     * 设置报错信息 <br><br/>
     *
     * @param message 报错信息
     * @return com.rotanava.face.common.model.RetData<T>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public RetData<T> message(String message) {
        this.setMsg(message);
        return this;
    }

    /**
     * 设置状态码 <br><br/>
     *
     * @param code 状态码
     * @return com.rotanava.face.common.model.RetData<T>
     * @author WeiQiangMiao
     * @date 2020/8/10
     * @version 1.0.0
     */
    public RetData<T> code(Integer code) {
        this.setCode(code);
        return this;
    }


    public boolean isSuccess() {
        return code == 200;
    }
}
