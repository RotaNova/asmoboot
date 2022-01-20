package com.rotanava.boot.system.test;

import cn.hutool.json.JSONUtil;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.rotanava.boot.system.api.ManagePermissionService;
import com.rotanava.boot.system.module.dao.SysApiPermissionMapper;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.HostNameProvider;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;
import static springfox.documentation.swagger.common.HostNameProvider.componentsFrom;

/**
 * @description: controller 自动生成rap2
 * @author: jintengzhou
 * @date: 2021-03-29 15:30
 */
@SpringBootTest
public class Rap2AutoGenerate {

    @Autowired
    private SysApiPermissionMapper sysApiPermissionMapper;

    @Autowired
    private ManagePermissionService managePermissionService;

    @Autowired
    private ServiceModelToSwagger2Mapper mapper;

    @Autowired
    private DocumentationCache documentationCache;


    //填写 controller 的 @Api(tags="") 的值
    public static final String CHECKTAG = "应用注册";

    //接口作者
    public static final String AUTHOR = "周金滕";


    @Test
    public void test() {
        final Documentation documentation = documentationCache.documentationByGroup("default");


        Swagger swagger = mapper.mapDocumentation(documentation);
        final Map<String, Path> pathMap = swagger.getPaths();
        System.out.println(JSONUtil.toJsonStr(swagger));
        final Map<String, Path> pathMapCopy = Maps.newHashMap();

        pathMap.forEach((url, path) -> {
            Operation operation = null;

            final Operation delete = path.getDelete();
            final Operation get = path.getGet();
            final Operation post = path.getPost();
            final Operation put = path.getPut();

            if (delete != null) {
                operation = delete;
            } else if (get != null) {
                operation = get;
            } else if (post != null) {
                operation = post;
            } else if (put != null) {
                operation = put;
            }


            if (operation != null) {
                final List<String> tags = operation.getTags();
                final String tag = tags.get(0);
                if (CHECKTAG.equals(tag)) {
                    pathMapCopy.put(url, path);
                }
            }
        });

        swagger.setPaths(pathMapCopy);

        final List<Tag> swaggerTags = swagger.getTags();
        for (Tag swaggerTag : swaggerTags) {
            if (swaggerTag.getName().equals(CHECKTAG)) {
                swaggerTags.clear();
                swaggerTags.add(swaggerTag);
                break;
            }
        }

//        UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest, swagger.getBasePath());
//        swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
//        if (Strings.isNullOrEmpty(swagger.getHost())) {
//            swagger.host("");
//        }
        swagger.host("");

        System.out.println(JSONUtil.toJsonStr(swagger));


    }
}