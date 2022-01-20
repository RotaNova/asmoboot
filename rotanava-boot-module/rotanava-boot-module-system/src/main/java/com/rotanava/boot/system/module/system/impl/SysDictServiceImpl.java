package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rotanava.boot.system.api.SysDictService;
import com.rotanava.framework.model.SysDict;
import com.rotanava.boot.system.module.dao.SysDictMapper;
import org.springframework.stereotype.Service;

/**
 * @description: 数据字典实现类
 * @author: richenLi
 * @create: 2021-03-08 17:36
 **/
@Service
public class SysDictServiceImpl  extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
