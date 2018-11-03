package com.example.getpostenumparam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
@Configuration
public class SpringParamConvertConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ApplicationContext context;

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        context.getBeansOfType(Converter.class).values().forEach(registry::addConverter);
    }
}
