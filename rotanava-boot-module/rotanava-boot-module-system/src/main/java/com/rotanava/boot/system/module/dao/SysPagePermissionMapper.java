package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-04 11:54
 */
@Mapper
public interface SysPagePermissionMapper extends BaseMapper<SysPagePermission> {

    /**
     * 更新页面删除状态通过id
     *
     * @param updatedPageDeleteStatus 更新页面删除状态
     * @param idCollection            id集合
     * @return int
     * @author WeiQiangMiao
     * @date 2021-03-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    int updatePageDeleteStatusByIdIn(@Param("updatedPageDeleteStatus") Integer updatedPageDeleteStatus, @Param("idCollection") Collection<Integer> idCollection);


    List<SysPagePermission> findAllByPageTypeInAndPageDeleteStatus(@Param("pageTypeCollection") Collection<Integer> pageTypeCollection, @Param("pageDeleteStatus") Integer pageDeleteStatus);

    List<SysPagePermission> findAllByPageTypeInAndPageDeleteStatusAndSysPageModuleTypeId(@Param("pageTypeCollection") Collection<Integer> pageTypeCollection, @Param("pageDeleteStatus") Integer pageDeleteStatus, @Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    /**
     * 找到系统的用户id
     *
     * @param sysUserId 系统的用户id
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-03-18
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findBySysUserId(@Param("sysUserId") Integer sysUserId, @Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    /**
     * 选择所有
     *
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> selectAllBySysPageModuleTypeId(@Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    /**
     * 找到系统页面权限
     *
     * @param queryWrapper 查询包装
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-04-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findSysPagePermissions(@Param(Constants.WRAPPER) QueryWrapper<SysPagePermission> queryWrapper);

    /**
     * 发现通过系统页面模块类型id
     *
     * @param sysPageModuleTypeId 系统页面模块类型id
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-04-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findBySysPageModuleTypeId(@Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);


    /**
     * 找到孩子
     *
     * @param idCollection id集合
     * @return {@link List<Integer> }
     * @author WeiQiangMiao
     * @date 2021-04-19
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<Integer> findChild(@Param("idCollection") Collection<String> idCollection);

    List<SysPagePermission> findAllBySysPageModuleTypeId(@Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    List<Integer> findIdByParentPageId(@Param("parentPageId") Integer parentPageId);

    void deleteBySysPageModuleTypeId(@Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    /**
     * 找到欢迎页面
     *
     * @return {@link List<SysPagePermission> }
     * @author weiqiangmiao
     * @date 2021/07/07
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findAdminWelcomePage();

    /**
     * 找到系统的用户id
     *
     * @param sysUserId 系统的用户id
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-03-18
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findWelcomePage(@Param("sysUserId") Integer sysUserId);

    /**
     * 发现通过系统页面模块类型id
     *
     * @param sysPageModuleTypeId 系统页面模块类型id
     * @return {@link List<SysPagePermission> }
     * @author WeiQiangMiao
     * @date 2021-04-16
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysPagePermission> findLinkPage(@Param("sysPageModuleTypeId") Integer sysPageModuleTypeId);

    SysPagePermission findByPageCode(@Param("pageCode") String pageCode);

    Integer findIdByPageUrl(@Param("pageUrl") String pageUrl);
}