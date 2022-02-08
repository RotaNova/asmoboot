package com.rotanava.boot.system.test;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.rotanava.boot.system.api.SysPermissionAutoGenerateService;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.Map;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-29 15:30
 */
@SpringBootTest
public class SysPermissionAutoGenerate {

    @Autowired
    private ServiceModelToSwagger2Mapper mapper;

    @Autowired
    private DocumentationCache documentationCache;

    @DubboReference
    private SysPermissionAutoGenerateService sysPermissionAutoGenerateService;



    @Test
    public void start() {
        final Map<String, Integer> map = Maps.newHashMap();

        //参数1  为控制层配置的API注解的tags
        //参数2  为该接口属于哪个页面id
        map.put("钉钉对接配置", 1326);
        map.forEach(this::addSysPagePermission);
    }


    /**
     * 功能: 添加页面权限
     * 作者: zjt
     * 日期: 2021/3/29 15:32
     * 版本: 1.0
     */
    public void addSysPagePermission(String checkTag, int sysPageId) {
        final Documentation documentation = documentationCache.documentationByGroup("default");
        Swagger swagger = mapper.mapDocumentation(documentation);
        final Map<String, Path> pathMap = swagger.getPaths();
        final String pathMapStr = JSONUtil.toJsonStr(pathMap);
        sysPermissionAutoGenerateService.addSysPagePermission(checkTag, sysPageId, pathMapStr);
    }


}