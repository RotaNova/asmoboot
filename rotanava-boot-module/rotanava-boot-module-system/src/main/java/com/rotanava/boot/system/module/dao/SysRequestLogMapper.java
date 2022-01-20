package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysRequestLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysRequestLogMapper extends BaseMapper<SysRequestLog> {
}