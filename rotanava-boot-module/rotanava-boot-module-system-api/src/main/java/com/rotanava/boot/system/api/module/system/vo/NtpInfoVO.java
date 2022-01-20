package com.rotanava.boot.system.api.module.system.vo;

import com.rotanava.boot.system.api.module.system.dto.system.NtpSettingDTO;
import com.rotanava.framework.common.constant.enums.TimeZoneBean;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-29 14:46
 **/
@Data
public class NtpInfoVO {

    private List<TimeZoneBean> zoneList;

    private NtpSettingDTO ntpSetting;
}
