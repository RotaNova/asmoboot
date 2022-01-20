package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.vo.SysPageModuleTypeVO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @description: 页面模块类型
 * @author: jintengzhou
 * @date: 2021-03-04 11:36
 */
@Validated
public interface SysPageModuleTypeService {

    /**
     * 功能: 获取页面模块类型
     * 作者: zjt
     * 日期: 2021/4/16 10:46
     * 版本: 1.0
     */
    List<SysPageModuleTypeVO> getSysPageModuleTypeVO();

}