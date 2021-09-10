package com.zhucan.enums.scanner;

import com.google.common.collect.Lists;
import com.zhucan.enums.scanner.annotation.EnumScan;
import com.zhucan.enums.scanner.cached.EnumCache;
import com.zhucan.enums.scanner.cached.MemoryEnumCache;
import com.zhucan.enums.scanner.context.EnumScanProperties;
import com.zhucan.enums.scanner.context.ExtensionClassPathScanningCandidateComponentProvider;
import com.zhucan.enums.scanner.context.ResourcesScanner;
import com.zhucan.enums.scanner.context.TypeFilterProvider;
import com.zhucan.enums.scanner.handler.EnumScanHandler;
import com.zhucan.enums.scanner.handler.EnumScanHandlerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/7/9 10:34
 * @description: starter 的自动装配类
 */
@EnableConfigurationProperties(EnumScanProperties.class)
@Configuration
public class CodeEnumAutoConfiguration {

    @Bean
    public EnumScanHandler enumTable(EnumCache cache,
                                     ResourcesScanner<Class<?>> scanner) {
        return new EnumScanHandlerImpl(cache, scanner);
    }

    /**
     * 默认的内存缓存,当用户重写了一个enumCache并注册为容器时,可覆盖本默认缓存
     *
     * @return 枚举缓存
     */
    @Bean
    @ConditionalOnMissingBean(EnumCache.class)
    public EnumCache enumCache() {
        return new MemoryEnumCache();
    }

    /**
     * 资料扫描器
     *
     * @param properties         配置
     * @param typeFilterProvider 扫描器过滤条件提供者
     * @return scanner
     */
    @Bean
    @ConditionalOnMissingBean(ResourcesScanner.class)
    public ResourcesScanner<Class<?>> resourcesScanner(EnumScanProperties properties,
                                                       TypeFilterProvider typeFilterProvider,
                                                       ApplicationContext context) {


        return new ExtensionClassPathScanningCandidateComponentProvider(false, x -> {

            List<TypeFilter> filters = typeFilterProvider.filter();

            if (filters != null) {
                // 增加扫描过滤器
                filters.forEach(x::addIncludeFilter);
            }

        }, properties, context);
    }

    /**
     * 扫描器过滤条件提供者
     *
     * @return 扫描器过滤条件提供者
     */
    @Bean
    @ConditionalOnMissingBean(TypeFilterProvider.class)
    public TypeFilterProvider typeFilterProvider() {
        // 设置默认的 filter
        return () -> Lists.newArrayList(new AnnotationTypeFilter(EnumScan.class));
    }


}
