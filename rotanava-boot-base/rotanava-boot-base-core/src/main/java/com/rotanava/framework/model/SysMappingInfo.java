package com.rotanava.framework.model;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-25 15:37
 **/
@Data
public class SysMappingInfo {

    private Integer id;

    private String key;

    private String value;

    private Date createTime;
}
