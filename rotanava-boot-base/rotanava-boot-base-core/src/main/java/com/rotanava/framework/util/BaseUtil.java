package com.rotanava.framework.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Component
public class BaseUtil implements ApplicationContextAware {


    private static ApplicationContext context;


    /**
     * 判断是linux系统还是其他系统
     * 如果是Linux系统，返回true，否则返回false
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
    }


    public static String getString(Object object) {
        if (StringUtil.isNullOrEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    /**
     * 将驼峰命名转化成下划线
     *
     * @param para
     * @return
     */
    public static String camelToUnderline(String para) {
        if (para.length() < 3) {
            return para.toLowerCase();
        }
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        //从第三个字符开始 避免命名不规范
        for (int i = 2; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 随机数对象
     *
     * @return
     */
    public static Random getRandom() {
        return ThreadLocalRandom.current();
    }


    public static String getUId() {
        return UUID.randomUUID().toString();
    }


    public static String getUIdImage() {
        StringBuffer sb = new StringBuffer();
        sb.append(getUId());
        sb.append(".jpg");
        return sb.toString();
    }


    public static Long getSnowflakeId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        return snowflakeIdWorker.nextId();
    }

    public static <T> T getBean(String cname, Class<T> cl) {
        try {
            if (context == null) {
                return null;
            }
            return cl.cast(context.getBean(cname));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }


    /**
     * map返回值的强转类型
     *
     * @param o
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T getMapValue(Object o, Class<T> c) {
        if (StringUtil.isNullOrEmpty(o)) {
            return null;
        }
        String strValue = String.valueOf(o);
        if (c == int.class || c == Integer.class) {
            return (T) Integer.valueOf(strValue);
        }
        if (c == double.class || c == Double.class) {
            return (T) Double.valueOf(strValue);
        }
        if (c == long.class || c == Long.class) {
            return (T) Long.valueOf(strValue);
        }
        if (c == Float.class || c == float.class) {
            return (T) Float.valueOf(strValue);
        }
        if (c == String.class) {
            return (T) strValue;
        }
        return (T) strValue;
    }


    public static ApplicationContext getContext() {
        return context;
    }


    public static HashMap<String, String[]> jsonToMap(JSONObject obj) {
        if (obj == null) {
            return null;
        }
        Map json = JSONObject.parseObject(obj.toJSONString(), Map.class);
        HashMap<String, String[]> data = new HashMap<>();

        for (Object key : json.keySet()) {
            Object value = json.get(key);
            JSONArray jsonArray = null;
            jsonArray = (JSONArray) value;
            String[] array = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                array[i] = jsonArray.getString(i);
            }
            data.put(String.valueOf(key), array);

        }
        return data;
    }

    public static String getRequestBodyStr(HttpServletRequest httpServletRequest) {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            return responseStrBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RedisUtil getRedisUtil() {
        return getBean("redisUtils", RedisUtil.class);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
