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
    ErrorCode FILE_ERROR_08 = new ErrorCode(100306, "读取zip压缩包出错！");
    ErrorCode FILE_ERROR_16 = new ErrorCode(100306, "config.json文件缺失！");
    ErrorCode FILE_ERROR_10 = new ErrorCode(100308, "读取zip压缩包缺少必要文件！");
    ErrorCode FILE_ERROR_11 = new ErrorCode(100309, "文件不存在！");
    ErrorCode FILE_ERROR_12 = new ErrorCode(100310, "文件合并失败！");
    ErrorCode FILE_ERROR_13 = new ErrorCode(100311, "文件未上传完成！");
    ErrorCode FILE_ERROR_14 = new ErrorCode(100312, "文件上传token不存在！");
    ErrorCode FILE_ERROR_15 = new ErrorCode(100312, "错误的文件token！");
    ErrorCode FILE_ERROR_17 = new ErrorCode(100313, "压缩包内有中文路径！");
    ErrorCode FILE_ERROR_18 = new ErrorCode(100314, "文件token在非业务接口应用");

}
