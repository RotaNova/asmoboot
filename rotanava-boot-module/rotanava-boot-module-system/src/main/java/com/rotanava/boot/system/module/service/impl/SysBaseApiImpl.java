package com.rotanava.boot.system.module.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rotanava.boot.system.api.DeviceAuthService;
import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.SysDictItemService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.UserDeleteStatus;
import com.rotanava.boot.system.api.module.system.bo.SysApiPermission;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRole;
import com.rotanava.boot.system.api.module.system.bo.SysRole;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.module.dao.*;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.constant.CacheConstant;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.common.constant.RequestMethod;
import com.rotanava.framework.common.constant.enums.MappingEnum;
import com.rotanava.framework.config.shiro.UrlPermission;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysDict;
import com.rotanava.framework.model.SysDictItem;
import com.rotanava.framework.model.SysMappingInfo;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.model.bo.SysSearchConfig;
import com.rotanava.framework.module.dao.BaseCommonMapper;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shiro.authz.Permission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * 底层共通业务API，提供其他独立模块调用
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
@Slf4j
@DubboService(validation = "true")
@Service
public class SysBaseApiImpl implements CommonApi {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysApiPermissionMapper sysApiPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysDepartRoleMapper sysDepartRoleMapper;

    @Autowired
    private SysDictItemService sysDictItemService;

    @Autowired
    private SysDictMapper sysDictMapper;

    @Autowired
    private SysSearchConfigMapper sysSearchConfigMapper;

    @Autowired
    private BaseCommonMapper baseCommonMapper;

    @Autowired
    private ManageSecurityService manageSecurityService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    DeviceAuthService deviceAuthService;

    @Override
    public Set<String> queryUserRoles(String userAccountName) {
        //查询用户
        LoginUser loginUser = getUserByAccountName(userAccountName);
        //获取个人角色名称集合
        List<SysRole> sysRoles = sysRoleMapper.findBySysUserId(loginUser.getId());
        Set<String> personalRoleNames = sysRoles.stream().map(sysRole -> String.format("%s%s", CommonConstant.PERSONAL, sysRole.getRoleName())).collect(Collectors.toSet());
        //获取部门角色名称集合
        List<SysDepartRole> sysDepartRoles = sysDepartRoleMapper.findBySysUserId(loginUser.getId());
        Set<String> departmentRoleNames = sysDepartRoles.stream().map(sysDepartRole -> String.format("%s%s", CommonConstant.DEPARTMENT, sysDepartRole.getDescription())).collect(Collectors.toSet());
        //合并
        Set<String> roleNames = new HashSet<>();
        roleNames.addAll(departmentRoleNames);
        roleNames.addAll(personalRoleNames);
        return roleNames;
    }

    @Override
    public Set<Permission> queryUserAuths(String userAccountName) {
        //查询用户
        LoginUser loginUser = getUserByAccountName(userAccountName);
        List<SysApiPermission> sysApiPermissions;
        if (loginUser.getIsAdmin()) {
            sysApiPermissions = sysApiPermissionMapper.selectList(Wrappers.emptyWrapper());
        } else {
            sysApiPermissions = sysApiPermissionMapper.findBySysUserId(loginUser.getId());
        }
        Set<Permission> urlPermissions = new HashSet<>();
        for (SysApiPermission sysApiPermission : sysApiPermissions) {
            RequestMethod requestMethod = RequestMethod.getEnumByRequestMethod(sysApiPermission.getApiMethod());
            if (requestMethod != null) {
                UrlPermission urlPermission = new UrlPermission(sysApiPermission.getApiUrl(), requestMethod.getMethodName());
                urlPermissions.add(urlPermission);
            }
        }

        return urlPermissions;
    }


    /**
     * @description : 获取映射表的value,方便程序动态获取值
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @Override
    public String getMappingValue(String key) {
        SysMappingInfo sysMappingInfoByKey = baseCommonMapper.getSysMappingInfoByKey(key);
        if (sysMappingInfoByKey != null) {
            return sysMappingInfoByKey.getValue();
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = CommonConstant.PREFIX_ONLINE_USER_INFO, key = "#userAccountName")
    public LoginUser getUserByAccountName(String userAccountName) {
        if (StringUtils.isEmpty(userAccountName)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = sysUserMapper.findByUserAccountNameAndUserDeleteStatus(userAccountName, UserDeleteStatus.NOT_DELETED.getStatus());
        if (sysUser == null) {
            return null;
        }

        BeanUtils.copyProperties(sysUser, loginUser);
        loginUser.setIsAdmin(sysRoleMapper.findBySysUserId(loginUser.getId()).stream().map(SysRole::getId).collect(Collectors.toList()).contains(1));
        return loginUser;
    }

    @Override
    public LoginUser getUserById(Integer userId) {
        final SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser != null) {
            return getUserByAccountName(sysUser.getUserAccountName());
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = CommonConstant.PREFIX_ONLINE_USER_INFO, key = "#userAccountName")
    public void deleteByAccountName(String userAccountName) {

    }

    @Override
//	@Cacheable(value = CacheConstant.SYS_DICT_CACHE,key = "#code+':'+#key")
    public String translateDict(String code, String key) {
        if (StringUtil.isNullOrEmpty(key)||"null".equals(key)){
            return "";
        }
        String cacheKey = CacheConstant.SYS_DICT_CACHE + "#" + code + ":" + "#" + key;
        String cacheValue = cacheMap.get(cacheKey);
        if (cacheValue == null) {
            log.info("无缓存dictText的时候调用这里！");
            try {
                cacheValue = sysDictMapper.queryDictTextByKey(code, key);
            }catch (Exception e){
                e.printStackTrace();
                log.error(cacheKey);
            }
            if (cacheValue == null) {
                cacheValue = "";
                return cacheValue;
            }

            cacheMap.put(cacheKey, cacheValue);
        }
        return cacheValue;
    }

    public static ConcurrentHashMap<String, String> cacheMap = new ConcurrentHashMap<>();

    @Override
    public String translateDictFromTable(String table, String text, String code, String key) {
        return sysDictMapper.queryTableDictTextByKey(table, text, code, key);
    }


    @Override
    public List<SysDictItem> getDictItem(String code) {
        SysDict sysDictByCode = sysDictMapper.getSysDictByCode(code);
        if (sysDictByCode == null) {
            return null;
        }

        QueryWrapper<SysDictItem> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("dict_id", sysDictByCode.getId());
        return sysDictItemService.list(itemQueryWrapper);
    }

    @Override
    public List<String> doSearchSql(Integer pageNum, Integer pageSize, String sql) {
        int page = (pageNum - 1) * pageSize;
        String limit = String.format("  LIMIT %s,%s", page, pageSize);
        sql = sql + limit;
        return sysSearchConfigMapper.doSearchSql(sql);
    }

    @Override
    public Long doSearchSqlCount(String sql) {
        sql = String.format("SELECT COUNT(*) FROM (%s) AS  temp", sql);
        return sysSearchConfigMapper.doSearchSqlCount(sql);
    }

    @Override
    public RetData<Boolean> doDuplicateCheck(String tableName, String fieldName, String fieldVal, String tableId) {
        MappingEnum mappingEnum = MappingEnum.getMappingEnum(tableName, fieldName);
        if (mappingEnum != null) {
            if (!StringUtils.isEmpty(tableId)) {
                String idSql = String.format("SELECT %s FROM %s WHERE id = '%s'  ", mappingEnum.getHideFieldName(), mappingEnum.getHideTableName(), tableId);
                String val = sysSearchConfigMapper.doById(idSql);
                if (ObjectUtil.equal(val, fieldVal)) {
                    return RetData.ok(true);
                }
            }
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s'  ", mappingEnum.getHideTableName(), mappingEnum.getHideFieldName(), fieldVal);
            if (sysSearchConfigMapper.doDuplicateCheck(sql) == 0) {
                return RetData.ok(true);
            } else {
                return RetData.ok(false).message("数据已存在");
            }
        }
        return RetData.ok(false).message("查询数据超出范围");
    }


    @Override
    @Cacheable(cacheNames = CommonConstant.PREFIX_ADMIN_USER, key = "#loginUserName")
    public boolean isAdmin(Integer loginUserId, String loginUserName) {
        return sysRoleMapper.findBySysUserId(loginUserId).stream().map(SysRole::getId).collect(Collectors.toList()).contains(1);
    }


    @Override
    @CacheEvict(cacheNames = CommonConstant.PREFIX_ADMIN_USER, key = "#loginUserName")
    public void deleteIsAdmin(Integer loginUserId, String loginUserName) {

    }

    @Override
    public ManageSecurity getManageSecurity() {
        return manageSecurityService.getManageSecurity();
    }

    @Override
    public void logout(String token) {
        sysUserService.logout(token);
    }

    @Override
    public List<SysSearchConfig> getConfigByPageId(String searchCode) {
        return sysSearchConfigMapper.getConfigByPageId(searchCode);
    }

    @Override
    public boolean getSystemAuthStatus() {
       return deviceAuthService.isExpiration();
    }
}