package com.zhucan.enums.conversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhucan.enums.conversion.serializer.JsonEnumConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.persistence.AttributeConverter;

/**
 * @author: zhuCan
 * @date: 2020/7/10 17:17
 * @description:
 */
@Configuration
@ConditionalOnClass({AttributeConverter.class})
@Import({ConverterPackageScan.class})
public class EnumsConversionAutoConfiguration {


    @Bean
    @DependsOn("objectMapper")
    @ConditionalOnBean(ObjectMapper.class)
    public JsonEnumConverter jsonEnumConverter() {
        return new JsonEnumConverter();
    }
}
