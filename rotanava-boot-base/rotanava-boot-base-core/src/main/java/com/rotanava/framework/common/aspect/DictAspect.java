package com.rotanava.framework.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.aspect.annotation.Dict;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 字典aop类
 * @Author: dangzhenghui
 * @Date: 2019-3-17 21:50
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class DictAspect {

    @DubboReference
    private CommonApi commonApi;

    @Autowired
    private ConfigurableApplicationContext context;

    // 定义切点Pointcut
    @Pointcut("@annotation(com.rotanava.framework.common.aspect.annotation.AdviceResponseBody)")
    public void excudeService() {

    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     * sex:1,
     * sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     * customRender:function (text) {
     * if(text==1){
     * return "男";
     * }else if(text==2){
     * return "女";
     * }else{
     * return text;
     * }
     * }
     * 目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     *
     * @param result
     */
    private void parseDictText(Object result) {
        long begin = System.currentTimeMillis();
        if (result instanceof RetData) {
            List<Object> records;
            if (((RetData) result).getData() instanceof IPage) {

                records = ((IPage) ((RetData) result).getData()).getRecords();
                if (records != null) {
                    ((IPage) ((RetData) result).getData()).setRecords(fieldListParse(records));
                }
            } else if (((RetData) result).getData() instanceof BaseVO) {
                records = ((BaseVO) ((RetData) result).getData()).getRecords();
                BaseVO baseVO = ((BaseVO) ((RetData) result).getData());
                if (baseVO.getData() != null) {
                    baseVO.setData(fieldParse(baseVO.getData()));
                }
                if (records != null) {
                    ((BaseVO) ((RetData) result).getData()).setRecords(fieldListParse(records));
                }
            }

            //解析collection
            else if (((RetData) result).getData() instanceof Collection) {
                Collection data = ((Collection) ((RetData) result).getData());
                ((RetData) result).setData(fieldListParse(data));
            } else {

                Object data = ((RetData) result).getData();
                if (data != null && data.getClass().getName().contains("com.rotanava")) {
                    ((RetData) result).setData(fieldParse(data));
                }
            }
        }
        long end = System.currentTimeMillis();
        log.info("字典解析耗时{}ms",(end-begin));
    }

    public List<JSONObject> fieldListParse(Collection<Object> records) {

        List<JSONObject> items = new ArrayList<>();
        for (Object record : records) {
            items.add(fieldParse(record));
        }
        return items;
    }

    public JSONObject fieldParse(Object record) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{}";
        try {
            //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
            json = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            log.error("json解析失败" + e.getMessage(), e);
        }
        JSONObject item = JSONObject.parseObject(json);
        //update-begin--Author:scott -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
        //for (Field field : record.getClass().getDeclaredFields()) {
        for (Field field : getAllFields(record)) {
            //update-end--Author:scott  -- Date:20190603 ----for：解决继承实体字段无法翻译问题------
            if (field.getAnnotation(Dict.class) != null) {
                String code = field.getAnnotation(Dict.class).dicCode();
                String text = field.getAnnotation(Dict.class).dicText();
                String table = field.getAnnotation(Dict.class).dictTable();
                String key = String.valueOf(item.get(field.getName()));

                //翻译字典值对应的txt
                String textValue = translateDictValue(code, text, table, key);
                item.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
            }
            //date类型默认转换string格式化日期
            if ("java.util.Date".equals(field.getType().getName()) && field.getAnnotation(JsonFormat.class) == null && item.get(field.getName()) != null) {
                SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aDate.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
            }
        }
        return item;
    }

    /**
     * 功能: 翻译字段
     * 作者: zjt
     * 日期: 2021/7/7 11:41
     * 版本: 1.0
     */
    public String translateDictValue(String value, Dict dict) {
        String code = dict.dicCode();
        String text = dict.dicText();
        String table = dict.dictTable();

        //翻译字典值对应的txt
        return translateDictValue(code, text, table, value);
    }


    /**
     * 翻译字典文本
     *
     * @param code
     * @param text
     * @param table
     * @param key
     * @return
     */
    private String translateDictValue(String code, String text, String table, String key) {
        if (StringUtil.isNullOrEmpty(key)) {
            return null;
        }
        CommonApi bean = null;
        try {
            bean = context.getBean("sysBaseApiImpl", CommonApi.class);
        }catch (Exception e){

        }

        StringBuffer textValue = new StringBuffer();
        String[] keys = key.split(",");
        for (String k : keys) {
            String tmpValue = null;
            log.debug(" 字典 key : " + k);
            if (k.trim().length() == 0) {
                continue; //跳过循环
            }
            if (!StringUtils.isEmpty(table)) {
                log.debug("--DictAspect------dicTable=" + table + " ,dicText= " + text + " ,dicCode=" + code);
                tmpValue = commonApi.translateDictFromTable(table, text, code, k.trim());
            } else {
                if (bean!=null){
                    tmpValue = bean.translateDict(code,k.trim());
                }else {
                    tmpValue = commonApi.translateDict(code, k.trim());
                }
            }
            if (tmpValue != null) {
                if (!"".equals(textValue.toString())) {
                    textValue.append(",");
                }
                textValue.append(tmpValue);
            }

        }
        return textValue.toString();
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


}
