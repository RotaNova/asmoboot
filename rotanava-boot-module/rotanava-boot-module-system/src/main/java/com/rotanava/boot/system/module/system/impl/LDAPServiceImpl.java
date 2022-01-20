package com.rotanava.boot.system.module.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.rotanava.boot.system.api.LDAPService;
import com.rotanava.boot.system.api.module.constant.SysServiceType;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.dto.UpdateLdapDTO;
import com.rotanava.boot.system.api.module.system.vo.GetLdapVO;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ldap服务
 *
 * @author WeiQiangMiao
 * @date 2021-04-23
 */
@Service
public class LDAPServiceImpl implements LDAPService {

    @Autowired
    private SysServiceSettingMapper sysServiceSettingMapper;

    @Override
    public GetLdapVO getLdap() {
        List<SysServiceSetting> sysServiceSettings = sysServiceSettingMapper.findBySysServiceType(SysServiceType.LDAP.getType());
        Map<String, String> securityManageMap = sysServiceSettings.stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, SysServiceSetting::getSysServiceValue));
        return BeanUtil.mapToBean(securityManageMap, GetLdapVO.class, Boolean.TRUE);
    }

    @Override
    public void updateLdap(UpdateLdapDTO updateLdapDTO) {
        Map<String, Object> securityManageMap = BeanUtil.beanToMap(updateLdapDTO, Boolean.TRUE, Boolean.TRUE);
        sysServiceSettingMapper.updateLdapByMap(securityManageMap);
    }
}
