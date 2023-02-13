package com.zhucan.enums.core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhucan.enums.core.serializer.JsonEnumDeserializer;
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

    private MappingJackson2HttpMessageConverter httpMessageConverter;
    private ObjectMapper objectMapper;

    public JsonEnumConverter(MappingJackson2HttpMessageConverter httpMessageConverter, ObjectMapper objectMapper) {
        this.httpMessageConverter = httpMessageConverter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Enum.class, new JsonEnumDeserializer());

        if (objectMapper != null){
            objectMapper.registerModule(simpleModule);
        }

        if (httpMessageConverter != null) {
            objectMapper = httpMessageConverter.getObjectMapper();
        }

        objectMapper.registerModule(simpleModule);
        httpMessageConverter.setObjectMapper(objectMapper);
    }

}
