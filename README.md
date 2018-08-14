#参考Dubbo框架，手写一个类似Dubbo框架功能（涵盖Dubbo RPC 核心功能）

##需要解决的问题

`1、为什么消费者能够调用到生产者的服务层`

`2、生产者怎么把内容注册到注册中心的，然后消费者是如何获取这个信息的`

`3、dubbo框架是如何跟spring进行整合的`
```
0）在 resource 文件夹下创建 META-INF 文件夹 
1）xsd 自定义标签 定义    
2）实现 NamespaceHandlerSupport
3）BeanDefinitionParser
```
`4、消费者获取的代理实例是如何创建1`
