package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:文件错误
 * 类别码 03
 */
public interface FileErrorCode {


    ErrorCode FILE_ERROR_00 = new ErrorCode(100300, "不是图片格式");
    ErrorCode FILE_ERROR_01 = new ErrorCode(100301, "大于3000条数据");
    ErrorCode FILE_ERROR_02 = new ErrorCode(100302, "上传错误");
    ErrorCode FILE_ERROR_03 = new ErrorCode(100303, "excel导出错误");
    ErrorCode FILE_ERROR_04 = new ErrorCode(100304, "图片错误");
    ErrorCode FILE_ERROR_05 = new ErrorCode(100305, "图片大小不能超过2M");
    ErrorCode FILE_ERROR_06 = new ErrorCode(100305, "获取错误导入文件模板出错");
    ErrorCode FILE_ERROR_07 = new ErrorCode(100305, "获取错误导出文件模板出错");

}
