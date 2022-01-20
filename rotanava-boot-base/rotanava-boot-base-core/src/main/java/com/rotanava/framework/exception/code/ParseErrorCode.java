package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:转换错误码
 * 类别码 08
 * @author: richenLi
 * @create: 2020-07-06 09:52
 **/
public interface ParseErrorCode {

    ErrorCode PARSE_ERROR_00=new ErrorCode(110800,"时间格式转换失败");
}
