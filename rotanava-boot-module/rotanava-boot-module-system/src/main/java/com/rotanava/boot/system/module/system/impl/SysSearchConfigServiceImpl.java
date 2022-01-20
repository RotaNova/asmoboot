package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rotanava.boot.system.api.SysSearchConfigService;
import com.rotanava.framework.model.bo.SysSearchConfig;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-16 17:32
 **/
@Service
public class SysSearchConfigServiceImpl extends ServiceImpl<SysSearchConfigMapper, SysSearchConfig> implements SysSearchConfigService {
}
