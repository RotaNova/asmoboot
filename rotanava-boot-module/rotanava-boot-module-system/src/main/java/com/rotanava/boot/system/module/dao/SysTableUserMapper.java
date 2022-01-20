package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysTableUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysTableUserMapper extends BaseMapper<SysTableUser> {
}
