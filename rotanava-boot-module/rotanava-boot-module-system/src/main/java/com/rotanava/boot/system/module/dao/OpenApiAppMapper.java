package com.rotanava.boot.system.module.dao;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.OpenApiApp;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 14:48
 */
public interface OpenApiAppMapper extends BaseMapper<OpenApiApp> {

    int deleteByApiId(@Param("apiId") Integer apiId);

    Integer countByAppIdAndApiId(@Param("appId")Integer appId,@Param("apiId")Integer apiId);

    int deleteByApiIdInAndAppId(@Param("apiIdCollection")Collection<Integer> apiIdCollection,@Param("appId")Integer appId);


}