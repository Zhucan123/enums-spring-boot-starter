package com.zhucan.enums.conversion.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author: zhuCan
 * @date: 19:55
 * @description: 重写枚举的反序列化
 */
public class JsonEnumConverter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MappingJackson2HttpMessageConverter httpMessageConverter;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (httpMessageConverter != null) {
            objectMapper = httpMessageConverter.getObjectMapper();
        }

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Enum.class, new JsonEnumDeserializer());
        objectMapper.registerModule(simpleModule);
        httpMessageConverter.setObjectMapper(objectMapper);
    }

    @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
