package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:调用第三方接口错误码
 * 类别码 07
 *
 * @author: richenLi
 * @create: 2020-07-06 09:52
 **/
public interface CallOtherErrorCode {
    ErrorCode CALLOTHER_ERROR_00 = new ErrorCode(120700, "连接第三方超时");
    ErrorCode CALLOTHER_ERROR_01 = new ErrorCode(120701, "无法准确解析数据");
    ErrorCode CALLOTHER_ERROR_02 = new ErrorCode(120702, "等待设备响应超时");
    ErrorCode CALLOTHER_ERROR_03 = new ErrorCode(120703, "当前设备状态可能已被改变");
}
