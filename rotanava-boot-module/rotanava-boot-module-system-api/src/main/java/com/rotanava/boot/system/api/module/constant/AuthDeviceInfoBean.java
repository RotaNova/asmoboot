package com.rotanava.boot.system.api.module.constant;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-06-30 16:15
 **/
@Data
public class AuthDeviceInfoBean {

    private String cpuId;

    private String hardwareUUID;

    private List<String> diskSerialList;


}
