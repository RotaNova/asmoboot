package com.rotanava.boot.system.module.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.OpenApiParam;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-19 14:48
 */
public interface OpenApiParamMapper extends BaseMapper<OpenApiParam> {

    int deleteByApiId(@Param("apiId")Integer apiId);

    List<OpenApiParam> findByApiIdAndType(@Param("apiId")Integer apiId,@Param("type")Integer type);


}