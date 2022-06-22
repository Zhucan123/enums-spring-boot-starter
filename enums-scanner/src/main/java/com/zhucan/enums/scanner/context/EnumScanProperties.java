package com.zhucan.enums.scanner.context;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author zhuCan
 * @description 码表扫描配置属性类
 * @since 2021-09-09 14:18
 **/
@ConfigurationProperties(prefix = "enum-scan")
public class EnumScanProperties {

    /**
     * 扫描的包路径
     */
    private List<String> scanPackages = Lists.newArrayList("com");

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }
}
