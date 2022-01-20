package com.rotanava.framework.module.dao;

import com.rotanava.framework.model.SysMappingInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-12 14:55
 **/
@Mapper
public interface BaseCommonMapper {

    @Select("SELECT * FROM  sys_mapping_info WHERE `key` = #{key} ")
    SysMappingInfo getSysMappingInfoByKey(String key);

    @Insert("INSERT INTO sys_mapping_info (`key`,`value`,`create_time`) value (#{key},#{value},#{createTime})")
    void insert(SysMappingInfo sysMappingInfo);

}
