package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysAnnouncement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {

    List<Integer> findIdByAnnTitle(@Param("annTitle") String annTitle);

    List<Integer> findIdByAnnTitleAndAnnCategory(@Param("annTitle") String annTitle, @Param("annCategory") Integer annCategory);

    IPage<SysAnnouncement> queryPage(@Param(Constants.WRAPPER) QueryWrapper<SysAnnouncement> queryWrapper, IPage<SysAnnouncement> iPage);

    int updateAnnEndNullTimeById(@Param("id") Integer id);
}