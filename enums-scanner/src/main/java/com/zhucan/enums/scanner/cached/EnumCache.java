package com.zhucan.enums.scanner.cached;


import com.zhucan.enums.scanner.dto.CodeTable;

import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/1/16 13:45
 * @description: 缓存枚举的接口
 */
public interface EnumCache {

  /**
   * 写入缓存
   *
   * @param codeEnums
   */
  void write(List<CodeTable> codeEnums);

  /**
   * 从缓存中路读取所有的枚举数据
   *
   * @return
   */
  List<CodeTable> read();

}
