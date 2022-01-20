package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.SysUserAnnouncementConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-01 16:59
 */
public interface SysUserAnnouncementConfigMapper extends BaseMapper<SysUserAnnouncementConfig> {

    SysUserAnnouncementConfig findAllBySysUserIdAndSysAnnConfigId(@Param("sysUserId") Integer sysUserId, @Param("sysAnnConfigId") Integer sysAnnConfigId);

    List<SysUserAnnouncementConfig> findBySysUserId(@Param("sysUserId") Integer sysUserId);

    List<Integer> findSysAnnConfigIdBySysUserId(@Param("sysUserId")Integer sysUserId);

    int deleteBySysUserId(@Param("sysUserId")Integer sysUserId);

    int deleteBySysAnnConfigId(@Param("sysAnnConfigId")Integer sysAnnConfigId);


}