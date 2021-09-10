package com.zhucan.enums.scanner.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zhuCan
 * @description 扩展 spring 资源扫描器
 * @testIgnore Class 类没办法 Mock ，无法使其抛出异常
 * @since 2021-01-11 10:48
 **/
public class ExtensionClassPathScanningCandidateComponentProvider extends ClassPathScanningCandidateComponentProvider implements ResourcesScanner<Class<?>> {

    private final Logger log = LoggerFactory.getLogger(ExtensionClassPathScanningCandidateComponentProvider.class);

    private final EnumScanProperties properties;

    private ApplicationContext context;


    public ExtensionClassPathScanningCandidateComponentProvider(boolean useDefaultFilters,
                                                                Consumer<ExtensionClassPathScanningCandidateComponentProvider> consumer,
                                                                EnumScanProperties properties, ApplicationContext context) {
        super(useDefaultFilters);
        this.properties = properties;
        this.context = context;
        consumer.accept(this);
    }


    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();

        return metadata.isIndependent();
    }

    @Override
    public List<Class<?>> classScan() {

        Set<BeanDefinition> candidateComponents = new HashSet<>();

        setResourceLoader(context);

        properties.getScanPackages().forEach(x -> candidateComponents.addAll(findCandidateComponents(x)));

        return candidateComponents.stream().map(x -> {
            try {
                return Class.forName(x.getBeanClassName());
            } catch (ClassNotFoundException e) {
                log.error("扫描资源, 执行 class 加载异常", e);
            }
            return null;
        }).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }


}
