package com.rotanava.framework.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: richenLi
 * @create: 2020-06-11 10:29
 **/
@Data
public class BaseVO<T> implements Serializable {


    /**
     * 总数
     */
    private Long total;


    /**
     * 时间数组
     */
    private String[] dataArray;


    /**
     * 时间戳数组
     */
    private Long[] timeStampArray;

    /**
     * 普通数据
     */
    private T data;


    /**
     * 列表数据
     */
    private List<T> records;



}
