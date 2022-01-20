package com.rotanava.boot.system.module.system.impl;


import com.rotanava.boot.system.api.SysPageModuleTypeService;
import com.rotanava.boot.system.api.module.system.bo.SysPageModuleType;
import com.rotanava.boot.system.api.module.system.vo.SysPageModuleTypeVO;
import com.rotanava.boot.system.module.dao.SysPageModuleTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjt
 * @date 2021-04-16
 */
@Service
public class SysPageModuleTypeServiceImpl implements SysPageModuleTypeService {

    @Autowired
    private SysPageModuleTypeMapper sysPageModuleTypeMapper;


    @Override
    public List<SysPageModuleTypeVO> getSysPageModuleTypeVO() {
        final List<SysPageModuleType> sysPageModuleTypeList = sysPageModuleTypeMapper.selectList(null);
        return sysPageModuleTypeList.stream().map(sysPageModuleType -> {
            final SysPageModuleTypeVO sysPageModuleTypeVO = new SysPageModuleTypeVO();
            sysPageModuleTypeVO.setSysPageModuleTypeId(sysPageModuleType.getId());
            sysPageModuleTypeVO.setSysPageModuleTypeName(sysPageModuleType.getName());
            return sysPageModuleTypeVO;
        }).collect(Collectors.toList());
    }
    
}
