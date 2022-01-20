package com.rotanava.framework.util.excel.annotation;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelTag {
    /**
     * 表头
     */
    String tag();

    /**
     * 如果有个这个字段优先取这个匹配对应的列
     */
    int index() default -1;

    /**
     * 字体颜色
     */
    IndexedColors fontColor() default IndexedColors.BLACK;


    int width() default 1000;

}