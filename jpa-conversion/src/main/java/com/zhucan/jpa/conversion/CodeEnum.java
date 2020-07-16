package com.zhucan.jpa.conversion;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author: zhuCan
 * @date: 2020/1/13 13:43
 * @description: 枚举需要实现的接口, 用来获取 code 和 name 的规范
 */
public interface CodeEnum {

  /**
   * 枚举的 code 值
   *
   * @return
   */
  @JsonValue
  Integer code();

  /**
   * 枚举的属性
   *
   * @return
   */
  String value();

  /**
   * 通过code 获取枚举值
   *
   * @param enumType
   * @param i
   * @param <EnumType>
   * @return
   */
  static <EnumType extends CodeEnum> EnumType valueOf(Class<EnumType> enumType, Integer i) {
    for (EnumType ele : enumType.getEnumConstants()) {
      if (ele.code().equals(i)) {
        return ele;
      }
    }
    return null;
  }

}
