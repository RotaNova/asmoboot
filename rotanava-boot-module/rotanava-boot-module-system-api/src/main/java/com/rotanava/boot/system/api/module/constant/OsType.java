package com.rotanava.boot.system.api.module.constant;

/**
 * 操作系统类型
 *
 * @author WeiQiangMiao
 * @date 2021-04-06
 */
public enum  OsType {

   // [Android 3.x Tablet, Mac OS X, Unknown, Linux, Mac OS X (iPhone), Windows Vista, Android 2.x, Windows 7, Windows, Mac OS X (iPad), Android]

    ANDROID_TABLET("Android 3.x Tablet","安卓手机"),
    MAC_OS_X("Mac OS X","苹果电脑"),
    LINUX("Linux","Linux"),
    IPHONE("Mac OS X (iPhone)","苹果手机"),
    WINDOWS_VISTA("Windows Vista","电脑"),
    ANDROID2X("Android 2.x","安卓手机"),
    WINDOws7("Windows 7","电脑"),
    IPAD("Mac OS X (iPad)","苹果平板"),
    ANDROID("Android","安卓手机"),
    WINDOWS("Windows","电脑"),
    UNKNOWN("Unknown","其他"),

    ;
    private String os;
    private String description;

    OsType(String os, String description) {
        this.os = os;
        this.description = description;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static OsType findByOs(String os) {

        OsType[] enums = OsType.values();
        for (OsType osType : enums) {
            if (osType.getOs().equals(os)) {
                return osType;
            }
        }
        return OsType.UNKNOWN;
    }
}
