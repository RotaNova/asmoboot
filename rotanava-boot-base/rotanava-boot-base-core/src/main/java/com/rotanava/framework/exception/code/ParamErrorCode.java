package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:参数错误
 * 类别码 02
 */
public interface ParamErrorCode {
    ErrorCode PARAM_ERROR_00 = new ErrorCode(100200, "id不能为空");
    ErrorCode PARAM_ERROR_01 = new ErrorCode(100201, "手机号不能为空");
    ErrorCode PARAM_ERROR_02 = new ErrorCode(100202, "手机号格式错误");
    ErrorCode PARAM_ERROR_03 = new ErrorCode(100203, "验证码错误");
    ErrorCode PARAM_ERROR_04 = new ErrorCode(100204, "验证码错误");
    ErrorCode PARAM_ERROR_05 = new ErrorCode(100205, "缺少相关字段");
    ErrorCode PARAM_ERROR_06 = new ErrorCode(100206, "时间不能大于现在的时间");
    ErrorCode PARAM_ERROR_07 = new ErrorCode(100207, "缺少有效的组织id");
    ErrorCode PARAM_ERROR_08 = new ErrorCode(100208, "重复的机构码");
    ErrorCode PARAM_ERROR_09 = new ErrorCode(100209, "机构因冻结、过期等原因无效！请联系管理员！");
    ErrorCode PARAM_ERROR_10 = new ErrorCode(100210, "至少要有一个如果条件和一个执行条件");
    ErrorCode PARAM_ERROR_11 = new ErrorCode(100211, "绿米开关设备没有这个开关");
    ErrorCode PARAM_ERROR_12 = new ErrorCode(100212, "参数错误请检查");
    ErrorCode PARAM_ERROR_13 = new ErrorCode(100213, "智慧用电策略缺少执行策略值");
    ErrorCode PARAM_ERROR_14 = new ErrorCode(100214, "人员标签不能为空");
    ErrorCode PARAM_ERROR_15 = new ErrorCode(100210, "参数错误");
    ErrorCode PARAM_ERROR_16 = new ErrorCode(100211, "已有相同分组名称");
    ErrorCode PARAM_ERROR_17 = new ErrorCode(100212, "时间填写不符合规定请修改");
    ErrorCode PARAM_ERROR_18 = new ErrorCode(100213, "工作时长范围1-1440分钟");
    ErrorCode PARAM_ERROR_19 = new ErrorCode(100214, "手机号已存在");
    ErrorCode PARAM_ERROR_20 = new ErrorCode(100215, "班次名称重复");
    ErrorCode PARAM_ERROR_21 = new ErrorCode(100216, "考勤组名称重复");
    ErrorCode PARAM_ERROR_22 = new ErrorCode(100217, "工作时长不能大于今日");
    ErrorCode PARAM_ERROR_23 = new ErrorCode(100218, "当前无法打卡");
    ErrorCode PARAM_ERROR_24 = new ErrorCode(100219, "缺少分组名称");
    ErrorCode PARAM_ERROR_25 = new ErrorCode(100220, "缺少负责人");
    ErrorCode PARAM_ERROR_26 = new ErrorCode(100221, "缺少联系方式");
    ErrorCode PARAM_ERROR_27 = new ErrorCode(100222, "缺少有效日期");
    ErrorCode PARAM_ERROR_28 = new ErrorCode(100223, "缺少分组名称");
    ErrorCode PARAM_ERROR_29 = new ErrorCode(100224, "有效日期格式不正确");
    ErrorCode PARAM_ERROR_30 = new ErrorCode(100225, "分组名称重复");
    ErrorCode PARAM_ERROR_31 = new ErrorCode(100226, "分组名称 与 上级分组名称 不匹配");
    ErrorCode PARAM_ERROR_32 = new ErrorCode(100232, "批量名单上传解析错误");
    ErrorCode PARAM_ERROR_33 = new ErrorCode(100233, "请至少搜索一个内容");
    ErrorCode PARAM_ERROR_34 = new ErrorCode(100234, "请选择下拉框内容");
    ErrorCode PARAM_ERROR_35 = new ErrorCode(100235, "上下班时间要大于等于1小时");
    ErrorCode PARAM_ERROR_36 = new ErrorCode(100236, "区间要大于等于30分钟");
    ErrorCode PARAM_ERROR_37 = new ErrorCode(100237, "密码强度不符合,密码强度限制为3种字符,最短8位长度,最长32位长度");
    ErrorCode PARAM_ERROR_38 = new ErrorCode(100238, "页面类型不能为空");
    ErrorCode PARAM_ERROR_39 = new ErrorCode(100239, "页面模块类型不能为空");
    ErrorCode PARAM_ERROR_40 = new ErrorCode(100240, "负责部门不在用户所属部门中");
    ErrorCode PARAM_ERROR_41 = new ErrorCode(100241, "旧密码不正确");
    ErrorCode PARAM_ERROR_42 = new ErrorCode(100242, "编码重复");
    ErrorCode PARAM_ERROR_43 = new ErrorCode(100243, "用户邮件为空,请绑定邮箱,不要调用验证邮箱");
    ErrorCode PARAM_ERROR_44 = new ErrorCode(100244, "验证码错误");
    ErrorCode PARAM_ERROR_45 = new ErrorCode(100245, "用户手机号为空,请绑定手机号,不要调用验证手机号");
    ErrorCode PARAM_ERROR_46 = new ErrorCode(100246, "验证码不存在");
    ErrorCode PARAM_ERROR_47 = new ErrorCode(100240, "旧密码不正确");
    ErrorCode PARAM_ERROR_48 = new ErrorCode(100240, "编码重复");
    ErrorCode PARAM_ERROR_49 = new ErrorCode(100240, "缺少父id");
    ErrorCode PARAM_ERROR_50 = new ErrorCode(100240, "无法删除当前登陆账号,当前账号在登陆");
    ErrorCode PARAM_ERROR_51 = new ErrorCode(100251, "状态错误");
    ErrorCode PARAM_ERROR_52 = new ErrorCode(100252, "验证码已失效");
    ErrorCode PARAM_ERROR_53 = new ErrorCode(100252, "缺少deptId");
    ErrorCode PARAM_ERROR_54 = new ErrorCode(100252, "选择的人员不属于当前部门下的人员");
    ErrorCode PARAM_ERROR_55 = new ErrorCode(100255, "验证码已失效,请重新获取");
    ErrorCode PARAM_ERROR_56 = new ErrorCode(100256, "手机号错误,请重新输入");
    ErrorCode PARAM_ERROR_57 = new ErrorCode(100257, "邮箱错误,请重新输入");
    ErrorCode PARAM_ERROR_58 = new ErrorCode(100258, "应用名称重复");
    ErrorCode PARAM_ERROR_59 = new ErrorCode(100259, "API Path重复");
    ErrorCode PARAM_ERROR_60 = new ErrorCode(100260, "文件错误");
    ErrorCode PARAM_ERROR_61 = new ErrorCode(100261, "页面已存在");
    ErrorCode PARAM_ERROR_62 = new ErrorCode(100261, "设备编码重复");
    ErrorCode PARAM_ERROR_63 = new ErrorCode(100261, "设备序号重复");
    ErrorCode PARAM_ERROR_64 = new ErrorCode(100263, "不能填写非底层属性");
    ErrorCode PARAM_ERROR_65 = new ErrorCode(100264, "不能勾选非数字属性");
    ErrorCode PARAM_ERROR_66 = new ErrorCode(100265, "设备分组编码重复");
    ErrorCode PARAM_ERROR_89 = new ErrorCode(100265, "设备分组名称重复");
    ErrorCode PARAM_ERROR_67 = new ErrorCode(100266, "参数为空");
    ErrorCode PARAM_ERROR_68 = new ErrorCode(100267, "修改处理状态不是'处理中'的数据");
    ErrorCode PARAM_ERROR_69 = new ErrorCode(100267, "系统规则无法删除");
    ErrorCode PARAM_ERROR_70 = new ErrorCode(100268, "这个设备不是在线状态无法操作");
    ErrorCode PARAM_ERROR_71 = new ErrorCode(100267, "时间参数错误");
    ErrorCode PARAM_ERROR_72 = new ErrorCode(100267, "策略名称重复");
    ErrorCode PARAM_ERROR_73 = new ErrorCode(100267, "策略编码重复");
    ErrorCode PARAM_ERROR_74 = new ErrorCode(100267, "场景名称重复");
    ErrorCode PARAM_ERROR_75 = new ErrorCode(100267, "场景编码重复");
    ErrorCode PARAM_ERROR_76 = new ErrorCode(100267, "报警名称重复");
    ErrorCode PARAM_ERROR_77 = new ErrorCode(100267, "报警编码重复");
    ErrorCode PARAM_ERROR_78 = new ErrorCode(100267, "设备和所选设备对不上");
    ErrorCode PARAM_ERROR_80 = new ErrorCode(100268, "只能批量处理'待处理'状态");
    ErrorCode PARAM_ERROR_79 = new ErrorCode(100267, "比较模式有误");

    ErrorCode PARAM_ERROR_81 = new ErrorCode(100269, "统一社会代码,如果无统一社会信用代码，则用组织机构代码，与企业名称必传一个");
    ErrorCode PARAM_ERROR_82 = new ErrorCode(100270, "不存在该企业信息");
    ErrorCode PARAM_ERROR_83 = new ErrorCode(100271, "已存在该企业信息");
    ErrorCode PARAM_ERROR_84 = new ErrorCode(100272, "不存在该项目信息");
    ErrorCode PARAM_ERROR_85 = new ErrorCode(100273, "已存在该项目信息");
    ErrorCode PARAM_ERROR_86 = new ErrorCode(100268, "人员已经存在");
    ErrorCode PARAM_ERROR_97 = new ErrorCode(100268, "不能删除最高管理员");

    //83 84 85 86 87

//    ErrorCode PARAM_ERROR_83 = new ErrorCode(100267, "groupParentId不存在");
//    ErrorCode PARAM_ERROR_84 = new ErrorCode(100267, "分组名称重复");
//    ErrorCode PARAM_ERROR_85 = new ErrorCode(100267, "分组编码重复");
//    ErrorCode PARAM_ERROR_86 = new ErrorCode(100267, "人员属性名称重复");
//    ErrorCode PARAM_ERROR_87 = new ErrorCode(100267, "姓名重复");
//    ErrorCode PARAM_ERROR_88 = new ErrorCode(100267, "编号重复");
//    ErrorCode PARAM_ERROR_86= new ErrorCode(100267, "设备不是在线状态无法修改设备名称");
//    ErrorCode PARAM_ERROR_87 = new ErrorCode(100267, "缺少calculationType");

    ErrorCode PARAM_ERROR_183 = new ErrorCode(100267, "groupParentId不存在");
    ErrorCode PARAM_ERROR_184 = new ErrorCode(100267, "分组名称重复");
    ErrorCode PARAM_ERROR_185 = new ErrorCode(100267, "分组编码重复");
    ErrorCode PARAM_ERROR_186 = new ErrorCode(100267, "设备不是在线状态无法修改设备名称");
    ErrorCode PARAM_ERROR_187 = new ErrorCode(100267, "缺少calculationType");
    ErrorCode PARAM_ERROR_188 = new ErrorCode(100267, "编号重复");
    ErrorCode PARAM_ERROR_189 = new ErrorCode(100267, "找不到图片");
    ErrorCode PARAM_ERROR_190 = new ErrorCode(100267, "编号为空");
    ErrorCode PARAM_ERROR_191 = new ErrorCode(100267, "标签名称重复");
    ErrorCode PARAM_ERROR_192 = new ErrorCode(100267, "人员数过高");
    ErrorCode PARAM_ERROR_193 = new ErrorCode(100267, "找不到图片");
    ErrorCode PARAM_ERROR_194 = new ErrorCode(100267, "找不到该人员");
    ErrorCode PARAM_ERROR_195 = new ErrorCode(100267, "不能关联自己哟!");
    ErrorCode PARAM_ERROR_196 = new ErrorCode(100267, "起始时间不存在!");
    ErrorCode PARAM_ERROR_197 = new ErrorCode(100267, "该组下有人无法删除！");

    ErrorCode PARAM_ERROR_198 = new ErrorCode(100268, "白名单已存在车辆！");
    ErrorCode PARAM_ERROR_199 = new ErrorCode(100268, "黑名单已存在车辆！");
    ErrorCode PARAM_ERROR_200 = new ErrorCode(100268, "车辆类型不存在！");
    ErrorCode PARAM_ERROR_201 = new ErrorCode(100268, "车型类型不存在！");
    ErrorCode PARAM_ERROR_202 = new ErrorCode(100268, "起始有效期格式错误！");
    ErrorCode PARAM_ERROR_203 = new ErrorCode(100268, "结束有效期格式错误！");

    ErrorCode PARAM_ERROR_87 = new ErrorCode(100269, "数据已存在");
    ErrorCode PARAM_ERROR_88 = new ErrorCode(100270, "是否自动生成代码为空");


    //考勤
    ErrorCode PARAM_ERROR_401 = new ErrorCode(100268, "钉钉事件订阅配置参数错误！");

    ErrorCode PARAM_ERROR_91 = new ErrorCode(100271, "编码已存在");
    ErrorCode PARAM_ERROR_92 = new ErrorCode(100272, "字典已存在");
    ErrorCode PARAM_ERROR_93 = new ErrorCode(100273, "字典不存在");
    ErrorCode PARAM_ERROR_94 = new ErrorCode(100274, "字典数据已存在");
    ErrorCode PARAM_ERROR_95 = new ErrorCode(100274, "计算错误");
    ErrorCode PARAM_ERROR_96= new ErrorCode(100296, "找不到该数据");
    ErrorCode PARAM_ERROR_101= new ErrorCode(1002101, "请正确填写抽帧率");
    ErrorCode PARAM_ERROR_102= new ErrorCode(1002102, "可信阈值不能为空");
    ErrorCode PARAM_ERROR_103= new ErrorCode(1002103, "可信阈值范围在0-100");
    ErrorCode PARAM_ERROR_104= new ErrorCode(100275, "Ai应用中心算法编码已存在！");
    ErrorCode PARAM_ERROR_105= new ErrorCode(100276, "算法编码为空！");
    ErrorCode PARAM_ERROR_106= new ErrorCode(100277, "服务完整请求地址 和 请求路径 都为空 ,必填其中一项！");
    ErrorCode PARAM_ERROR_107= new ErrorCode(100278, "事件输出图片截取类型必填其中一项！");
    ErrorCode PARAM_ERROR_108= new ErrorCode(100279, "区域验证必填其中一项！");
    ErrorCode PARAM_ERROR_109= new ErrorCode(100280, "图片流定义或视频流定义必填其中一项！");
    ErrorCode PARAM_ERROR_110= new ErrorCode(100281, "对应labelCom不存在！");
    ErrorCode PARAM_ERROR_111= new ErrorCode(100282, "计算类型错误！");
    ErrorCode PARAM_ERROR_112= new ErrorCode(100283, "公式错误！");
    ErrorCode PARAM_ERROR_113= new ErrorCode(100284, "持续时间类型错误！");
    ErrorCode PARAM_ERROR_114= new ErrorCode(100285, "图片参数类型错误！");
    ErrorCode PARAM_ERROR_115= new ErrorCode(100286, "视频参数类型错误！");
    ErrorCode PARAM_ERROR_116= new ErrorCode(100287, "自定义字段类型错误！");
    ErrorCode PARAM_ERROR_117= new ErrorCode(100287, "自定义字段类型为select情况下items必填！");
    ErrorCode PARAM_ERROR_118 = new ErrorCode(100288, "json转换错误！请检查json体！");
    ErrorCode PARAM_ERROR_119 = new ErrorCode(100289, "发布日期格式错误！");
    ErrorCode PARAM_ERROR_120= new ErrorCode(100290, "产品ai算法编码已存在！");
    ErrorCode PARAM_ERROR_121= new ErrorCode(100291, "数字类型必要最大值、最小值、长度");
    ErrorCode PARAM_ERROR_122= new ErrorCode(100292, "在docker都配置，镜像文件不能为空！");
    ErrorCode PARAM_ERROR_123= new ErrorCode(100293, "图片、视频参数 只能二选一！");
    ErrorCode PARAM_ERROR_124= new ErrorCode(100294, "计算类型为count/iou时，无法设置成target事件输出图片截取类型");
    ErrorCode PARAM_ERROR_125= new ErrorCode(100295, "报告信息为空，请检查");
    ErrorCode PARAM_ERROR_126= new ErrorCode(100295, "datasetsId不存在");
    ErrorCode PARAM_ERROR_127= new ErrorCode(100296, "datasetsId不存在");
    ErrorCode PARAM_ERROR_128= new ErrorCode(100297, "压缩包正在导入中！请勿导入！");
    ErrorCode PARAM_ERROR_129= new ErrorCode(100298, "category或images重复数据");
    ErrorCode PARAM_ERROR_130= new ErrorCode(100299, "数据集状态为未导入！");
    ErrorCode PARAM_ERROR_131= new ErrorCode(100300, "图片集合为空！");
    ErrorCode PARAM_ERROR_132= new ErrorCode(100300, "Images文件夹命名错误！请按照规范命名！");
    ErrorCode PARAM_ERROR_133= new ErrorCode(100300, "Annotations文件夹命名错误！请按照规范命名！");
    ErrorCode PARAM_ERROR_134= new ErrorCode(100301, "压缩包解压目录不存在！");
    ErrorCode PARAM_ERROR_135= new ErrorCode(100302, "图片id不存在！");
    ErrorCode PARAM_ERROR_136= new ErrorCode(100303, "标注状态不存在！");
    ErrorCode PARAM_ERROR_137= new ErrorCode(100304, "导出id不存在！");
    ErrorCode PARAM_ERROR_138= new ErrorCode(100305, "JSON格式错误！");
    ErrorCode PARAM_ERROR_139= new ErrorCode(100306, "XML格式错误！");
    ErrorCode PARAM_ERROR_140= new ErrorCode(100140, "部署失败，Ai服务不可用");
    ErrorCode PARAM_ERROR_141= new ErrorCode(100307, "只能填写bbox或mask！");
    ErrorCode PARAM_ERROR_142= new ErrorCode(100308, "category、category_id、score字段现阶段业务必须存在！");
    ErrorCode PARAM_ERROR_143= new ErrorCode(100309, "基础ai服务不能删除！");
    ErrorCode PARAM_ERROR_144= new ErrorCode(100309, "在identifier关键字不能填写：second、mask、area、credibilityThreshold");
}
