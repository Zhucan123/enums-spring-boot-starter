package com.zhucan.enums.scanner.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhucan.enums.scanner.CodeEnum;
import com.zhucan.enums.scanner.annotation.EnumScan;
import com.zhucan.enums.scanner.cached.EnumCache;
import com.zhucan.enums.scanner.constant.EnumConstant;
import com.zhucan.enums.scanner.dto.CodeItem;
import com.zhucan.enums.scanner.dto.CodeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: zhuCan
 * @date: 2020/1/16 13:48
 * @description: 使用码表扫描, 就需要创建一个EnumCache的实现类, 并设置为BEAN
 */
public class EnumScanHandlerImpl implements EnumScanHandler{

  @Autowired
  private EnumCache cache;

  @Autowired
  private ObjectMapper mapper;

  /**
   * 通过反射来获取所有需要扫描的枚举属性值,并存入缓存中
   *
   * @throws IOException
   */
  private void cacheHandler() throws IOException {
    List<Class<?>> classes = new ArrayList<>();
    try {
      // 读取所有码表类名数据
      File file = ResourceUtils.getFile("classpath:" + EnumConstant.ENUM_FILE_NAME);
      List<String> classPath = mapper.readValue(file, new TypeReference<List<String>>() {
        @Override
        public Type getType() {
          return super.getType();
        }
      });

      // 转换为class
      classes = classPath.stream().map(x -> {
        try {
          return Class.forName(x);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
        return null;
      }).filter(Objects::nonNull)
          .collect(Collectors.toList());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    List<CodeTable> codeEnums = new ArrayList<>();
    classes.forEach(clazz -> {
      try {
        // 过滤出 继承了CodeEnum 和 标记了EnumScan注解的枚举
        if (CodeEnum.class.isAssignableFrom(clazz) && clazz.isEnum() && clazz.isAnnotationPresent(EnumScan.class)) {
          // 枚举的所有实例
          List<CodeEnum> items = Arrays.stream(clazz.getEnumConstants())
              .map(item -> (CodeEnum) item)
              .collect(Collectors.toList());

          //获取默认值
          int defaultEnumCode = clazz.getAnnotation(EnumScan.class).defaultEnumCode();

          // 填充数据
          codeEnums.add(new CodeTable(clazz.getSimpleName(),
                  items.stream().map(CodeItem::new).collect(Collectors.toList()),
                  items.stream().collect(Collectors.toMap(CodeEnum::code, i -> i, (m, n) -> m)).get(defaultEnumCode),
                  clazz.getName()));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    cache.write(codeEnums);
  }

  /**
   * 获取系统中的所有枚举
   *
   * @return
   */
  @Override
  public List<CodeTable> codeTables() {
    List<CodeTable> read = cache.read();
    if (read == null) {
      try {
        cacheHandler();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return cache.read();
    }
    return read;
  }


}
