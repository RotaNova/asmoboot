package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rotanava.boot.system.api.SysPageAndApiService;
import com.rotanava.boot.system.api.module.constant.PageDeleteStatus;
import com.rotanava.boot.system.api.module.constant.PageType;
import com.rotanava.boot.system.api.module.constant.SysPermissionType;
import com.rotanava.boot.system.api.module.constant.VisibleToAllType;
import com.rotanava.boot.system.api.module.system.bean.PermissionListAndId;
import com.rotanava.boot.system.api.module.system.bo.SysDepartPermission;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRole;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRolePermission;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import com.rotanava.boot.system.api.module.system.bo.SysRolePermission;
import com.rotanava.boot.system.api.module.system.vo.SysApiChooseVO;
import com.rotanava.boot.system.module.dao.SysDepartPermissionMapper;
import com.rotanava.boot.system.module.dao.SysDepartRoleMapper;
import com.rotanava.boot.system.module.dao.SysDepartRolePermissionMapper;
import com.rotanava.boot.system.module.dao.SysPagePermissionMapper;
import com.rotanava.boot.system.module.dao.SysRolePermissionMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-10 10:47
 */
@Service
@Log4j2
public class SysPageAndApiServiceImpl implements SysPageAndApiService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;

    @Autowired
    private SysDepartRolePermissionMapper sysDepartRolePermissionMapper;

    @Autowired
    private SysDepartPermissionMapper sysDepartPermissionMapper;

    @Autowired
    private SysPagePermissionMapper sysPagePermissionMapper;


    @Override
    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    public List<Tree<Integer>> getSysPageTree(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId) {
        List<SysPagePermission> sysPagePermissionList = sysPagePermissionMapper.findAllByPageTypeInAndPageDeleteStatusAndSysPageModuleTypeId(Lists.newArrayList(PageType.SUBMENU.getType(), PageType.A_MENU.getType())
                , PageDeleteStatus.NOT_DELETED.getStatus(), sysPageModuleTypeId);

        List<Integer> sysRolePermissionList = null;

        List<Integer> sysDepartRolePermissionList = null;

        List<Integer> sysDepartPermissionList = null;

        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
            sysRolePermissionList = sysRolePermissionMapper.findBySysRoleId(relationId).stream().map(SysRolePermission::getPageId).collect(Collectors.toList());
        }

        if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
            sysDepartRolePermissionList = sysDepartRolePermissionMapper.findByDepartRoleId(relationId).stream().map(SysDepartRolePermission::getPagePermissionId).collect(Collectors.toList());

            final SysDepartRole sysDepartRole = sysDepartRoleMapper.selectById(relationId);

            //本部门拥有的菜单
            final List<Integer> deptHavePageIdList = sysDepartPermissionMapper.findByDepartId(sysDepartRole.getSysDepartmentId()).stream().map(SysDepartPermission::getPagePermissionId).collect(Collectors.toList());

            sysPagePermissionList = sysPagePermissionList.stream().filter(sysPagePermission -> deptHavePageIdList.contains(sysPagePermission.getId()) || sysPagePermission.getVisibleToAll().equals(VisibleToAllType.VISIBLE.getType())).collect(Collectors.toList());

        }

        if (sysPermissionType == SysPermissionType.DEPT) {
            sysDepartPermissionList = sysDepartPermissionMapper.findByDepartId(relationId).stream().map(SysDepartPermission::getPagePermissionId).collect(Collectors.toList());
        }

        final List<Integer> finalSysRolePermissionList = sysRolePermissionList;
        final List<Integer> finalSysDepartRolePermissionList = sysDepartRolePermissionList;
        final List<Integer> finalSysDepartPermissionList = sysDepartPermissionList;


        return TreeUtil.build(sysPagePermissionList, 0, (sysPagePermission, treeNode) -> {
            final Integer sysPageId = sysPagePermission.getId();
            treeNode.setId(sysPageId);
            treeNode.setParentId(sysPagePermission.getParentPageId());
            treeNode.setName(sysPagePermission.getPageTitle());
            treeNode.setWeight(sysPagePermission.getSort());
            treeNode.putExtra("sysPageId", sysPageId);
            treeNode.putExtra("choose", false);
            treeNode.putExtra("visibleToAll", sysPagePermission.getVisibleToAll());

            if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
                if (finalSysRolePermissionList.contains(sysPageId)) {
                    treeNode.putExtra("choose", true);
                }
            }

            if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
                if (finalSysDepartRolePermissionList.contains(sysPageId)) {
                    treeNode.putExtra("choose", true);
                }
            }

            if (sysPermissionType == SysPermissionType.DEPT) {
                if (finalSysDepartPermissionList.contains(sysPageId)) {
                    treeNode.putExtra("choose", true);
                }
            }

        });

    }

    @Override
    @Transactional(rollbackFor = Throwable.class, readOnly = true)
    public List<SysApiChooseVO> getSysApiPermission(SysPermissionType sysPermissionType, int relationId, int sysPageId) {

        List<SysApiChooseVO> sysApiChooseList = Lists.newArrayList();

        //获取2-接口权限
        List<SysPagePermission> sysPagePermissionList = sysPagePermissionMapper.findAllByPageTypeInAndPageDeleteStatus(Lists.newArrayList(PageType.INTERFACE_PERMISSIONS.getType())
                , PageDeleteStatus.NOT_DELETED.getStatus());

        sysPagePermissionList = sysPagePermissionList.stream().filter(sysPagePermission -> sysPagePermission.getParentPageId() == sysPageId).collect(Collectors.toList());


        List<Integer> sysRolePermissionList = null;
        List<Integer> sysDepartRolePermissionList = null;
        List<Integer> sysDepartPermissionList = null;

        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
            sysRolePermissionList = sysRolePermissionMapper.findBySysRoleId(relationId).stream().map(SysRolePermission::getPageId).collect(Collectors.toList());
        }

        if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
            sysDepartRolePermissionList = sysDepartRolePermissionMapper.findByDepartRoleId(relationId).stream().map(SysDepartRolePermission::getPagePermissionId).collect(Collectors.toList());

            final SysDepartRole sysDepartRole = sysDepartRoleMapper.selectById(relationId);

            //本部门拥有的菜单
            final List<Integer> deptHavePageIdList = sysDepartPermissionMapper.findByDepartId(sysDepartRole.getSysDepartmentId()).stream().map(SysDepartPermission::getPagePermissionId).collect(Collectors.toList());
            sysPagePermissionList = sysPagePermissionList.stream().filter(sysPagePermission -> deptHavePageIdList.contains(sysPagePermission.getId()) || sysPagePermission.getVisibleToAll().equals(VisibleToAllType.VISIBLE.getType())).collect(Collectors.toList());
        }

        if (sysPermissionType == SysPermissionType.DEPT) {
            sysDepartPermissionList = sysDepartPermissionMapper.findByDepartId(relationId).stream().map(SysDepartPermission::getPagePermissionId).collect(Collectors.toList());
        }

        for (SysPagePermission sysPagePermission : sysPagePermissionList) {
            final Integer sysPagePermissionId = sysPagePermission.getId();
            final SysApiChooseVO sysApiChooseVO = new SysApiChooseVO();
            sysApiChooseVO.setAbilityType(sysPagePermission.getAbilityType());
            sysApiChooseVO.setPageTitle(sysPagePermission.getPageTitle());
            sysApiChooseVO.setSysPageId(sysPagePermissionId);
            sysApiChooseVO.setChoose(false);


            if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
                sysApiChooseVO.setChoose(sysRolePermissionList.contains(sysPagePermissionId));
            }

            if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
                sysApiChooseVO.setChoose(sysDepartRolePermissionList.contains(sysPagePermissionId));
            }

            if (sysPermissionType == SysPermissionType.DEPT) {
                sysApiChooseVO.setChoose(sysDepartPermissionList.contains(sysPagePermissionId));
            }

            sysApiChooseVO.setVisibleToAll(sysPagePermission.getVisibleToAll());

            sysApiChooseList.add(sysApiChooseVO);
        }

        return sysApiChooseList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSysPermission(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId, Set<Integer> sysPageIdList) {
        //获取所有的页面数据
        final List<SysPagePermission> sysPagePermissionList = sysPagePermissionMapper.findAllBySysPageModuleTypeId(sysPageModuleTypeId);
        //该模块下的所有页面id
        final List<Integer> originalSysPageIdList = sysPagePermissionList.stream().map(SysPagePermission::getId).collect(Collectors.toList());


        for (SysPagePermission sysPagePermission : sysPagePermissionList) {
            final boolean canRemove = sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType()) || sysPagePermission.getVisibleToAll() == VisibleToAllType.VISIBLE.getType();
            if (canRemove) {
                sysPageIdList.remove(sysPagePermission.getId());
            }
        }


        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {


//            List<Integer> pageIdBySysRoleId = sysRolePermissionMapper.findPageIdBySysRoleId(relationId);
//            sysRolePermissionMapper.deleteByPageIdInAndSysRoleId(originalSysPageIdList, relationId);


            //如果取消子节点接口全部取消
            //如果勾选  判断子节点是否全部没被选择 如果有一个被勾选则无效
            long begin = System.currentTimeMillis();
            dealWithChooseAndCancel(sysPermissionType, relationId, sysPageModuleTypeId, sysPageIdList);
            long end = System.currentTimeMillis();
            log.info("dealWithChooseAndCancel time={}ms",(end-begin));

            for (Integer sysPageId : getSysPageIdListAndParent(sysPageIdList)) {
                if (sysRolePermissionMapper.countBySysRoleIdAndPageId(relationId, sysPageId) == 0) {
                    final SysRolePermission sysRolePermission = new SysRolePermission();
                    sysRolePermission.setPageId(sysPageId);
                    sysRolePermission.setSysRoleId(relationId);
                    sysRolePermissionMapper.insert(sysRolePermission);
                }
            }
        } else if (sysPermissionType == SysPermissionType.DEPT_ROLE) {

//            List<Integer> pagePermissionIdByDepartRoleId = sysDepartRolePermissionMapper.findPagePermissionIdByDepartRoleId(relationId);
            sysDepartRolePermissionMapper.deleteByPagePermissionIdInAndDepartRoleId(originalSysPageIdList, relationId);


            //如果取消子节点接口全部取消
            //如果勾选  判断子节点是否全部没被选择 如果有一个被勾选则无效
            dealWithChooseAndCancel(sysPermissionType, relationId, sysPageModuleTypeId, sysPageIdList);

            for (Integer sysPageId : getSysPageIdListAndParent(sysPageIdList)) {
                if (sysDepartRolePermissionMapper.countByDepartRoleIdAndPagePermissionId(relationId, sysPageId) == 0) {
                    final SysDepartRolePermission sysDepartRolePermission = new SysDepartRolePermission();
                    sysDepartRolePermission.setDepartRoleId(relationId);
                    sysDepartRolePermission.setPagePermissionId(sysPageId);
                    sysDepartRolePermissionMapper.insert(sysDepartRolePermission);
                }
            }
        } else if (sysPermissionType == SysPermissionType.DEPT) {

            sysDepartPermissionMapper.deleteByPagePermissionIdInAndDepartId(originalSysPageIdList, relationId);

            //如果取消子节点接口全部取消
            //如果勾选  判断子节点是否全部没被选择 如果有一个被勾选则无效
            dealWithChooseAndCancel(sysPermissionType, relationId, sysPageModuleTypeId, sysPageIdList);

            final Set<Integer> sysPageIdListAndParent = getSysPageIdListAndParent(sysPageIdList);
            for (Integer sysPageId : sysPageIdListAndParent) {
                if (sysDepartPermissionMapper.countByDepartIdAndPagePermissionId(relationId, sysPageId) == 0) {
                    final SysDepartPermission sysDepartPermission = new SysDepartPermission();
                    sysDepartPermission.setDepartId(relationId);
                    sysDepartPermission.setPagePermissionId(sysPageId);
                    sysDepartPermissionMapper.insert(sysDepartPermission);
                }
            }

            //删除 关联的角色权限
            final List<SysDepartRolePermission> sysDepartRolePermissionList = sysDepartRolePermissionMapper.selectList(null);
            final List<SysPagePermission> sysPagePermissions = sysPagePermissionMapper.findAllBySysPageModuleTypeId(sysPageModuleTypeId);
            final Map<Integer, SysPagePermission> sysPagePermissionMap = sysPagePermissions.stream()
                    .collect(Collectors.toMap(SysPagePermission::getId, sysPagePermission -> sysPagePermission));

            for (SysDepartRolePermission sysDepartRolePermission : sysDepartRolePermissionList) {
                final Integer pagePermissionId = sysDepartRolePermission.getPagePermissionId();
                if (!sysPageIdListAndParent.contains(pagePermissionId)) {
                    final SysPagePermission sysPagePermission = sysPagePermissionMap.get(pagePermissionId);
                    if (sysPagePermission != null) {
                        if (PageType.INTERFACE_PERMISSIONS.getType().equals(sysPagePermission.getPageType())) {
                            //如果是接口权限 判断父级是否需要被删除
                            if (!sysPageIdListAndParent.contains(sysPagePermission.getParentPageId())) {
                                sysDepartRolePermissionMapper.deleteById(sysDepartRolePermission.getId());
                            }
                        } else {
                            sysDepartRolePermissionMapper.deleteById(sysDepartRolePermission.getId());
                        }
                    }
                }
            }

        }
    }

    /**
     * 功能: 如果取消子节点接口全部取消  如果勾选  判断子节点是否全部没被选择 如果有一个被勾选则无效
     * 作者: zjt
     * 日期: 2021/5/6 17:44
     * 版本: 1.0
     */
    private void dealWithChooseAndCancel(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId, Set<Integer> sysPageIdList) {
        //如果取消子节点接口全部取消
        //如果勾选  判断子节点是否全部没被选择 如果有一个被勾选则无效
        final List<SysPagePermission> sysPagePermissions = sysPagePermissionMapper.findAllBySysPageModuleTypeId(sysPageModuleTypeId);
        final Map<Integer, SysPagePermission> sysPagePermissionMap = sysPagePermissions.stream()
                .collect(Collectors.toMap(SysPagePermission::getId, sysPagePermission -> sysPagePermission));
        final Map<Integer, Set<Integer>> sysPagePermissionGroupMap = sysPagePermissions.stream().filter(sysPagePermission -> sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType()))
                .collect(Collectors.groupingBy(SysPagePermission::getParentPageId, Collectors.mapping(SysPagePermission::getId, Collectors.toSet())));
        final List<Integer> pageIdList = sysRolePermissionMapper.findPageIdBySysRoleId(relationId);
        final List<Integer> deptPageIdList = sysDepartRolePermissionMapper.findPagePermissionIdByDepartRoleId(relationId);


        final List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.findBySysRoleId(relationId);
        final List<SysDepartPermission> sysDepartPermissionList = sysDepartPermissionMapper.findByDepartId(relationId);
        final List<SysDepartRolePermission> sysDepartRolePermissionList = sysDepartRolePermissionMapper.findByDepartRoleId(relationId);
        PermissionListAndId permissionListAndId = new PermissionListAndId(sysRolePermissionList, sysDepartPermissionList, sysDepartRolePermissionList);


        saveSysPermissionApi(sysPermissionType, relationId, sysPageIdList, sysPagePermissions, sysPagePermissionMap, sysPagePermissionGroupMap, pageIdList, deptPageIdList, permissionListAndId);

    }


    /**
     * 功能: 获取上级和原本需要保存的syspageId
     * 作者: zjt
     * 日期: 2021/4/30 16:50
     * 版本: 1.0
     */
    private Set<Integer> getSysPageIdListAndParent(Set<Integer> sysPageIdList) {
        Set<Integer> allSysPageIdList = Sets.newHashSet();

        final Map<Integer, Integer> parentPageIdMap = sysPagePermissionMapper.selectList(null).stream().collect(Collectors.toMap(SysPagePermission::getId, SysPagePermission::getParentPageId));

        for (Integer sysPageId : sysPageIdList) {
            boolean flag = true;
            allSysPageIdList.add(sysPageId);
            while (flag) {
                final Integer parentPageId = parentPageIdMap.get(sysPageId);
                allSysPageIdList.add(parentPageId);
                sysPageId = parentPageId;
                if (parentPageId == 0) {
                    flag = false;
                }
            }
        }
        allSysPageIdList.remove(0);
        return allSysPageIdList;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSysPermissionApi(SysPermissionType sysPermissionType, int relationId, int sysPageModuleTypeId, int sysParentPageId, Set<Integer> sysPageIdList) {
        long start = System.currentTimeMillis();
        //获取所有的页面数据
        final List<SysPagePermission> sysPagePermissionList = sysPagePermissionMapper.findAllBySysPageModuleTypeId(sysPageModuleTypeId);
        final Map<Integer, SysPagePermission> sysPagePermissionMap = sysPagePermissionList.stream().collect(Collectors.toMap(SysPagePermission::getId, sysPagePermission -> sysPagePermission));

        final List<Integer> allSysPageIdList = sysPagePermissionList.stream()
                .filter(sysPagePermission -> {
                    //该接口应该不是全员可见 和 不是接口
                    final boolean canRemove = !sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType()) || sysPagePermission.getVisibleToAll() == VisibleToAllType.VISIBLE.getType();
                    if (canRemove) {
                        sysPageIdList.remove(sysPagePermission.getId());
                    }
                    return !canRemove;
                }).map(SysPagePermission::getId).collect(Collectors.toList());

        //判断是否包含page_type 不应该为 除了接口权限
//        if (!allSysPageIdList.containsAll(sysPageIdList)) {
//            throw new CommonException(CommonErrorCode.COMMON_ERROR_38);
//        }


        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
            final List<SysRolePermission> sysRolePermissionList = sysRolePermissionMapper.findBySysRoleId(relationId);
            final List<Integer> sysRolePermissionIds = sysRolePermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPageId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysRolePermission::getId).collect(Collectors.toList());


            if (!sysRolePermissionIds.isEmpty()) {
                sysRolePermissionMapper.deleteBatchIds(sysRolePermissionIds);
            }


            //如果接口全为空 父节点删掉
            if (sysPageIdList.isEmpty()) {
                sysRolePermissionMapper.deleteBySysRoleIdAndPageId(relationId, sysParentPageId);
            }

            for (Integer sysPageId : sysPageIdList) {
                final SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setPageId(sysPageId);
                sysRolePermission.setSysRoleId(relationId);
                sysRolePermissionMapper.insert(sysRolePermission);


                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysRolePermissionMapper.countBySysRoleIdAndPageId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysRolePermission.setPageId(parentPageId);
                        sysRolePermission.setSysRoleId(relationId);
                        sysRolePermissionMapper.insert(sysRolePermission);
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("单条耗时：" + (end - start) + "ms ");
        } else if (sysPermissionType == SysPermissionType.DEPT_ROLE) {

            final List<SysDepartRolePermission> sysDepartRolePermissionList = sysDepartRolePermissionMapper.findByDepartRoleId(relationId);
            final List<Integer> sysDepartRolePermissionIds = sysDepartRolePermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPagePermissionId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysDepartRolePermission::getId).collect(Collectors.toList());

            if (!sysDepartRolePermissionIds.isEmpty()) {
                sysDepartRolePermissionMapper.deleteBatchIds(sysDepartRolePermissionIds);
            }

            //如果接口全为空 父节点删掉
            if (sysPageIdList.isEmpty()) {
                sysDepartRolePermissionMapper.deleteByDepartRoleIdAndPagePermissionId(relationId, sysParentPageId);
            }


            for (Integer sysPageId : sysPageIdList) {
                final SysDepartRolePermission sysDepartRolePermission = new SysDepartRolePermission();
                sysDepartRolePermission.setDepartRoleId(relationId);
                sysDepartRolePermission.setPagePermissionId(sysPageId);
                sysDepartRolePermissionMapper.insert(sysDepartRolePermission);

                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysDepartRolePermissionMapper.countByDepartRoleIdAndPagePermissionId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysDepartRolePermission.setDepartRoleId(relationId);
                        sysDepartRolePermission.setPagePermissionId(parentPageId);
                        sysDepartRolePermissionMapper.insert(sysDepartRolePermission);
                    }
                }

            }
        } else if (sysPermissionType == SysPermissionType.DEPT) {

            final List<SysDepartPermission> sysDepartPermissionList = sysDepartPermissionMapper.findByDepartId(relationId);
            final List<Integer> sysDepartPermissionIds = sysDepartPermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPagePermissionId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysDepartPermission::getId).collect(Collectors.toList());

            if (!sysDepartPermissionIds.isEmpty()) {
                sysDepartPermissionMapper.deleteBatchIds(sysDepartPermissionIds);
            }

            //如果接口全为空 父节点删掉
            if (sysPageIdList.isEmpty()) {
                sysDepartPermissionMapper.deleteByDepartIdAndPagePermissionId(relationId, sysParentPageId);
            }

            for (Integer sysPageId : sysPageIdList) {
                final SysDepartPermission sysDepartPermission = new SysDepartPermission();
                sysDepartPermission.setDepartId(relationId);
                sysDepartPermission.setPagePermissionId(sysPageId);
                sysDepartPermissionMapper.insert(sysDepartPermission);

                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysDepartPermissionMapper.countByDepartIdAndPagePermissionId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysDepartPermission.setDepartId(relationId);
                        sysDepartPermission.setPagePermissionId(parentPageId);
                        sysDepartPermissionMapper.insert(sysDepartPermission);
                    }
                }

            }
        }


    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSysPermissionApi(SysPermissionType sysPermissionType, int relationId, List<SysPagePermission> sysPagePermissionList, int sysParentPageId, Set<Integer> sysPageIdList, PermissionListAndId permissionListAndId) {


        long start = System.currentTimeMillis();

        final Map<Integer, SysPagePermission> sysPagePermissionMap = sysPagePermissionList.stream().collect(Collectors.toMap(SysPagePermission::getId, sysPagePermission -> sysPagePermission));

        final List<Integer> allSysPageIdList = sysPagePermissionList.stream()
                .filter(sysPagePermission -> {
                    //该接口应该不是全员可见 和 不是接口
                    final boolean canRemove = !sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType()) || sysPagePermission.getVisibleToAll() == VisibleToAllType.VISIBLE.getType();
                    if (canRemove) {
                        sysPageIdList.remove(sysPagePermission.getId());
                    }
                    return !canRemove;
                }).map(SysPagePermission::getId).collect(Collectors.toList());

        //判断是否包含page_type 不应该为 除了接口权限
//        if (!allSysPageIdList.containsAll(sysPageIdList)) {
//            throw new CommonException(CommonErrorCode.COMMON_ERROR_38);
//        }


        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
            final List<SysRolePermission> sysRolePermissionList = permissionListAndId.getSysRolePermissionList();
            final List<Integer> sysRolePermissionIds = sysRolePermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPageId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysRolePermission::getId).collect(Collectors.toList());


            if (!sysRolePermissionIds.isEmpty()) {
                sysRolePermissionMapper.deleteBatchIds(sysRolePermissionIds);
            }



            for (Integer sysPageId : sysPageIdList) {
                final SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setPageId(sysPageId);
                sysRolePermission.setSysRoleId(relationId);
                sysRolePermissionMapper.insert(sysRolePermission);


                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysRolePermissionMapper.countBySysRoleIdAndPageId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysRolePermission.setPageId(parentPageId);
                        sysRolePermission.setSysRoleId(relationId);
                        sysRolePermissionMapper.insert(sysRolePermission);
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("单条耗时：" + (end - start) + "ms ");
        } else if (sysPermissionType == SysPermissionType.DEPT_ROLE) {

            final List<SysDepartRolePermission> sysDepartRolePermissionList = permissionListAndId.getSysDepartRolePermissionList();
            final List<Integer> sysDepartRolePermissionIds = sysDepartRolePermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPagePermissionId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysDepartRolePermission::getId).collect(Collectors.toList());

            if (!sysDepartRolePermissionIds.isEmpty()) {
                sysDepartRolePermissionMapper.deleteBatchIds(sysDepartRolePermissionIds);
            }


            for (Integer sysPageId : sysPageIdList) {
                final SysDepartRolePermission sysDepartRolePermission = new SysDepartRolePermission();
                sysDepartRolePermission.setDepartRoleId(relationId);
                sysDepartRolePermission.setPagePermissionId(sysPageId);
                sysDepartRolePermissionMapper.insert(sysDepartRolePermission);

                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysDepartRolePermissionMapper.countByDepartRoleIdAndPagePermissionId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysDepartRolePermission.setDepartRoleId(relationId);
                        sysDepartRolePermission.setPagePermissionId(parentPageId);
                        sysDepartRolePermissionMapper.insert(sysDepartRolePermission);
                    }
                }

            }
        } else if (sysPermissionType == SysPermissionType.DEPT) {

            final List<SysDepartPermission> sysDepartPermissionList = permissionListAndId.getSysDepartPermissionList();
            final List<Integer> sysDepartPermissionIds = sysDepartPermissionList.stream().filter(sysPermission -> {
                final Integer pageId = sysPermission.getPagePermissionId();
                return allSysPageIdList.contains(pageId) && sysParentPageId == sysPagePermissionMap.get(pageId).getParentPageId();
            }).map(SysDepartPermission::getId).collect(Collectors.toList());

            if (!sysDepartPermissionIds.isEmpty()) {
                sysDepartPermissionMapper.deleteBatchIds(sysDepartPermissionIds);
            }


            for (Integer sysPageId : sysPageIdList) {
                final SysDepartPermission sysDepartPermission = new SysDepartPermission();
                sysDepartPermission.setDepartId(relationId);
                sysDepartPermission.setPagePermissionId(sysPageId);
                sysDepartPermissionMapper.insert(sysDepartPermission);

                final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
                if (sysPagePermission != null) {
                    final Integer parentPageId = sysPagePermission.getParentPageId();

                    //要判断父级是否添加 如果没有要添加
                    final Integer count = sysDepartPermissionMapper.countByDepartIdAndPagePermissionId(relationId, parentPageId);
                    if (count == 0 && parentPageId != 0) {
                        sysDepartPermission.setDepartId(relationId);
                        sysDepartPermission.setPagePermissionId(parentPageId);
                        sysDepartPermissionMapper.insert(sysDepartPermission);
                    }
                }

            }
        }
    }


    public void saveSysPermissionApi(SysPermissionType sysPermissionType, int relationId, Set<Integer> sysPageIdList, List<SysPagePermission> sysPagePermissions, Map<Integer, SysPagePermission> sysPagePermissionMap, Map<Integer, Set<Integer>> sysPagePermissionGroupMap, List<Integer> pageIdList, List<Integer> deptPageIdList, PermissionListAndId permissionListAndId) {

        Set<Integer> sysParentPageIds = new HashSet<>();
        for (Integer sysPageId : sysPagePermissionMap.keySet()) {
            final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
            if (!sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType())) {
                if (!sysPageIdList.contains(sysPageId)) {
                    sysParentPageIds.add(sysPageId);
                }
            }
        }
        if(!CollectionUtils.isEmpty(sysParentPageIds)){
            if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
                sysRolePermissionMapper.deleteBySysRoleIdAndPageIdIn(relationId, sysParentPageIds);
            } else if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
                sysDepartRolePermissionMapper.deleteByDepartRoleIdAndPagePermissionIdIn(relationId, sysParentPageIds);
            }else if (sysPermissionType == SysPermissionType.DEPT) {
                sysDepartPermissionMapper.deleteByDepartIdAndPagePermissionIdIn(relationId, sysParentPageIds);
            }
        }

        for (Integer sysPageId : sysPagePermissionMap.keySet()) {
            final SysPagePermission sysPagePermission = sysPagePermissionMap.get(sysPageId);
            //如果是子菜单
            if (!sysPagePermission.getPageType().equals(PageType.INTERFACE_PERMISSIONS.getType())) {
                //勾选
                if (sysPageIdList.contains(sysPageId)) {
                    final Set<Integer> sysPageList = sysPagePermissionGroupMap.get(sysPagePermission.getId());
                    if (sysPageList != null) {
                        //判断是否接口有有个打勾 如果有不需要全部打勾
                        if (sysPermissionType == SysPermissionType.SYSTEM_ROLE) {
                            if (CollectionUtils.containsAny(pageIdList, sysPageList)) {
                                continue;
                            }
                        } else if (sysPermissionType == SysPermissionType.DEPT_ROLE) {
                            if (CollectionUtils.containsAny(deptPageIdList, sysPageList)) {
                                continue;
                            }
                        }
//                        saveSysPermissionApi(sysPermissionType, relationId, sysPageModuleTypeId, sysPagePermission.getId(), sysPageList);
                        saveSysPermissionApi(sysPermissionType, relationId, sysPagePermissions, sysPagePermission.getId(), sysPageList, permissionListAndId);
                    }
                } else {
                    //取消勾选
//                    saveSysPermissionApi(sysPermissionType, relationId, sysPageModuleTypeId, sysPagePermission.getId(), Sets.newHashSet());
                    saveSysPermissionApi(sysPermissionType, relationId, sysPagePermissions, sysPagePermission.getId(),  Sets.newHashSet(), permissionListAndId);
                }
            }
        }
    }


}