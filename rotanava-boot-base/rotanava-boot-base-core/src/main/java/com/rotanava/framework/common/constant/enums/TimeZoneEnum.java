package com.rotanava.framework.common.constant.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-29 14:23
 **/
public enum TimeZoneEnum {
    SHANGHAI("Asia/Shanghai","中国标准时间 (北京)"),
    HONG_KONG("Asia/Hong_Kong","香港时间 (香港)"),
    NEW_YORK("America/New_York","美国东部时间 (纽约)"),
    LONDON(" Europe/London","格林尼治标准时间 (伦敦)"),
    BANGKOK(" Asia/Bangkok","曼谷时间"),
    Tehran("Asia/Tehran","伊朗标准时间 (德黑兰)"),
    Nairobi("Africa/Nairobi","东部非洲标准时间 (内罗毕)")

    ;

    private String zoneValue;

    private String zoneText;


    TimeZoneEnum(String zoneValue, String zoneText) {
        this.zoneValue = zoneValue;
        this.zoneText = zoneText;
    }

    public String getZoneValue() {
        return zoneValue;
    }

    public void setZoneValue(String zoneValue) {
        this.zoneValue = zoneValue;
    }

    public String getZoneText() {
        return zoneText;
    }

    public void setZoneText(String zoneText) {
        this.zoneText = zoneText;
    }

    public static List<TimeZoneBean> getZoneList(){
        List<TimeZoneBean> list = new ArrayList<>();
        for (TimeZoneEnum value : values()) {
            TimeZoneBean timeZoneBean = new TimeZoneBean();
            timeZoneBean.setZoneText(value.zoneText);
            timeZoneBean.setZoneValue(value.zoneValue);
            list.add(timeZoneBean);
        }
        return list;
    }


}
