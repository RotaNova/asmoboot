package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.ManagePermissionService;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysPageModuleTypeService;
import com.rotanava.boot.system.api.module.constant.AbilityType;
import com.rotanava.boot.system.api.module.constant.AnnPriorityType;
import com.rotanava.boot.system.api.module.constant.IsAuto;
import com.rotanava.boot.system.api.module.constant.IsJump;
import com.rotanava.boot.system.api.module.constant.PageDeleteStatus;
import com.rotanava.boot.system.api.module.constant.PageLeafFlag;
import com.rotanava.boot.system.api.module.constant.PageShow;
import com.rotanava.boot.system.api.module.constant.PageStatus;
import com.rotanava.boot.system.api.module.constant.PageType;
import com.rotanava.boot.system.api.module.constant.SysAnnConfigIdEnum;
import com.rotanava.boot.system.api.module.constant.VisibleToAllType;
import com.rotanava.boot.system.api.module.system.bean.ExportPageExcel;
import com.rotanava.boot.system.api.module.system.bean.ImportPageExcel;
import com.rotanava.boot.system.api.module.system.bean.SysApiBean;
import com.rotanava.boot.system.api.module.system.bo.SysApiPermission;
import com.rotanava.boot.system.api.module.system.bo.SysPageApi;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import com.rotanava.boot.system.api.module.system.dto.AddSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.AddSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysApiPermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.vo.SysApiPermissionVO;
import com.rotanava.boot.system.api.module.system.vo.SysPageModuleTypeVO;
import com.rotanava.boot.system.api.module.system.vo.SysPagePermissionVO;
import com.rotanava.boot.system.module.dao.SysApiPermissionMapper;
import com.rotanava.boot.system.module.dao.SysDepartPermissionMapper;
import com.rotanava.boot.system.module.dao.SysDepartRolePermissionMapper;
import com.rotanava.boot.system.module.dao.SysPageApiMapper;
import com.rotanava.boot.system.module.dao.SysPagePermissionMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.constant.enums.MappingEnum;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.AuthErrorCode;
import com.rotanava.framework.exception.code.FileErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SearchDTO;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.PageUtils;
import com.rotanava.framework.util.SortUtils;
import com.rotanava.framework.util.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 管理权限服务impl
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Service
@DubboService
@Slf4j
public class ManagePermissionServiceImpl implements ManagePermissionService {

    @Autowired
    private SysApiPermissionMapper sysApiPermissionMapper;

    @Autowired
    private SysPagePermissionMapper sysPagePermissionMapper;

    @Autowired
    private SysPageApiMapper sysPageApiMapper;

    @Autowired
    private SysDepartPermissionMapper sysDepartPermissionMapper;

    @Autowired
    private SysDepartRolePermissionMapper sysDepartRolePermissionMapper;

    @DubboReference
    private CommonApi commonApi;

    @DubboReference
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private SysPageModuleTypeService sysPageModuleTypeService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer saveSysPagePermission(AddSysPagePermissionDTO addSysPagePermissionDTO, int userId) {
        SysPagePermission sysPagePermission = new SysPagePermission();
        BeanUtils.copyProperties(addSysPagePermissionDTO, sysPagePermission);
        //从shiro获取用户信息
        sysPagePermission.setCreateTime(new Date());
        sysPagePermission.setCreateBy(userId);
        sysPagePermission.setUpdateTime(new Date());
        sysPagePermission.setUpdateBy(userId);
        sysPagePermission.setPageDeleteStatus(PageDeleteStatus.NOT_DELETED.getStatus());
        //页面资源类型 等于 接口权限
        if (PageType.INTERFACE_PERMISSIONS.getType().equals(sysPagePermission.getPageType())) {
            //页面类型 等于空 报错
            if (sysPagePermission.getAbilityType() == null) {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_38);
            }
        } else {
            sysPagePermission.setAbilityType(null);
        }
        log.info("pagecode ==" + addSysPagePermissionDTO.getPageCode());
        if (!commonApi.doDuplicateCheck(MappingEnum.SYS_PAGE_PERMISSION_PAGE_CODE.getTableName(), MappingEnum.SYS_PAGE_PERMISSION_PAGE_CODE.getFieldName(), addSysPagePermissionDTO.getPageCode(), null).getData()) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_61);
        }
        checkParameters(sysPagePermission, addSysPagePermissionDTO.getParentPageId());

        if (IsAuto.YES.getAuto().equals(addSysPagePermissionDTO.getIsAuto())) {
            if (StringUtils.isEmpty(addSysPagePermissionDTO.getPageJson())) {
                throw new CommonException(ParamErrorCode.PARAM_ERROR_88);
            }
        }

        //新增
        sysPagePermissionMapper.insert(sysPagePermission);

        return sysPagePermission.getId();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysPagePermission(DeleteSysPagePermissionDTO deleteSysPagePermissionDTO) {
        //更新页面删除状态按 为 已删除

        List<Integer> sysPageIds = sysPagePermissionMapper.findChild(deleteSysPagePermissionDTO.getSysPageIds());
        List<SysPageApi> sysPageApis = sysPageApiMapper.findBySysPageIdIn(sysPageIds);
        List<Integer> sysApiIds = sysPageApis.stream().map(SysPageApi::getSysApiId).collect(Collectors.toList());
        List<Integer> sysPageApiIds = sysPageApis.stream().map(SysPageApi::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(sysPageIds)) {
            sysPagePermissionMapper.deleteBatchIds(sysPageIds);
        }
        if (!CollectionUtils.isEmpty(sysApiIds)) {
            sysApiPermissionMapper.deleteBatchIds(sysApiIds);
        }
        if (!CollectionUtils.isEmpty(sysPageApiIds)) {
            sysPageApiMapper.deleteBatchIds(sysPageApiIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysPagePermission(UpdateSysPagePermissionDTO updateSysPagePermissionDTO) {
        SysPagePermission sysPagePermission = new SysPagePermission();
        BeanUtils.copyProperties(updateSysPagePermissionDTO, sysPagePermission);
        sysPagePermission.setId(updateSysPagePermissionDTO.getSysPageId());
        //从shiro获取用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysPagePermission.setUpdateTime(new Date());
        sysPagePermission.setUpdateBy(sysUser.getUpdateBy());
        if (!commonApi.doDuplicateCheck(MappingEnum.SYS_PAGE_PERMISSION_PAGE_CODE.getTableName(), MappingEnum.SYS_PAGE_PERMISSION_PAGE_CODE.getFieldName(), updateSysPagePermissionDTO.getPageCode(), Convert.toStr(updateSysPagePermissionDTO.getSysPageId())).getData()) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_61);
        }
        checkParameters(sysPagePermission, updateSysPagePermissionDTO.getParentPageId());
        //更新页面权限
        sysPagePermissionMapper.updateById(sysPagePermission);
    }


    @Override
    public BaseVO<SysPagePermissionVO> getSysPagePermission(Integer sysPageId) {
        //按页面id查询
        SysPagePermission sysPagePermission = sysPagePermissionMapper.selectById(sysPageId);
        if (sysPagePermission == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_15);
        }
        SysPagePermissionVO sysPagePermissionVO = new SysPagePermissionVO();
        BeanUtils.copyProperties(sysPagePermission, sysPagePermissionVO);
        sysPagePermissionVO.setSysPageId(sysPagePermission.getId());

        BaseVO<SysPagePermissionVO> sysPagePermissionBaseVO = new BaseVO<>();
        sysPagePermissionBaseVO.setData(sysPagePermissionVO);

        return sysPagePermissionBaseVO;
    }

    @Override
    public BaseVO<SysPagePermissionVO> listSysPagePermissions(SearchDTO searchDTO) {
        QueryWrapper<SysPagePermission> queryWrapper = QueryGenerator.initQueryWrapper(searchDTO);

        //按条件 分页 查询接口资源
        return listSysPagePermissionBaseVO(sysPagePermissionMapper.findSysPagePermissions(queryWrapper));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveSysApiPermission(AddSysApiPermissionDTO addSysApiPermissionDTO, int userId) {
        //按 接口路径 与 接口请求方式 查询列表
        List<SysApiPermission> sysApiPermissions = sysApiPermissionMapper.findByApiUrlAndApiMethod(addSysApiPermissionDTO.getApiUrl(), addSysApiPermissionDTO.getApiMethod());
        if (!CollectionUtils.isEmpty(sysApiPermissions)) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_16);
        }

        SysApiPermission sysApiPermission = new SysApiPermission();
        BeanUtils.copyProperties(addSysApiPermissionDTO, sysApiPermission);
        //从shiro获取用户信息
        sysApiPermission.setCreateTime(new Date());
        sysApiPermission.setCreateBy(userId);
        sysApiPermission.setUpdateTime(new Date());
        sysApiPermission.setUpdateBy(userId);
        if (!commonApi.doDuplicateCheck(MappingEnum.SYS_API_PERMISSION_API_CODE.getTableName(), MappingEnum.SYS_API_PERMISSION_API_CODE.getFieldName(), addSysApiPermissionDTO.getApiCode(), null).getData()) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_61);
        }
        //保存接口权限
        sysApiPermissionMapper.insert(sysApiPermission);
        //按页面id查询
        SysPagePermission sysPagePermission = sysPagePermissionMapper.selectById(addSysApiPermissionDTO.getSysPageId());
        if (sysPagePermission == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_15);
        }

        SysPageApi sysPageApi = new SysPageApi(sysApiPermission.getId(), addSysApiPermissionDTO.getSysPageId());
        //保存 接口与页面 的关联
        sysPageApiMapper.insert(sysPageApi);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteSysApiPermission(DeleteSysApiPermissionDTO deleteSysApiPermissionDTO) {
        final List<Integer> sysApiIds = deleteSysApiPermissionDTO.getSysApiIds();
        //删除 接口与页面 的关联
        sysPageApiMapper.deleteBySysApiIdIn(sysApiIds);
        //删除 接口权限
        sysApiPermissionMapper.deleteBatchIds(sysApiIds);

        //删除部门角色权限
        sysDepartRolePermissionMapper.deleteByPagePermissionIdIn(sysApiIds);
        //删除系统部门角色权限
        sysDepartPermissionMapper.deleteByPagePermissionIdIn(sysApiIds);

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateSysApiPermission(UpdateSysApiPermissionDTO updateSysApiPermissionDTO) {
        SysApiPermission sysApiPermission = new SysApiPermission();
        BeanUtils.copyProperties(updateSysApiPermissionDTO, sysApiPermission);
        //从shiro获取用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        sysApiPermission.setUpdateTime(new Date());
        sysApiPermission.setUpdateBy(sysUser.getId());
        sysApiPermission.setId(updateSysApiPermissionDTO.getSysApiId());
        if (!commonApi.doDuplicateCheck(MappingEnum.SYS_API_PERMISSION_API_CODE.getTableName(), MappingEnum.SYS_API_PERMISSION_API_CODE.getFieldName(), updateSysApiPermissionDTO.getApiCode(), Convert.toStr(updateSysApiPermissionDTO.getSysApiId())).getData()) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_61);
        }
        //按 接口id修改接口权限
        sysApiPermissionMapper.updateById(sysApiPermission);
        if(sysPageApiMapper.countBySysApiId(updateSysApiPermissionDTO.getSysApiId()) > 0){
            sysPageApiMapper.updateSysPageIdBySysApiId(updateSysApiPermissionDTO.getSysPageId(), updateSysApiPermissionDTO.getSysApiId());
        }else {
            SysPageApi sysPageApi = new SysPageApi();
            sysPageApi.setSysApiId( updateSysApiPermissionDTO.getSysApiId());
            sysPageApi.setSysPageId(updateSysApiPermissionDTO.getSysPageId());
            sysPageApiMapper.insert(sysPageApi);
        }

    }

    @Override
    public BaseVO<SysApiPermissionVO> getSysApiPermission(Integer sysApiId) {
        //按 接口id
        SysApiPermission sysApiPermission = sysApiPermissionMapper.selectById(sysApiId);
        if (sysApiPermission == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_14);
        }
        //按 接口id 查询 接口关联页面表
        SysPageApi sysPageApi = sysPageApiMapper.findBySysApiId(sysApiId);
        if (sysPageApi == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_15);
        }
        SysPagePermission sysPagePermission = sysPagePermissionMapper.selectById(sysPageApi.getSysPageId());

        SysApiPermissionVO sysApiPermissionVO = new SysApiPermissionVO();
        BeanUtils.copyProperties(sysApiPermission, sysApiPermissionVO);
        sysApiPermissionVO.setSysApiId(sysPageApi.getSysApiId());
        sysApiPermissionVO.setSysPageId(sysPageApi.getSysPageId());
        sysApiPermissionVO.setPageTitle(sysPagePermission.getPageTitle());


        BaseVO<SysApiPermissionVO> sysApiPermissionBaseVO = new BaseVO<>();
        sysApiPermissionBaseVO.setData(sysApiPermissionVO);
        return sysApiPermissionBaseVO;
    }

    @Override
    public BaseVO<SysApiPermissionVO> listSysApiPermissions(BaseDTO baseDTO) {
        QueryWrapper<SysApiPermission> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        queryWrapper.orderByDesc("sys_api_permission.id");
        IPage<SysApiPermission> sysApiBeanPage = PageUtils.startPage(baseDTO);

        //按条件 分页 查询接口资源
        List<SysApiBean> sysApiBeans = sysApiPermissionMapper.findSysApiBean(
                queryWrapper,
                sysApiBeanPage
        );

        List<SysApiPermissionVO> sysApiPermissionVOList = new ArrayList<>();
        for (SysApiBean sysApiBean : sysApiBeans) {
            SysApiPermissionVO sysApiPermissionVO = new SysApiPermissionVO();
            BeanUtils.copyProperties(sysApiBean, sysApiPermissionVO);
            sysApiPermissionVOList.add(sysApiPermissionVO);
        }
        BaseVO<SysApiPermissionVO> sysApiPermissionBaseVO = new BaseVO<>();
        sysApiPermissionBaseVO.setRecords(sysApiPermissionVOList);
        sysApiPermissionBaseVO.setTotal(sysApiBeanPage.getTotal());
        return sysApiPermissionBaseVO;

    }

    @Override
    public List<Tree<Integer>> listSysPagePermissionsNavigationBar(Integer sysPageModuleTypeId) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPagePermission> sysPagePermissions = new ArrayList<>();

        if (loginUser.getIsAdmin()) {
            sysPagePermissions = sysPagePermissionMapper.findBySysPageModuleTypeId(sysPageModuleTypeId);
        } else {
            sysPagePermissions = sysPagePermissionMapper.findBySysUserId(loginUser.getId(), sysPageModuleTypeId);
        }

        return TreeUtil.build(sysPagePermissions, 0, (sysPagePermission, treeNode) -> {
            treeNode.setId(sysPagePermission.getId());
            treeNode.setParentId(sysPagePermission.getParentPageId());
            treeNode.setName(sysPagePermission.getPageTitle());
            treeNode.setWeight(sysPagePermission.getSort());

            treeNode.putExtra("isJump", sysPagePermission.getIsJump());
            treeNode.putExtra("pageIcon", sysPagePermission.getPageIcon());
            treeNode.putExtra("parentPageId", sysPagePermission.getParentPageId());
            treeNode.putExtra("pageTitle", sysPagePermission.getPageTitle());
            treeNode.putExtra("pageUrl", sysPagePermission.getPageUrl());
            treeNode.putExtra("pageRedirect", sysPagePermission.getPageRedirect());
            treeNode.putExtra("isAuto", sysPagePermission.getIsAuto());
        });
    }

    @Override
    public BaseVO<SysPagePermissionVO> listSysPagePermissionsUseApi(Integer sysPageModuleTypeId) {
        return listSysPagePermissionBaseVO(sysPagePermissionMapper.findLinkPage(sysPageModuleTypeId));
    }

    @Override
    public Integer countByApiUrl(String apiUrl) {
        return sysApiPermissionMapper.countByApiUrl(apiUrl);
    }

    @Override
    public String getPageJson(Integer sysPageId) {
        //按页面id查询
        SysPagePermission sysPagePermission = sysPagePermissionMapper.selectById(sysPageId);
        if (sysPagePermission == null) {
            throw new CommonException(AuthErrorCode.AUTH_ERROR_15);
        }
        return sysPagePermission.getPageJson();
    }

    @Override
    public void importExcel(MultipartFile file) throws Exception {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final String originalFilename = file.getOriginalFilename();
        //错误返回内容
        final List<ImportPageExcel> errorPageExcels = Lists.newArrayList();
        List<ImportPageExcel> pageExcels;
        try {
            pageExcels = ExcelUtils.parse(file.getInputStream(), ImportPageExcel.class);
        } catch (Exception e) {
            log.error("excel文件解析错误", e);
            //文件解析错误
            throw new CommonException(ParamErrorCode.PARAM_ERROR_60);
        }

        List<SysPageModuleTypeVO> sysPageModuleTypeVO = sysPageModuleTypeService.getSysPageModuleTypeVO();
        Map<String, Integer> pageModuleTypeMap = sysPageModuleTypeVO.stream().collect(Collectors.toMap(SysPageModuleTypeVO::getSysPageModuleTypeName, SysPageModuleTypeVO::getSysPageModuleTypeId));
        for (ImportPageExcel pageExcel : pageExcels) {
            SysPagePermission sysPagePermission = sysPagePermissionMapper.findByPageCode(pageExcel.getParentPageCode());

            final AddSysPagePermissionDTO addSysPagePermissionDTO = new AddSysPagePermissionDTO();
            addSysPagePermissionDTO.setPageRedirect(pageExcel.getPageRedirect());
            addSysPagePermissionDTO.setPageIcon(pageExcel.getPageIcon());


            if (!StringUtils.isEmpty(pageExcel.getSysPageModuleTypeName())) {

                Integer id = pageModuleTypeMap.get(pageExcel.getSysPageModuleTypeName());
                if (id != null) {
                    addSysPagePermissionDTO.setSysPageModuleTypeId(id);
                } else {
                    pageExcel.setFailReason("所属模块不存在错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("所属模块为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageTitle())) {
                addSysPagePermissionDTO.setPageTitle(pageExcel.getPageTitle());
            } else {
                pageExcel.setFailReason("页面名称为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageCode())) {
                addSysPagePermissionDTO.setPageCode(pageExcel.getPageCode());
            } else {
                pageExcel.setFailReason("页面编码为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageUrl())) {
                addSysPagePermissionDTO.setPageUrl(pageExcel.getPageUrl());
            } else {
                pageExcel.setFailReason("页面资源路径为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageShow())) {
                Integer pageShow = PageShow.findPageShow(pageExcel.getPageShow());
                if (pageShow != null) {
                    addSysPagePermissionDTO.setPageShow(pageShow);
                } else {
                    pageExcel.setFailReason("是否可见填写错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("是否可见为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getVisibleToAll())) {
                Integer type = VisibleToAllType.findType(pageExcel.getVisibleToAll());
                if (type != null) {
                    addSysPagePermissionDTO.setVisibleToAll(type);
                } else {
                    pageExcel.setFailReason("是否全员可见填写错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("是否全员可见为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getIsJump())) {
                Integer jump = IsJump.findJump(pageExcel.getIsJump());
                if (jump != null) {
                    addSysPagePermissionDTO.setIsJump(jump);
                } else {
                    pageExcel.setFailReason("是否外链填写错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("是否外链为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageStatus())) {
                Integer status = PageStatus.findStatus(pageExcel.getPageStatus());
                if (status != null) {
                    addSysPagePermissionDTO.setPageStatus(status);
                } else {
                    pageExcel.setFailReason("页面资源状态填写错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("页面资源状态为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!StringUtils.isEmpty(pageExcel.getPageType())) {
                Integer type = PageType.findType(pageExcel.getPageType());
                if (type != null) {
                    addSysPagePermissionDTO.setPageType(type);
                } else {
                    pageExcel.setFailReason("页面资源类型填写错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("页面资源类型为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (!PageType.A_MENU.getType().equals(addSysPagePermissionDTO.getPageType())) {
                if (!StringUtils.isEmpty(pageExcel.getParentPageCode())) {
                    if (sysPagePermission != null) {
                        addSysPagePermissionDTO.setParentPageId(sysPagePermission.getId());
                    } else {
                        pageExcel.setFailReason("父节点编码不存在");
                        errorPageExcels.add(pageExcel);
                        continue;
                    }
                } else {
                    pageExcel.setFailReason("父节点编码为空错误,页面资源类型非一级菜单");
                    errorPageExcels.add(pageExcel);
                    continue;
                }

            }
            if (PageType.INTERFACE_PERMISSIONS.getType().equals(addSysPagePermissionDTO.getPageType())) {
                if (!StringUtils.isEmpty(pageExcel.getAbilityType())) {
                    Integer type = AbilityType.findType(pageExcel.getAbilityType());
                    if (type != null) {
                        addSysPagePermissionDTO.setAbilityType(type);
                    } else {
                        pageExcel.setFailReason("页面类型填写错误");
                        errorPageExcels.add(pageExcel);
                        continue;
                    }
                } else {
                    pageExcel.setFailReason("页面类型为空错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            }

            if (!StringUtils.isEmpty(pageExcel.getIsAuto())) {
                Integer auto = IsAuto.findAuto(pageExcel.getIsAuto());
                if (auto != null) {
                    addSysPagePermissionDTO.setIsAuto(auto);
                }
            } else {
                pageExcel.setFailReason("是否自动生成为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }

            if (IsAuto.YES.getAuto().equals(addSysPagePermissionDTO.getIsAuto())) {
                if (!StringUtils.isEmpty(pageExcel.getPageJson())) {
                    addSysPagePermissionDTO.setPageJson(pageExcel.getPageJson());
                } else {
                    pageExcel.setFailReason("自动生成代码为空错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            }


            if (pageExcel.getSort() != null) {
                try {
                    addSysPagePermissionDTO.setSort(Convert.toInt(pageExcel.getSort()));
                } catch (Exception e) {
                    pageExcel.setFailReason("排序不为正整数错误");
                    errorPageExcels.add(pageExcel);
                    continue;
                }
            } else {
                pageExcel.setFailReason("排序为空错误");
                errorPageExcels.add(pageExcel);
                continue;
            }


            try {
                saveSysPagePermission(addSysPagePermissionDTO, sysUser.getId());
            } catch (CommonException e) {
                ErrorCode errorCode = e.getErrorCode();
                pageExcel.setFailReason(errorCode.getMsg());
                errorPageExcels.add(pageExcel);
            }

        }


        if (errorPageExcels.size() > 0) {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, errorPageExcels, ImportPageExcel.class);
            String objName = String.format("导入页面资源失败_%s.xlsx", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">批量页面权限失败" + errorPageExcels.size() + "条，下载表格查看详细信息。<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">点击下载</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "导入页面资源结果通知", String.format("批量导入失败%s条，点击下载表格查看详细信息。", errorPageExcels.size()), richText, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        } else {
            //导入成功
            String msg = originalFilename + "导入成功" + pageExcels.size() + "条";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, msg, msg, msg, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        }


    }

    @Override
    public void exportExcel() {
        List<SysPagePermission> sysPagePermissions = sysPagePermissionMapper.selectList(Wrappers.emptyWrapper());
        exportExcel(sysPagePermissions);
    }


    @Override
    public String getMould() {
        return fileUploadUtil.getObjUrl(BucketNamePool.COMMON_BUCKET, "页面批量导入模版.xlsx", 10);
    }

    @Override
    public void batchExportExcel(List<Integer> sysPageIds) {
        List<SysPagePermission> sysPagePermissions = sysPagePermissionMapper.selectBatchIds(sysPageIds);
        exportExcel(sysPagePermissions);
    }

    private void exportExcel(List<SysPagePermission> sysPagePermissions) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPageModuleTypeVO> sysPageModuleTypeVO = sysPageModuleTypeService.getSysPageModuleTypeVO();
        Map<Integer, String> pageModuleTypeMap = sysPageModuleTypeVO.stream().collect(Collectors.toMap(SysPageModuleTypeVO::getSysPageModuleTypeId, SysPageModuleTypeVO::getSysPageModuleTypeName));
        Map<Integer, String> map = sysPagePermissions.stream().collect(Collectors.toMap(SysPagePermission::getId, SysPagePermission::getPageCode));
        sysPagePermissions = SortUtils.sortByParent(sysPagePermissions, 0, "id", "parentPageId", null);

        List<ExportPageExcel> pageExcels = new LinkedList<>();
        for (SysPagePermission sysPagePermission : sysPagePermissions) {

            ExportPageExcel pageExcel = new ExportPageExcel();

            pageExcel.setSysPageModuleTypeName(pageModuleTypeMap.get(sysPagePermission.getSysPageModuleTypeId()));
            pageExcel.setPageTitle(sysPagePermission.getPageTitle());
            pageExcel.setPageCode(sysPagePermission.getPageCode());
            pageExcel.setPageUrl(sysPagePermission.getPageUrl());
            pageExcel.setPageRedirect(sysPagePermission.getPageRedirect());
            pageExcel.setPageIcon(sysPagePermission.getPageIcon());
            pageExcel.setParentPageCode(map.get(sysPagePermission.getParentPageId()));
            pageExcel.setPageDescription(sysPagePermission.getPageDescription());
            pageExcel.setPageJson(sysPagePermission.getPageJson());
            pageExcel.setSort(Convert.toStr(sysPagePermission.getSort()));

            String pageShow = PageShow.findPageShow(sysPagePermission.getPageShow());
            if (pageShow != null) {
                pageExcel.setPageShow(pageShow);
            }

            String visibleToAllType = VisibleToAllType.findType(sysPagePermission.getVisibleToAll());
            if (visibleToAllType != null) {
                pageExcel.setVisibleToAll(visibleToAllType);
            }

            String jump = IsJump.findJump(sysPagePermission.getIsJump());
            if (jump != null) {
                pageExcel.setIsJump(jump);
            }

            String status = PageStatus.findStatus(sysPagePermission.getPageStatus());
            if (status != null) {
                pageExcel.setPageStatus(status);
            }

            String pageType = PageType.findType(sysPagePermission.getPageType());
            if (pageType != null) {
                pageExcel.setPageType(pageType);
            }

            String abilityType = AbilityType.findType(sysPagePermission.getAbilityType());
            if (abilityType != null) {
                pageExcel.setAbilityType(abilityType);
            }

            String auto = IsAuto.findAuto(sysPagePermission.getIsAuto());
            if (auto != null) {
                pageExcel.setIsAuto(auto);
            }
            pageExcels.add(pageExcel);
        }


        try {

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, pageExcels, ExportPageExcel.class);
            //富文本
            String objName = String.format("页面资源列表_%s", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">批量导出页面资源" + pageExcels.size() + "条，下载表格查看详细信息。<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">点击下载</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "导出页面资源结果通知", "导出成功", richText, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);

        } catch (Exception e) {
            throw new CommonException(FileErrorCode.FILE_ERROR_03);
        }
    }

    /**
     * 签证官sys列表页面允许基地
     *
     * @param sysPagePermissions 系统页面的权限
     * @return {@link BaseVO<SysPagePermissionVO> }
     * @author WeiQiangMiao
     * @date 2021-03-17
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private BaseVO<SysPagePermissionVO> listSysPagePermissionBaseVO(List<SysPagePermission> sysPagePermissions) {
        List<SysPagePermissionVO> sysPagePermissionVOList = new ArrayList<>();
        for (SysPagePermission sysPagePermission : sysPagePermissions) {
            SysPagePermissionVO sysPagePermissionVO = new SysPagePermissionVO();
            BeanUtils.copyProperties(sysPagePermission, sysPagePermissionVO);
            sysPagePermissionVO.setSysPageId(sysPagePermission.getId());
            sysPagePermissionVOList.add(sysPagePermissionVO);
        }


        BaseVO<SysPagePermissionVO> sysPagePermissionBaseVO = new BaseVO<>();
        sysPagePermissionBaseVO.setRecords(sysPagePermissionVOList);

        return sysPagePermissionBaseVO;
    }

    /**
     * 检查参数
     *
     * @param sysPagePermission 系统页面权限
     * @param parentPageId      父页面id
     * @author WeiQiangMiao
     * @date 2021-03-20
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private void checkParameters(SysPagePermission sysPagePermission, Integer parentPageId) {
        if (PageType.A_MENU.getType().equals(sysPagePermission.getPageType())) {
            sysPagePermission.setPageLeafFlag(PageLeafFlag.IS_NOT.getPageLeafFlag());
            sysPagePermission.setParentPageId(0);
        } else {
            //父级id为空
            if (sysPagePermission.getParentPageId() != null) {
                //设置 页面资源 不是 叶子节点
                sysPagePermission.setPageLeafFlag(PageLeafFlag.YES.getPageLeafFlag());
                sysPagePermission.setParentPageId(parentPageId);
            } else {
                sysPagePermission.setPageLeafFlag(PageLeafFlag.IS_NOT.getPageLeafFlag());
            }
        }
    }


}
