package com.zhucan.enums.scanner;

import com.zhucan.enums.scanner.cached.EnumCache;
import com.zhucan.enums.scanner.cached.MemoryEnumCache;
import com.zhucan.enums.scanner.handler.EnumScanHandler;
import com.zhucan.enums.scanner.handler.EnumScanHandlerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhuCan
 * @date: 2020/7/9 10:34
 * @description: starter 的自动装配类
 */
@Configuration
public class CodeEnumAutoConfiguration {

  @Bean
  public EnumScanHandler enumTable() {
    return new EnumScanHandlerImpl();
  }

  /**
   * 默认的内存缓存,当用户重写了一个enumCache并注册为容器时,可覆盖本默认缓存
   *
   * @return
   */
  @Bean
  @ConditionalOnMissingBean(EnumCache.class)
  public EnumCache enumCache() {
    return new MemoryEnumCache();
  }
}
