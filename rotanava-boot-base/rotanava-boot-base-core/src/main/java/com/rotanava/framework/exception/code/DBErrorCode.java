package com.rotanava.framework.exception.code;


import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:数据库错误
 * 类别码 01
 */
public interface DBErrorCode {

    ErrorCode DB_ERROR_00 = new ErrorCode(100100, "找不到该数据");
    ErrorCode DB_ERROR_01 = new ErrorCode(100101, "该数据不能删除");
    ErrorCode DB_ERROR_02 = new ErrorCode(100102, "创建数据库失败");
    ErrorCode DB_ERROR_03 = new ErrorCode(100102, "找不到节假日数据");
    ErrorCode DB_ERROR_04 = new ErrorCode(100104, "删除数据库失败");
    ErrorCode DB_ERROR_05 = new ErrorCode(100105, "该状态下数据不能修改");
    ErrorCode DB_ERROR_06 = new ErrorCode(100106, "保存失败，请重试");
    ErrorCode DB_ERROR_07 = new ErrorCode(100107, "数据库备份失败");
    ErrorCode DB_ERROR_08 = new ErrorCode(100108, "找不到父级数据");
    ErrorCode DB_ERROR_09 = new ErrorCode(100109, "该数据重复");
    ErrorCode DB_ERROR_10 = new ErrorCode(100109, "禁止操作");
    ErrorCode DB_ERROR_11 = new ErrorCode(100109, "没有人员名单数据无法导出");
    ErrorCode DB_ERROR_12 = new ErrorCode(100112, "该数据无法删除，请先删除关联数据");
    ErrorCode DB_ERROR_13 = new ErrorCode(100113, "该驱动包不存在，请检查填写是否正确");
    ErrorCode DB_ERROR_14 = new ErrorCode(100114, "数据库连接异常，请检查是否正确");
    ErrorCode DB_ERROR_15 = new ErrorCode(100115, "sql执行异常，请联系接口提供者");
    ErrorCode DB_ERROR_16 = new ErrorCode(100116, "sql配置异常，请检查");


}
