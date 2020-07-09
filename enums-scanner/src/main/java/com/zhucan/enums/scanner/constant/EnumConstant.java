package com.zhucan.enums.scanner.constant;

/**
 * @author: zhuCan
 * @date: 2020/7/9 10:24
 * @description: 枚举所需要的一些常量属性
 */
public interface EnumConstant {

  /**
   * 通过processor扫描enum生成的classpath文件的文件名
   */
  String ENUM_FILE_NAME = "enum_classpath_cached";

  /**
   * 缓存枚举码表的可以
   */
  String ENUM_CACHE_KEY = "enum_cache_key";
}
