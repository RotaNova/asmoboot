package com.rotanava.boot.dbapi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-11-12 10:54
 **/
@Mapper
public interface DBApiMapper {

//    @Select(value = " ${sql}  ",databaseId = "mysql")
//    List<Map> doMysqlSqlByObject(Map ma);
//
//    @Select(value = " ${sql}  ",databaseId = "oracle")
//    List<Map> doOracleSqlByObject(Map ma);
}
