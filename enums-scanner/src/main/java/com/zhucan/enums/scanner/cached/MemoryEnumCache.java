package com.zhucan.enums.scanner.cached;

import com.zhucan.enums.scanner.constant.EnumConstant;
import com.zhucan.enums.scanner.dto.CodeTable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zhuCan
 * @date: 2020/7/9 10:32
 * @description: 默认的枚举缓存实现类, 把枚举数据缓存到本地内存中
 * 可以通过重写一个实现enumCache 的容器类来覆盖默认缓存
 */
public class MemoryEnumCache implements EnumCache {

  private static Map<String, Object> cache = new ConcurrentHashMap<>();

  @Override
  public void write(List<CodeTable> codeEnums) {
    cache.put(EnumConstant.ENUM_CACHE_KEY, codeEnums);
  }

  @Override
  public List<CodeTable> read() {
    return (List<CodeTable>) cache.get(EnumConstant.ENUM_CACHE_KEY);
  }
}
