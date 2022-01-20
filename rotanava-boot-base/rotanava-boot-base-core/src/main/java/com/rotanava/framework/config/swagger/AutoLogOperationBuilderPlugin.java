package com.rotanava.framework.config.swagger;

import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;

/**
 * @description: 如果有autoLog标注没有ApiOperation 取 @autolog 的数据
 * @author: jintengzhou
 * @date: 2021-03-29 16:19
 */
@Slf4j
@Component
@Order(-999)
public class AutoLogOperationBuilderPlugin implements OperationBuilderPlugin {
    @Override
    public void apply(OperationContext context) {

        final List<AutoLog> autoLogList = context.findAllAnnotations(AutoLog.class);
        final List<ApiOperation> apiOperationList = context.findAllAnnotations(ApiOperation.class);
        final OperationBuilder operationBuilder = context.operationBuilder();
        if (apiOperationList.isEmpty() && autoLogList.size() >= 1) {
            final AutoLog autoLog = autoLogList.get(0);
            operationBuilder.summary(autoLog.value());
        }

        if (autoLogList.size() >= 1){
            final AutoLog autoLog = autoLogList.get(0);
            List<VendorExtension> extensions = Lists.newArrayList();
            Integer type = autoLog.operateType().getType();
            if (type.equals(OperateTypeEnum.SELECT.getType())) {
                type = 3;
            } else if (type.equals(OperateTypeEnum.DELETE.getType())) {
                type = 1;
            } else if (type.equals(OperateTypeEnum.ADD.getType())) {
                type = 0;
            } else if (type.equals(OperateTypeEnum.UPDATE.getType())) {
                type = 2;
            }
            extensions.add(new StringVendorExtension("operateType", Convert.toStr(type)));
            operationBuilder.extensions(extensions);
        }

    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return SwaggerPluginSupport.pluginDoesApply(documentationType);
    }
}