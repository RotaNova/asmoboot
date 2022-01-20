package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.OpenApi;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 14:48
 */
public interface OpenApiMapper extends BaseMapper<OpenApi> {

    @Select("SELECT a.* " +
            "FROM open_api AS  a " +
            "INNER JOIN open_api_app j ON a.id = j.api_id  WHERE a.api_path = #{path}  AND j.app_id = #{appId} AND a.request_method = #{requestMethod}")
    OpenApi getApiByRequest(@Param("path") String path
            , @Param("appId") Integer appId
            , @Param("requestMethod") Integer requestMethod);


    Integer countByApiPath(@Param("apiPath") String apiPath);

    IPage<OpenApi> queryPage(@Param(Constants.WRAPPER) QueryWrapper<OpenApi> queryWrapper, IPage<OpenApi> iPage);

    int updateSqlTextById(@Param("updatedSqlText")String updatedSqlText,@Param("id")Integer id);

    int updateDataSourceById(@Param("dataSourceId")Integer dataSourceId,@Param("id")Integer id);
}
