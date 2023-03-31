package com.rotanava.framework.config.dubbo.filter;

/**
 * 机构数据源工具类
 *
 * @author WeiQiangMiao
 * @date 2020/7/28
 */
public class UserSourceUtil {

    private static ThreadLocal<String> thread = new ThreadLocal<>();

    public static void setUserID(String userID) {
        thread.set(userID);
    }

    public static String getUserId() {
        return thread.get();
    }

    public static void remove(){
        thread.remove();
    }
}
