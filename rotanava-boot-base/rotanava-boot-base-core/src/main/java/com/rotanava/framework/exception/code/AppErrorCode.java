package com.rotanava.framework.exception.code;


import com.rotanava.framework.code.ErrorCode;

/**
 * @description: app错误码 区间 20000 - 30000
 * @author: jintengzhou
 * @date: 2020-06-30 14:11
 */
public interface AppErrorCode {

    ErrorCode SMS_ALREADY_SEND = new ErrorCode(20001, "短信已经发送，请稍后再请求");
    ErrorCode LOGINERROR = new ErrorCode(20002, "账号或者密码错误");
    ErrorCode CAPTCHAERROR = new ErrorCode(20003, "验证码失效或者错误");
    ErrorCode ORGANIZATIONERROR = new ErrorCode(20004, "机构码无效");
    ErrorCode NOT_ORGANIZATION = new ErrorCode(20006, "当前人员没有这个机构！");
    ErrorCode HAVE_ORGANIZATION = new ErrorCode(20007, "这个机构已经创建过了请跳过这个步骤");
    ErrorCode NOT_THIS_ORGANIZATION = new ErrorCode(20008, "没有这个机构请检查!");
    ErrorCode CANT_CHANGE_PHOTO = new ErrorCode(20009, "已经认证无法修改照片!");
    ErrorCode MISSING_REQUIRED_FIELD = new ErrorCode(20010, "请填写相关的字段");
    ErrorCode NEW_AND_OLD_PASSWORDS_SAME_ERROR = new ErrorCode(20008, "新旧密码相同！");
    ErrorCode PHONE_NOT_EQUALS = new ErrorCode(20009, "手机号跟绑定的手机号不同！");
    ErrorCode PHONE_EQUALS_ERROR = new ErrorCode(20010, "新手机跟旧手机相同！");
    ErrorCode PHONE_ALREADY_EXISTS_ERROR = new ErrorCode(20011, "新手机号码已存在！");
    ErrorCode PHONE_THIRTY_DAYS_UPDATE_ERROR = new ErrorCode(20012, "手机号30天只能修改一次！");
    ErrorCode NOT_THIS_FILE_CERTIFICATE = new ErrorCode(20013, "没有这个文件凭证或者文件失效了 请重新上传");
    ErrorCode PERSONORGANIZATIONINFOID_ERROR = new ErrorCode(20014, "personOrganizationInfoId无效请检查");
    ErrorCode MISSING_PICTURE = new ErrorCode(20015, "缺少认证照片");
    ErrorCode CANNOT_BE_MODIFIED_AFTER_SUCCESSFUL_AUTHENTICATION = new ErrorCode(20016, "认证成功无法修改");
    ErrorCode THE_STATE_HAS_CHANGED = new ErrorCode(20017, "状态已经改变请刷新");
    ErrorCode WECHAT_HAS_ERROR = new ErrorCode(20018, "微信验权失败,请重试");
    ErrorCode BIND_WECHAT_INVALID = new ErrorCode(20019, "绑定微信失效请重新尝试");
    ErrorCode ACCOUNT_ABNORMAL = new ErrorCode(20020, "账号异常");
    ErrorCode HAS_BIND_WECHAT = new ErrorCode(20021, "这个账号已经绑定过微信");
    ErrorCode NOT_ORGANIZATION_PEOPLE = new ErrorCode(20022, "获取家庭失效无此家庭人员");
    ErrorCode FAILED_DORMITORY_LEAVE = new ErrorCode(20023, "请假失败 请确保pc端该人员有加入宿舍考勤");
    ErrorCode MISSING_LEAVE_TIME = new ErrorCode(20024, "缺少请假时间");
    ErrorCode NOT_OPERATE_LEAVE_AUTHORITY = new ErrorCode(20025, "没有操作改人员请假的权限");
    ErrorCode MISSING_FIELD = new ErrorCode(20026, "没有操作改人员请假的权限");
    ErrorCode NOT_NULL_PHONE_ERROR = new ErrorCode(20027, "手机号不存在");
    ErrorCode NULL_ORGANIZED_ERROR = new ErrorCode(20028, "您没有绑定机构");
    ErrorCode INCORRECT_NUMBER_OF_ORGANIZATION_ID = new ErrorCode(20029, "没有找到组织或者组织id有误");
    ErrorCode NO_SUCH_PERSON_IN_THE_DORMITORY = new ErrorCode(20030, "在PC端宿舍考勤请假没有查找到该人,请到pc端宿舍考勤添加该人员");
    ErrorCode CANTNOT_FIND_PERSON = new ErrorCode(20031, "找不到该用户");
    ErrorCode CURRENT_PERSONNEL_ARE_NOT_CERTIFIED = new ErrorCode(20032, "当前人员未认证无法使用该功能,请认证");
//    ErrorCode GET_UNIONID_FALSE = new ErrorCode(20033, "微信获取unionId失败 请重试保证授权该操作");
    ErrorCode GET_UNIONID_FALSE = new ErrorCode(20033, "网络开小差了,请重试!");
    ErrorCode NO_DOCKING_WITH_THIS_APP = new ErrorCode(20034, "暂无对接此应用");
    ErrorCode SIGNATURE_ERROR = new ErrorCode(20035, "签名错误");
    ErrorCode ACCESS_TOKEN_ERROR = new ErrorCode(20035, "访问令牌错误");
    ErrorCode WRONG_OR_INVALID_CODE = new ErrorCode(20035, "code错误或无效");

}