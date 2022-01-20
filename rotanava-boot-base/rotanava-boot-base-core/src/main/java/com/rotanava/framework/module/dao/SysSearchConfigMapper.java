package com.rotanava.framework.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.framework.model.bo.SysSearchConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysSearchConfigMapper extends BaseMapper<SysSearchConfig> {

    @Select("SELECT * FROM sys_search_config WHERE search_code =  #{searchCode} ")
    List<SysSearchConfig> getConfigByPageId(String searchCode);

    @Select("SELECT * FROM sys_search_config ")
    List<SysSearchConfig> getAllConfig();

    @Select(" ${sql}  ")
    List<String> doSearchSql(String sql);

    @Select(" ${sql}  ")
    Long doSearchSqlCount(String sql);

    @Select(" ${sql}  ")
    List<Map> doSearchSqlByObject(Map ma);

    @Select(" ${sql}  ")
    int doDuplicateCheck(String sql);

    @Update(" ${sql}  ")
    Integer doUpdateSqlByObject(Map ma);

    @Select(" ${sql}  ")
    String doById(String sql);


}