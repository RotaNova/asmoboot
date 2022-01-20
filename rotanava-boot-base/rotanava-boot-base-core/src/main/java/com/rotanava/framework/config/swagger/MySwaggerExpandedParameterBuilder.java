package com.rotanava.framework.config.swagger;

import com.google.common.base.Optional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-04-20 15:49
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class MySwaggerExpandedParameterBuilder implements ExpandedParameterBuilderPlugin {

    @Override
    public void apply(ParameterExpansionContext context) {
        final Optional<NotNull> notNullOptional = context.findAnnotation(NotNull.class);
        if (notNullOptional.isPresent()) {
            context.getParameterBuilder().required(true);
        }

        final Optional<NotEmpty> notEmptyOptional = context.findAnnotation(NotEmpty.class);
        if (notEmptyOptional.isPresent()) {
            context.getParameterBuilder().required(true);
        }

        final Optional<NotBlank> notBlankOptional = context.findAnnotation(NotBlank.class);
        if (notBlankOptional.isPresent()) {
            context.getParameterBuilder().required(true);
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return SwaggerPluginSupport.pluginDoesApply(documentationType);
    }
}