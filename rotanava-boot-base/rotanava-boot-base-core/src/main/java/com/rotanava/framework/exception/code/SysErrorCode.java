package com.rotanava.framework.exception.code;


import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:系统错误码
 * 类别码 09
 *
 * @author: richenLi
 * @create: 2020-11-10 15:40
 **/
public interface SysErrorCode {

    ErrorCode SYS_ERROR_00 = new ErrorCode(110900, "该版本系统暂无此项功能，请联系管理员开通");
    ErrorCode SYS_ERROR_01 = new ErrorCode(110901, "私有服务器连接超时，请检查服务器是否开启");
    ErrorCode SYS_ERROR_02 = new ErrorCode(110902, "找不到云端app的dubbo引用");
    ErrorCode SYS_ERROR_03 = new ErrorCode(110903, "找不到本地的dubbo引用");
    ErrorCode SYS_ERROR_04 = new ErrorCode(110904, "message_local找不到对应的服务");
    ErrorCode SYS_ERROR_05 = new ErrorCode(110905, "无法连接互联网");
    ErrorCode SYS_ERROR_06 = new ErrorCode(110906, "minio初始化错误");
    ErrorCode SYS_ERROR_07 = new ErrorCode(110907, "短信发送失败，请检查手机号是否正确");
    ErrorCode SYS_ERROR_08 = new ErrorCode(110908, "请不要传递敏感字符,已记录此次操作");
    ErrorCode SYS_ERROR_09 = new ErrorCode(110909, "找不到该用户");
    ErrorCode SYS_ERROR_10 = new ErrorCode(110910, "无效鉴权信息");
    ErrorCode SYS_ERROR_11 = new ErrorCode(110911, "短信服务配置不完善，无法使用");

}
