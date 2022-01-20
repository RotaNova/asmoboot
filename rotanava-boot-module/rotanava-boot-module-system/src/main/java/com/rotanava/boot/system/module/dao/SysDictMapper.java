package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.framework.model.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-08 17:37
 **/
@Mapper
public interface SysDictMapper  extends BaseMapper<SysDict> {

    String queryDictTextByKey(@Param("code") String code, @Param("key") String key);


    String queryTableDictTextByKey(@Param("table") String table,@Param("text") String text,@Param("code") String code,@Param("key") String key);


    SysDict getSysDictByCode(String code);
}
