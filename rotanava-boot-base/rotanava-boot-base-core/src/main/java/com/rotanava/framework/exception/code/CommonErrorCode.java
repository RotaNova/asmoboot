package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:通用以及业务错误码
 * 类别码 06
 *
 * @author: richenLi
 * @create: 2020-07-06 09:52
 **/
public interface CommonErrorCode {
    ErrorCode COMMON_ERROR_00 = new ErrorCode(100600, "数据为空");
    ErrorCode COMMON_ERROR_01 = new ErrorCode(100601, "图片中没有人脸");
    ErrorCode COMMON_ERROR_02 = new ErrorCode(100602, "机构码有误，请联系管理员");
    ErrorCode COMMON_ERROR_03 = new ErrorCode(100603, "数据为空");
    ErrorCode COMMON_ERROR_04 = new ErrorCode(100604, "图片中没有人脸");
    ErrorCode COMMON_ERROR_05 = new ErrorCode(100605, "请确保该人员已经添加在宿舍");
    ErrorCode COMMON_ERROR_06 = new ErrorCode(100606, "机构码长度有误！4-6位");
    ErrorCode COMMON_ERROR_07 = new ErrorCode(100607, "只有冻结的机构才能解冻");
    ErrorCode COMMON_ERROR_08 = new ErrorCode(100608, "暂且只支持 authorization_code登陆");
    ErrorCode COMMON_ERROR_09 = new ErrorCode(100609, "单页数量过大");
    ErrorCode COMMON_ERROR_10 = new ErrorCode(100610, "页面资源不存在");
    ErrorCode COMMON_ERROR_11 = new ErrorCode(100611, "网址资源存在");
    ErrorCode COMMON_ERROR_12 = new ErrorCode(100612, "系统页面资源关联网址资源关联存在");
    ErrorCode COMMON_ERROR_13 = new ErrorCode(100613, "无法添加请检查");
    ErrorCode COMMON_ERROR_14 = new ErrorCode(100614, "设置的条件无法同时满足,请检查后修改!");
    ErrorCode COMMON_ERROR_15 = new ErrorCode(100615, "设置的名称重复,请修改!");
    ErrorCode COMMON_ERROR_16 = new ErrorCode(100616, "没有找到对应的数据库");
    ErrorCode COMMON_ERROR_17 = new ErrorCode(100617, "发送操作指令超时");
    ErrorCode COMMON_ERROR_18 = new ErrorCode(100212, "有个班次已经被删除请刷新");
    ErrorCode COMMON_ERROR_19 = new ErrorCode(100213, "这个班次正在使用");
    ErrorCode COMMON_ERROR_20 = new ErrorCode(100214, "这个设备已经添加过了");
    ErrorCode COMMON_ERROR_21 = new ErrorCode(100214, "已经有人员或者组被添加入其他考勤组了,请更换人员或者组");
    ErrorCode COMMON_ERROR_22 = new ErrorCode(100214, "只有待审核能进行修改");
    ErrorCode COMMON_ERROR_23 = new ErrorCode(100223, "发送短信失败");
    ErrorCode COMMON_ERROR_24 = new ErrorCode(100224, "当前打卡无效,请刷新");
    ErrorCode COMMON_ERROR_25 = new ErrorCode(100225, "该邮箱已被其他账号绑定,请尝试更换邮箱");
    ErrorCode COMMON_ERROR_26 = new ErrorCode(100226, "邮箱未绑定");
    ErrorCode COMMON_ERROR_27 = new ErrorCode(100227, "该手机号已被其他账号绑定,请尝试更换手机号");
    ErrorCode COMMON_ERROR_28 = new ErrorCode(100228, "手机号未绑定");
    ErrorCode COMMON_ERROR_29 = new ErrorCode(100229, "发送过于频繁,请等待60秒");
    ErrorCode COMMON_ERROR_30 = new ErrorCode(100230, "系统用户邮件为空");
    ErrorCode COMMON_ERROR_31 = new ErrorCode(100231, "系统用户手机号为空");
    ErrorCode COMMON_ERROR_32 = new ErrorCode(100232, "请选择指定部门");
    ErrorCode COMMON_ERROR_33 = new ErrorCode(100232, "已发布无法修改");
    ErrorCode COMMON_ERROR_34 = new ErrorCode(100232, "勾选了已发布的通告，发布失败");
    ErrorCode COMMON_ERROR_35 = new ErrorCode(100232, "勾选了未发布/已撤销的通告，撤销失败");
    ErrorCode COMMON_ERROR_36 = new ErrorCode(100232, "勾选了正常/过期的用户，解冻失败");
    ErrorCode COMMON_ERROR_37 = new ErrorCode(100232, "勾选了冻结/过期的用户，冻结失败");
    ErrorCode COMMON_ERROR_38 = new ErrorCode(100232, "sysPageId有误");
    ErrorCode COMMON_ERROR_39 = new ErrorCode(100232, "不能冻结自己");
    ErrorCode COMMON_ERROR_40 = new ErrorCode(100233, "productkey没有对应的产品");
    ErrorCode COMMON_ERROR_41 = new ErrorCode(100233, "通知名称重复");
    ErrorCode COMMON_ERROR_42 = new ErrorCode(100233, "不能修改该条");
    ErrorCode COMMON_ERROR_43 = new ErrorCode(100233, "数据发生变更请重新获取");
    ErrorCode COMMON_ERROR_44 = new ErrorCode(100233, "超出配额无法启用");
    ErrorCode COMMON_ERROR_45 = new ErrorCode(100234, "该账号无已绑定的邮箱或手机号，请联系管理员重置密码");
    ErrorCode COMMON_ERROR_46 = new ErrorCode(100501, "请输入正确的验证码");
    ErrorCode COMMON_ERROR_47 = new ErrorCode(100502, "图片验证码生成失败");
    ErrorCode COMMON_ERROR_48 = new ErrorCode(100503, "图片验证码已失效");
    ErrorCode COMMON_ERROR_49 = new ErrorCode(100249, "钉钉数据不一致,请重试！");
    ErrorCode COMMON_ERROR_50 = new ErrorCode(100250, "同步钉钉数据入库失败");
    ErrorCode COMMON_ERROR_51 = new ErrorCode(100250, "请配置钉钉配置数据！");
    ErrorCode COMMON_ERROR_52 = new ErrorCode(100250, "钉钉配置连接失败!");
    ErrorCode COMMON_ERROR_53 = new ErrorCode(100251, "AI能力未设置！无法启用！");
    ErrorCode COMMON_ERROR_54 = new ErrorCode(100252, "图片流或视频流未启动一项！无法启用！");
    ErrorCode COMMON_ERROR_55 = new ErrorCode(100253, "生效日期未设置！无法启用！");
    ErrorCode COMMON_ERROR_56 = new ErrorCode(100254, "抽帧率或置信度为空！无法启用！");
}
