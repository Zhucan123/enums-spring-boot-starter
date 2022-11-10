package com.zhucan.enums.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhucan.enums.core.mvc.JsonEnumConverter;
import com.zhucan.enums.core.mvc.MvcConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhuCan
 * @description
 * @since 2022-11-09 10:06
 **/
@Configuration
public class EnumsCoreAutoConfiguration {

    @Bean
    @DependsOn("objectMapper")
    @ConditionalOnBean(ObjectMapper.class)
    public JsonEnumConverter jsonEnumConverter() {
        return new JsonEnumConverter();
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    public MvcConfiguration mvcConfiguration(){
        return new MvcConfiguration();
    }
}
