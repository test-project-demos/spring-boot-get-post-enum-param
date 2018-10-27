package com.example.getpostenumparam.config;

import com.example.getpostenumparam.spring.deserializer.GenderEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
@Configuration
public class SpringParamConvertConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);

        registry.addConverter(new GenderEnumConverter());
    }
}
