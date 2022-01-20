package com.rotanava.boot.system.test;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.rotanava.boot.system.api.SysPermissionAutoGenerateService;
import com.rotanava.framework.common.oss.FileUploadUtil;
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


    @Autowired
    FileUploadUtil fileUploadUtil;

    @Test
    public void testminio() {
        fileUploadUtil.setBucketPolicyReadOnly("rn-platform-errorface-resources");

    }
    @Test
    public void start() {
        final Map<String, Integer> map = Maps.newHashMap();
//        map.put("网络配置", 5);
//        map.put("用户管理", 16);
//        map.put("角色管理", 15);
//        map.put("部门管理", 17);
//        map.put("通知消息", 19);
//        map.put("系统消息", 20);
//        map.put("告警消息", 21);
//        map.put("应用注册", 275);
//        map.put("API管理", 276);
//        map.put("我的消息", 26);
//        map.put("消息接收配置", 27);
//        map.put("消息通知配置", 108);
//        map.put("LDAP", 273);
        map.put("钉钉对接配置", 1326);
//        map.put("表格管理", 1);
//        map.put("升级维护",23);
//        map.put("页面管理",28);

//         map.put("数据源管理",1135);
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