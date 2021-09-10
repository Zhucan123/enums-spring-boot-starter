package com.zhucan.enums.scanner.context;

import org.springframework.core.type.filter.TypeFilter;

import java.util.List;

/**
 * @author zhuCan
 * @description 过滤器
 * @since 2021-09-09 14:26
 **/
public interface TypeFilterProvider {

    /**
     * 提供过滤器
     *
     * @return filter
     */
    List<TypeFilter> filter();
}
