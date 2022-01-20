package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 13:59
 */
@Mapper
public interface SysUserAnnouncementMapper extends BaseMapper<SysUserAnnouncement> {

    int deleteBySysAnnouncementId(@Param("sysAnnouncementId") Integer sysAnnouncementId);

    SysUserAnnouncement findBySysUserIdAndSysAnnouncementId(@Param("sysUserId") Integer sysUserId, @Param("sysAnnouncementId") Integer sysAnnouncementId);

    int updateAnnReadFlagBySysUserIdAndSysAnnouncementIdIn(@Param("updatedAnnReadFlag") Integer updatedAnnReadFlag, @Param("sysUserId") Integer sysUserId, @Param("sysAnnouncementIdCollection") Collection<Integer> sysAnnouncementIdCollection);

    int updateAnnReadFlagAndAnnReadTimeBySysUserId(@Param("updatedAnnReadFlag") Integer updatedAnnReadFlag, @Param("updatedAnnReadTime") Date updatedAnnReadTime, @Param("sysUserId") Integer sysUserId);

    List<Integer> findSysAnnouncementIdBySysUserId(@Param("sysUserId") Integer sysUserId);

    IPage<SysUserAnnouncement> queryAnnouncementList(@Param(Constants.WRAPPER) QueryWrapper<SysUserAnnouncement> queryWrapper, IPage<SysUser> iPage);

    int deleteBySysAnnouncementIdIn(@Param("sysAnnouncementIdCollection") Collection<Integer> sysAnnouncementIdCollection);

    List<Integer> findSysUserIdBySysAnnouncementId(@Param("sysAnnouncementId") Integer sysAnnouncementId);

    List<Integer> findSysUserIdBySysAnnouncementIdIn(@Param("sysAnnouncementIds") List<Integer> sysAnnouncementIds);
}