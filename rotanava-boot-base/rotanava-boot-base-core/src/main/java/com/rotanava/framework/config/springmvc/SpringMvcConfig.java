package com.rotanava.framework.config.springmvc;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-09-09 17:53
 */
@Configuration
public class SpringMvcConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("4GB"));
        return factory.createMultipartConfig();
    }

}