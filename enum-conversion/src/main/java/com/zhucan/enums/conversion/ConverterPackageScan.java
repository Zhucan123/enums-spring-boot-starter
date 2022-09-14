package com.zhucan.enums.conversion;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author: zhuCan
 * @date: 2020/7/13 13:59
 * @description: 用于扫描默认的内置基础转换器, 加载进JPA的转换配置
 */
@EntityScan(basePackages = "com.zhucan.enums.conversion.converter")
public class ConverterPackageScan {

}
