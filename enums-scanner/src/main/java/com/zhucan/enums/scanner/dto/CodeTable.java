package com.zhucan.enums.scanner.dto;

import com.zhucan.enums.scanner.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/7/9 11:25
 * @description: 枚举码表缓存的数据结构
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeTable {

  /**
   * 枚举名称
   */
  private String enumName;

  /**
   * 枚举里面的所有枚举值
   */
  private List<CodeItem> items;

  /**
   * 默认的枚举值,默认是值为1的对象
   */
  private CodeEnum defaultItem;

  /**
   * 枚举的classPath,用来区分同名的枚举
   */
  private String classPath;

}
