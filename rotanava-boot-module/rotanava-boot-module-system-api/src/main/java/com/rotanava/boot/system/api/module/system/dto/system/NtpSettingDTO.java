package com.rotanava.boot.system.api.module.system.dto.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-29 14:05
 **/
@Data
public class NtpSettingDTO {

    /**
     * ntp服务器地址
     */
    private String ntpAddress;

    /**
     * ntp服务器端口
     */
    private String ntpPort;

    /**
     * ntp校准间隔
     */
    private Integer ntpInterval;

    /**
     * ntp选项 1ntp校时 2手动校时
     */
    private Integer ntpOption;


    /**
     * 本地时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date localTime;


    /**
     * 系统时区
     */
    private String ntpTimeZone;
}
