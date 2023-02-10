package com.zhucan.enums.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhucan.enums.core.mvc.JsonEnumConverter;
import com.zhucan.enums.core.mvc.MvcConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhuCan
 * @description
 * @since 2022-11-09 10:06
 **/
@Configuration
public class EnumsCoreAutoConfiguration {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public JsonEnumConverter jsonEnumConverter(MappingJackson2HttpMessageConverter httpMessageConverter,
            ObjectMapper objectMapper) {
        return new JsonEnumConverter(httpMessageConverter, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    public MvcConfiguration mvcConfiguration(){
        return new MvcConfiguration();
    }
}
