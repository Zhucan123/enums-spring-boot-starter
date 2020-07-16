package com.zhucan.jpa.conversion.converter;

import javax.persistence.Converter;
import java.util.List;

/**
 * @author: zhuCan
 * @date: 2020/7/10 18:10
 * @description:
 */
@Converter(autoApply = true)
public class IntegerListConverter extends AbstractJsonConverter<List<Integer>> {
}
