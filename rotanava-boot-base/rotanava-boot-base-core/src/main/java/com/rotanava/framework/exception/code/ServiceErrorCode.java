package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-09-06 14:11
 **/
public interface ServiceErrorCode {

    ErrorCode SERVICE_ERROR_00=new ErrorCode(112000,"暂无可用数据");
    ErrorCode SERVICE_ERROR_01=new ErrorCode(112001,"该状态不可部署");
    ErrorCode SERVICE_ERROR_02=new ErrorCode(112002,"服务方法不可用");
}
