# enums-spring-boot-starter

[![License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)
[![Gitee Stars](https://gitee.com/zc_oss/enums-spring-boot-starter/badge/star.svg?theme=dark)](https://gitee.com/zc_oss/enums-spring-boot-starter)

enums-spring-boot-starter 是一个针对 Java 枚举类型的开源项目，它可以帮助你在数据库操作和接口传参过程中自动转换枚举类型。通过使用这个库，你可以更方便地在不同的场景中使用 Java 枚举类型。

## 主要特性

- 自动将枚举类型映射为数字类型，以便将其存储在数据库中
- 从数据库获取数据时，自动将数字类型装配为相应的枚举类型
- 在接口的前后端传参过程中，实现枚举类型与数字类型的自动转换

## 安装

### 使用Maven

在你的 `pom.xml` 文件中添加以下依赖：

```xml

<dependency>
    <groupId>com.example</groupId>
    <artifactId>enums-spring-boot-starter</artifactId>
    <version>1.1.8-RELEASE</version>
</dependency>
```

### 使用Gradle

在你的 build.gradle 文件中添加以下依赖：

```groovy
implementation 'com.example:enums-spring-boot-starter:1.1.8-RELEASE'
```

## 使用方法

（在此部分提供关于如何在项目中使用该库的具体代码示例）

## 示例

### 1.定义我们的枚举

```java

@Getter
@AllArgsConstructor
@EnumAutoConverter
@EnumScan
public enum OrderBillStateEnum implements ExtensionEnum {
    PENDING_AUDIT(1, "待审核"),
    PART_AUDIT(2, "部分审核通过"),
    NOT_PASS(3, "审核不通过"),
    UNCONFIRMED(5, "未确认"),
    UNDER_COMPLAINT(7, "申诉中"),
    CONFIRMED(9, "已确认");

    private final int code;
    private final String name;
    
}
```

### 2.前端接受参数枚举(前端传递code值就行)

如 http://localhost:8080/orders?billState=1

```java

@Data
public class PluginOrderBillQuery {

    @ApiModelProperty("账单审核状态")
    private OrderBillStateEnum billState;

}
```

### 3.数据库映射对象Entity中直接使用枚举, 会自动转换为code值存储到数据库, 然后在读取数据库实体时code值又转换成了枚举

```java

@Table(name = "plugin_order_bill")
@Entity
@Data
@Where(clause = "is_deleted = 0")
public class PluginOrderBill  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * 合同 GUID
     */
    @Column
    private String contractId;


    /**
     * 账单状态
     */
    @Column
    private OrderBillStateEnum billState;

}
```

### 4.接口查询出来的数据DTO返回前端时, 定义的枚举会自动转换为code值

```java

@Data
public class PluginOrderBillDTO {

    @ApiModelProperty("账单id")
    private Long id;

    @ApiModelProperty("账单审核状态")
    private OrderBillStateEnum billState;
}
```
响应体结构示例
```json
{
  "success": 1,
  "message": "",
  "data": {
    "id": 1244,
    "billState": 1
  }
}
```
## 贡献

我们欢迎所有对该项目感兴趣的开发者参与其中。请阅读我们的贡献指南了解更多信息。

## 许可证

该项目根据 Apache License 2.0 许可进行授权。


> 希望这个README模板对你有所帮助。你可以根据实际情况进一步完善和优化这个README文件，确保它包含所有必要的信息，以便其他开发者更容易理解和使用你的项目。祝你好运！
