package com.rotanava.framework.exception.code;

import com.rotanava.framework.code.ErrorCode;

/**
 * 错误类型:第三方设备服务问题
 * 类别码 04
 *
 * @author: richenLi
 * @create: 2020-07-06 09:43
 **/
public interface DeviceErrorCode {
    ErrorCode DEVICE_ERROR_00 = new ErrorCode(130400, "无法连接到设备，请检查该设备IP是否正确");
    ErrorCode DEVICE_ERROR_01 = new ErrorCode(130401, "人脸下发失败");
    ErrorCode DEVICE_ERROR_02 = new ErrorCode(130402, "找不到该设备");
    ErrorCode DEVICE_ERROR_03 = new ErrorCode(130403, "aqara设备错误");
    ErrorCode DEVICE_ERROR_04 = new ErrorCode(130404, "此时修改可能会覆盖");
    ErrorCode DEVICE_ERROR_05 = new ErrorCode(130405, "该设备uuid为空");
    ErrorCode DEVICE_ERROR_06 = new ErrorCode(130406, "该uuid已重复");
    ErrorCode DEVICE_ERROR_07 = new ErrorCode(130407, "该断路器处于维修模式，请手动合闸");
    ErrorCode DEVICE_ERROR_08 = new ErrorCode(130408, "该设备暂无抓拍服务的产品功能，请等待后续更新");
    ErrorCode DEVICE_ERROR_09 = new ErrorCode(130409, "该设备没有父级NVR设备，暂不支持该功能");
    ErrorCode DEVICE_ERROR_10 = new ErrorCode(130410, "取回放失败，请检查设备是否在线及配置是否正确");
    ErrorCode DEVICE_ERROR_11 = new ErrorCode(130411, "分组无此设备");
    ErrorCode DEVICE_ERROR_12 = new ErrorCode(130412, "该设备暂无播放功能，请等待后续更新");
    ErrorCode DEVICE_ERROR_13 = new ErrorCode(130413, "设备取回放失败");
    ErrorCode DEVICE_ERROR_14 = new ErrorCode(130414, "设备同步人员失败");
    ErrorCode DEVICE_ERROR_15 = new ErrorCode(130415, "设备调用服务失败");
}
