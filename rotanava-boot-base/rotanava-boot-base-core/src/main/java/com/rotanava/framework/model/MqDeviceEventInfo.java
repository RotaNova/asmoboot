package com.rotanava.framework.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 设备事件
 * @author: richenLi
 * @date: 2021-08-05 16:46
 */
@Data
public class MqDeviceEventInfo implements Serializable {


    @ApiModelProperty("设备状态 online：上线。offline：离线。")
    private String status;

    @ApiModelProperty("设备在平台内的唯一标识。")
    private String iotId;


    @ApiModelProperty("设备所属产品的唯一标识")
    private String productKey;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("发送通知的时间点")
    private String time;

    @ApiModelProperty("事件类型")
    private String type;

    @ApiModelProperty("事件名")
    private String name;

    @ApiModelProperty("事件标识符")
    private String identifier;

    @ApiModelProperty("参数")
    private JSONObject value;



}