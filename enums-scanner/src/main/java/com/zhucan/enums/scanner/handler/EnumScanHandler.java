package com.zhucan.enums.scanner.handler;

import com.zhucan.enums.scanner.dto.CodeTable;

import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/7/9 11:48
 * @description: 获取枚举码表的接口
 */
public interface EnumScanHandler {

  /**
   * 获取所有的枚举接口
   *
   * @return
   */
  List<CodeTable> codeTables();

}