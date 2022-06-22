package com.zhucan.enums.conversion.converter;

import javax.persistence.Converter;
import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/7/10 18:12
 * @description:
 */
@Converter(autoApply = true)
public class CharListConverter extends AbstractJsonConverter<List<Character>> {
}
