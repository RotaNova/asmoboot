package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.module.constant.*;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.dto.ManageSecurityDTO;
import com.rotanava.boot.system.api.module.system.vo.ManageSecurityVO;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.BuildUtil;
import com.rotanava.framework.util.IPUtils;
import com.rotanava.framework.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 管理安全服务impl
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@Service
public class ManageSecurityServiceImpl implements ManageSecurityService {

    @Autowired
    private SysServiceSettingMapper sysServiceSettingMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ManageSecurity getManageSecurity() {
        if (redisUtil.hasKey(CommonConstant.PREFIX_MANAGE_SECURITY)) {
            String prefixManageSecurity = Convert.toStr(redisUtil.get(CommonConstant.PREFIX_MANAGE_SECURITY));
            return JSONUtil.toBean(prefixManageSecurity,ManageSecurity.class);
        }
        return updateManageSecurity();
    }

    @Override
    public BaseVO<ManageSecurityVO> getManageSecurityVO() {

        ManageSecurityVO manageSecurityVO = new ManageSecurityVO();
        BeanUtils.copyProperties(getManageSecurity(), manageSecurityVO);
        return BuildUtil.buildListVO(manageSecurityVO);
    }

    @Override
    public void updateManageSecurity(ManageSecurityDTO manageSecurityDTO) {

        ManageSecurity manageSecurity = getManageSecurity();

        if (LoginAccountPassOutOn.TURN_ON.getOn().equals(manageSecurityDTO.getAccountPassOutOn())) {
            if (manageSecurityDTO.getAccountPassOutMins() == null) {
                Optional.ofNullable(manageSecurity.getAccountPassOutMins()).ifPresent(manageSecurityDTO::setAccountPassOutMins);
            }
        }

        if (LoginLockoutStrategyOn.TURN_ON.getOn().equals(manageSecurityDTO.getAccountLockoutStrategyOn())) {
            if (manageSecurityDTO.getAccountLockoutStrategyMins() == null) {
                Optional.ofNullable(manageSecurity.getAccountLockoutStrategyMins()).ifPresent(manageSecurityDTO::setAccountLockoutStrategyMins);
            }
            if (manageSecurityDTO.getAccountLockoutStrategyFrequency() == null) {
                Optional.ofNullable(manageSecurity.getAccountLockoutStrategyFrequency()).ifPresent(manageSecurityDTO::setAccountLockoutStrategyFrequency);
            }
        }

        List<String> adminIps = StrUtil.split(manageSecurityDTO.getAdminLoginIpFiltering(), ",", -1, true, true);
        IPUtils.checkNetworkSegment(adminIps);
        manageSecurityDTO.setAdminLoginIpFiltering(String.join(",", adminIps));

        List<String> memberIps = StrUtil.split(manageSecurityDTO.getMemberLoginIpFiltering(), ",", -1, true, true);
        IPUtils.checkNetworkSegment(memberIps);
        manageSecurityDTO.setMemberLoginIpFiltering(String.join(",", memberIps));

        Map<String, Object> map = BeanUtil.beanToMap(manageSecurityDTO, Boolean.TRUE, Boolean.FALSE);
        List<String> codes = sysServiceSettingMapper.findSysServiceCodeBySysServiceCodeIn(map.keySet());
        List<SysServiceSetting> sysServiceSettings = new ArrayList<>();
        for (SecurityEnum securityEnum : SecurityEnum.values()) {
            final String sysServiceCode = securityEnum.getSysServiceCode();
            final String sysServiceValue = Convert.toStr(map.get(sysServiceCode));
            if (!codes.contains(sysServiceCode)) {
                SysServiceSetting sysServiceSetting = new SysServiceSetting();
                sysServiceSetting.setSysServiceCode(securityEnum.getSysServiceCode());
                sysServiceSetting.setSysServiceName(securityEnum.getSysServiceName());
                sysServiceSetting.setSysServiceType(securityEnum.getSysServiceType());
                sysServiceSetting.setSysServiceValue(!StringUtils.isEmpty(sysServiceValue) ? sysServiceValue : securityEnum.getSysServiceDefaultValue());
                sysServiceSetting.setSysServiceDefaultValue(securityEnum.getSysServiceDefaultValue());
                sysServiceSetting.setSysServiceDescription(securityEnum.getSysServiceDescription());
                sysServiceSettings.add(sysServiceSetting);
                map.remove(sysServiceCode);
            }else {
                map.put(sysServiceCode,!StringUtils.isEmpty(sysServiceValue) ? sysServiceValue : securityEnum.getSysServiceDefaultValue());
            }
        }

        if(!CollectionUtils.isEmpty(sysServiceSettings)){
            sysServiceSettingMapper.insertList(sysServiceSettings);
        }

        if(MapUtil.isNotEmpty(map)){
            sysServiceSettingMapper.updateManageSecurityByMap(map);
        }

        updateManageSecurity();
    }

    @PostConstruct
    @Override
    public ManageSecurity updateManageSecurity() {

        List<SysServiceSetting> sysServiceSettings = sysServiceSettingMapper.findBySysServiceType(SysServiceType.SECURITY_MANAGE.getType());
        Map<String, String> securityManageMap = sysServiceSettings.stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, SysServiceSetting::getSysServiceValue));
        ManageSecurity manageSecurity = BeanUtil.mapToBean(securityManageMap, ManageSecurity.class, Boolean.TRUE);
        redisUtil.set(CommonConstant.PREFIX_MANAGE_SECURITY, JSONUtil.toJsonStr(manageSecurity));
        return manageSecurity;
    }


}
