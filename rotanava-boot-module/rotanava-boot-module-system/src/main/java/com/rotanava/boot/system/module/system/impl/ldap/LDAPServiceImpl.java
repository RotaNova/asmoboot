package com.rotanava.boot.system.module.system.impl.ldap;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.rotanava.boot.system.api.LDAPService;
import com.rotanava.boot.system.api.SysUserService;
import com.rotanava.boot.system.api.module.constant.LdapEnum;
import com.rotanava.boot.system.api.module.constant.MqttSetEnum;
import com.rotanava.boot.system.api.module.constant.SysServiceType;
import com.rotanava.boot.system.api.module.constant.UserSex;
import com.rotanava.boot.system.api.module.system.bean.LDAPUser;
import com.rotanava.boot.system.api.module.system.bo.SysServiceSetting;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateLdapDTO;
import com.rotanava.boot.system.api.module.system.dto.ldap.LdapTestLoginDTO;
import com.rotanava.boot.system.api.module.system.vo.GetLdapVO;
import com.rotanava.boot.system.module.dao.SysServiceSettingMapper;
import com.rotanava.boot.system.module.dao.SysUserMapper;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.SysErrorCode;
import com.rotanava.framework.util.BaseUtil;
import com.rotanava.framework.util.Date8Util;
import com.rotanava.framework.util.StringUtil;
import com.rotanava.framework.util.SysUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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
@Log4j2
public class LDAPServiceImpl implements LDAPService, InitializingBean {

    @Autowired
    private SysServiceSettingMapper sysServiceSettingMapper;

    @Autowired
    private LDAPUtil ldapUtil;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    public static final String LDAP_PRE_CODE = "[LDAP]";

    @Override
    public void afterPropertiesSet() {
        try {
            GetLdapVO ldap = getLdap();
            if (StringUtil.isNullOrEmpty(ldap.getLdapAddress())) {
                return;
            }
            if (StringUtil.isNullOrEmpty(ldap.getLdapBindDn())) {
                return;
            }
            if (StringUtil.isNullOrEmpty(ldap.getLdapUserOu())) {
                return;
            }
            ldapUtil.resetLdapTemplate(ldap.getLdapAddress(), "", ldap.getLdapBindDn(), ldap.getLdapPassword());
            log.info("加载ldap信息{}", ldap);
        }catch (Exception e){
            log.error("加载ldap信息异常",e);
        }
    }

    @Override
    public GetLdapVO getLdap() {
        List<SysServiceSetting> sysServiceSettings = sysServiceSettingMapper.findBySysServiceType(SysServiceType.LDAP.getType());
        Map<String, String> securityManageMap = sysServiceSettings.stream().collect(Collectors.toMap(SysServiceSetting::getSysServiceCode, SysServiceSetting::getSysServiceValue));
        return BeanUtil.mapToBean(securityManageMap, GetLdapVO.class, Boolean.TRUE);
    }

    @Override
    public void updateLdap(UpdateLdapDTO updateLdapDTO) {
        Map<String, Object> map = BeanUtil.beanToMap(updateLdapDTO, Boolean.TRUE, Boolean.FALSE);

        List<String> codes = sysServiceSettingMapper.findSysServiceCodeBySysServiceCodeIn(map.keySet());
        List<SysServiceSetting> sysServiceSettings = new ArrayList<>();

        for (LdapEnum ldapEnum : LdapEnum.values()) {
            final String sysServiceCode = ldapEnum.getSysServiceCode();
            final String sysServiceValue = Convert.toStr(map.get(sysServiceCode));
            if (!codes.contains(sysServiceCode)) {
                SysServiceSetting sysServiceSetting = new SysServiceSetting();
                sysServiceSetting.setSysServiceCode(ldapEnum.getSysServiceCode());
                sysServiceSetting.setSysServiceName(ldapEnum.getSysServiceName());
                sysServiceSetting.setSysServiceType(ldapEnum.getSysServiceType());
                sysServiceSetting.setSysServiceValue(!StringUtils.isEmpty(sysServiceValue) ? sysServiceValue : ldapEnum.getSysServiceDefaultValue());
                sysServiceSetting.setSysServiceDefaultValue(ldapEnum.getSysServiceDefaultValue());
                sysServiceSetting.setSysServiceDescription(ldapEnum.getSysServiceDescription());
                sysServiceSettings.add(sysServiceSetting);
                map.remove(sysServiceCode);
            }else {
                map.put(sysServiceCode,!StringUtils.isEmpty(sysServiceValue) ? sysServiceValue : ldapEnum.getSysServiceDefaultValue());
            }
        }

        if(!CollectionUtils.isEmpty(sysServiceSettings)){
            sysServiceSettingMapper.insertList(sysServiceSettings);
        }

        if(MapUtil.isNotEmpty(map)){
            sysServiceSettingMapper.updateLdapByMap(map);
        }

        ldapUtil.resetLdapTemplate(updateLdapDTO.getLdapAddress(), "", updateLdapDTO.getLdapBindDn(), updateLdapDTO.getLdapPassword());
    }


    @Override
    public void ldapUserImport(){
        GetLdapVO ldapConfig = getLdap();
        getLDAPList(ldapConfig.getLdapUserOu());
    }

    private void getLDAPList(String ldapUserOu) {
        String[] userOuArray = ldapUserOu.split("\\|");

        List<JSONObject> ldapUserJsonList = new ArrayList<>();

        for (String base : userOuArray) {
            List<JSONObject> userListByBase = ldapUtil.getUserListByBase(base);
            ldapUserJsonList.addAll(userListByBase);
        }

        List<LDAPUser> ldapUserList = parseLdapAttributeMapping(ldapUserJsonList);

        for (LDAPUser ldapUser : ldapUserList) {
            try {
                SysUser sysUser = sysUserMapper.findByUserAccountName(ldapUser.getUserAccountName());
                if (sysUser != null) {
                    if (!sysUser.getUserCode().contains(LDAP_PRE_CODE)) {
                        //删除
                        sysUserMapper.deleteById(sysUser.getId());
                        sysUser = null;
                    } else {
                        //更新
                    }
                }
                if (sysUser == null) {
                    //新增
                    sysUser = new SysUser();
                    sysUser.setUserAccountName(ldapUser.getUserAccountName());
                    sysUser.setUserName(ldapUser.getUserName());
                    sysUser.setCreateTime(new Date());
                    final AddSysUserDTO sysUserDTO = new AddSysUserDTO();
                    sysUserDTO.setUserName(ldapUser.getUserName());
                    sysUserDTO.setUserAccountName(ldapUser.getUserAccountName());
                    sysUserDTO.setUserPassword("RN123456");
                    sysUserDTO.setUserSex(UserSex.DO_NOT_DISCLOSE.getSex());
                    sysUserDTO.setUserCode(getLdapPreCode(ldapUser.getUserAccountName()));
                    sysUserDTO.setUserValidTime(Date8Util.parse("2099-12-31", Date8Util.DATE).getTime());
                    sysUserDTO.setUserSysrole(0);
                    sysUserDTO.setUserStatus(1);
                    Integer currentReqUserId = SysUtil.getCurrentReqUserId();
                    final Integer sysUserId = sysUserService.addSysUser(sysUserDTO, null, currentReqUserId, false);
                }
            }catch (Exception e){
                log.error("添加ldap用户{}失败",ldapUser.getUserAccountName(),e);
            }

        }

    }

    /**
     * @description :获取ldap用户编码
     * @Author : richenLi
     * @version :  1.0
     */
    private String getLdapPreCode(Object code) {
        return LDAP_PRE_CODE + code;
    }

    private List<LDAPUser> parseLdapAttributeMapping(List<JSONObject> ldapUserJsonList) {

        GetLdapVO ldapConfig = getLdap();
        JSONObject jsonObject = JSONObject.parseObject(ldapConfig.getLdapAttributeMapping());
        List<LDAPUser> ldapUserList = new ArrayList<>();
        for (JSONObject ldapJsonUser : ldapUserJsonList) {
            LDAPUser ldapUser = new LDAPUser();
            ldapUser.setUserName(ldapJsonUser.getString(jsonObject.getString("username")));
            ldapUser.setUserAccountName(ldapJsonUser.getString(jsonObject.getString("name")));
            if (jsonObject.containsKey("sex")) {
                ldapUser.setSex(ldapJsonUser.getIntValue(jsonObject.getString("sex")));
            }
            ldapUserList.add(ldapUser);
        }
        return ldapUserList;

    }


    @Override
    public void testConnection(UpdateLdapDTO updateLdapDTO) {
        boolean connection = ldapUtil.testConnection(updateLdapDTO.getLdapAddress(), "", updateLdapDTO.getLdapBindDn(), updateLdapDTO.getLdapPassword());
        if (!connection) {
            throw new CommonException(SysErrorCode.SYS_ERROR_12);
        }
    }

    @Override
    public boolean LDAPLogin(LdapTestLoginDTO ldapTestLoginDTO) {
        GetLdapVO ldapConfig = getLdap();
        String[] userOuArray = ldapConfig.getLdapUserOu().split("\\|");
        for (String base : userOuArray) {
            boolean authenticate = ldapUtil.authenticate(ldapTestLoginDTO.getUserName(), ldapTestLoginDTO.getPassword(), base);
            if (authenticate) {
                return true;
            }
        }
        return false;


    }


}
