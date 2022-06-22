package com.zhucan.enums.conversion.converter;


import com.zhucan.enums.conversion.CodeEnum;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 本类 为code为数字类型的枚举类的转换基类 ,继承此类可以在jpa持久化时和实体类对象与数值字段 时 类型互相转换
 * <p>
 * 使用方式 :
 * 1. 在实体类的字段上面 @Convert(Converter=子类.class)
 * 2. 在子类上加入 @Converter(autoApply = true), 可开启jpa全局自动转换
 *
 * @author zhuCan
 */
public abstract class AbstractEnumConverter<S extends CodeEnum, M extends Number> implements AttributeConverter<S, M> {

    /**
     * 枚举类转为 code值
     *
     * @param attributeStatus
     * @return
     */
    @Override
    public M convertToDatabaseColumn(S attributeStatus) {
        //做空判断,因为数据库枚举类型传空时,这里会异常
        return attributeStatus == null ? null : (M) attributeStatus.code();
    }

    /**
     * code值 转为枚举类
     *
     * @param i
     * @return
     */
    @Override
    public S convertToEntityAttribute(M i) {
        if (i == null) {
            return null;
        }
        //获取 子类中 <S,T> 规定的参数类型
        Type[] actualTypeArguments = ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            //获取第一个泛型参数 S
            Class<S> clazz = (Class<S>) actualTypeArguments[0];
            //判断是否是枚举类,获取枚举的所有实例
            if (Enum.class.isAssignableFrom(clazz)) {
                S[] values = clazz.getEnumConstants();
                for (S ele : values) {
                    if (i.equals(ele.code())) {
                        return ele;
                    }
                }
            }
        }

        return null;
    }

}
