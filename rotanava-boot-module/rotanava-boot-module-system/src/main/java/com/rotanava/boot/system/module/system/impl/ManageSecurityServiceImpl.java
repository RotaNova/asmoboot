package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.module.constant.LoginAccountPassOutOn;
import com.rotanava.boot.system.api.module.constant.LoginLockoutStrategyOn;
import com.rotanava.boot.system.api.module.constant.SysServiceType;
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

import javax.annotation.PostConstruct;
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
            if (manageSecurityDTO.getAccountPassOutOn() == null) {
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

        Map<String, Object> securityManageMap = BeanUtil.beanToMap(manageSecurityDTO, Boolean.TRUE, Boolean.TRUE);
        sysServiceSettingMapper.updateManageSecurityByMap(securityManageMap);
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
