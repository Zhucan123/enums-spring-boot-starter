# rocketONS-starter

#### 介绍
轻量级springBootStarter,基于阿里云ONS消息服务的一个简易封装,能快捷的创建rocketMQ的生产者和消费者,
实现应用之间的消息传递.

#### 软件架构
 基于spring的ApplicationContext容器管理,自动扫描consumer监听器,并注册启动消费者,来回调接受来自阿里云ONS服务传递分发的消息.
 可根据配置文件动态创建消费者和生产者,自定义消费者的启停开关,自动序列化和解析消息实体.


#### 安装教程

```mxml
       <dependency>
             <groupId>io.gitee.zhucan123</groupId>
             <artifactId>rocket-ons-spring-boot-starter</artifactId>
             <version>1.0.8</version>
       </dependency>
```

#### 使用说明

#### 1. 项目加入配置

```text
rocket:
  address: http://xxxx
  secretKey: xxxx
  accessKey: xxxx
  topic: xxxx
  groupSuffix: GID_
  enable: true
  delay: 1000
```


参数名|类型|是否必填|默认值|描述
---|---|---|---|---
accessKey	                |String	|是|-	        |您在阿里云账号管理控制台中创建的 AccessKeyId，用于身份认证。
secretKey	                |String	|是|-	        |您在阿里云账号管理控制台中创建的 AccessKeySecret，用于身份认证。
address    	                |String	|是|-	        |设置 TCP 协议接入点。
groupSuffix	                |String	|否|GID_	    |您在控制台创建的 Group ID 的前缀,一般是以GID_头。
topic	                    |String	|是|-         	|默认绑定的topic,当生产者未指定topic时使用这个默认值
delay	                    |String	|否|1000	    |消息发送延迟毫秒数
enable	                    |String	|否|true	    |是非开启使用

> 最新配置及说明，详见阿里云官方文档：https://help.aliyun.com/document_detail/93574.html?spm=a2c4g.11186623.6.553.8185447dHy2atL

###### 1.1 在主程序上面加上@EnableRocketONS来开启使用,或配置类中设置 rocket.enable = true
```java
@EnableRocketONS
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```


#### 2. consumer的使用示例代码

```java
@ConsumerListener(tags = "msg_tag", consumers = 2)
@OnsConfiguration(topic = "topic-example", group = "GID_${example.group}")
public class ExampleConsumerListener implements RocketListener<MessageData> {

  @Override
  public Action consume(Message message, MessageData messageBody, ConsumeContext consumeContext) {

    // do someThing!

    return Action.CommitMessage;
  }
}

```
 * @OnsConfiguration : 注册成一个spring容器,并设置消费者绑定的topic和group,可设置固定值,也可以使用${propertiesKey}的方式读取配置文件里面的配置
 * @ConsumerListener : 标识这是一个ons的消息消费者监听器
      1. tags:接受带有相关tag的消息
      2. consumers : 启动的实例数量
  
 * MessageData : 为消息体类型class,可替换为任意java类
 * consume : 为消费业务逻辑处理方法
 
 
#### 3. 生产者的使用

```java
@OnsConfiguration(topic = "topic-example", group = "group-example")
public class ExampleProducer extends DefaultProducerProxy {

}
```
 * @OnsConfiguration : 注册成一个spring容器,并设置生产者绑定的topic和group,可设置固定值,也可以使用${propertiesKey}的方式读取配置文件里面的配置
 * 继承通用的消息生产者基类, DefaultProducerProxy调用父类的sendMsg方法生产消息,并把消息投递到阿里云ONS服务

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
