# Demo of DDDplus

演示如何使用[DDDplus](https://github.com/funkygao/cp-ddd-framework)实现一套`订单履约中台OMS`。

![Requirement](https://img.shields.io/badge/JDK-8+-green.svg)
[![DDDplus version](https://img.shields.io/badge/DDDplus-1.0.2--SNAPSHOT-blue)](https://github.com/funkygao/cp-ddd-framework)

[OMS业务入门](https://github.com/funkygao/oms/blob/master/README.md)。

## 目录

- [如何运行该演示](#如何运行该演示)
- [演示代码入口](#演示代码入口)
- [项目基本介绍](https://github.com/funkygao/cp-ddd-framework/wiki/The-Demo)
- [代码快速入门](#代码快速入门)
- [代码结构](#代码结构)
   - [依赖关系](#依赖关系)
   - 一套[订单履约中台代码库](#order-center-cp)
   - [中台的个性化业务包](#order-center-pattern)
   - [三个业务前台代码库](#订单履约中台的多个业务前台)
      - [KA业务前台](#order-center-bp-ka)
      - [ISV业务前台](#order-center-bp-isv)
      - [生鲜业务前台](#order-center-bp-fresh)
   - [支撑域](#支撑域)
- [如何快速搭建中台工程骨架](#如何快速搭建中台工程骨架)

## 如何运行该演示

``` bash
git clone https://github.com/dddplus/dddplus-demo.git
cd dddplus-demo
mvn package
java -jar order-center-cp/cp-oc-main/target/dddplus-demo.jar
#java -jar order-center-cp/cp-oc-main/target/dddplus-demo.jar 9090 plugin

# in another terminal
curl -XPOST http://localhost:9090/order             # submit an order
curl -XPOST http://localhost:9090/reload?plugin=isv # plugin hot reloading
```

## 演示代码入口

- 启动入口 [OrderServer.java](order-center-cp/cp-oc-main/src/main/java/org/example/cp/oms/OrderServer.java)
- Web Controller [OrderController.java](order-center-cp/cp-oc-controller/src/main/java/org/example/cp/oms/controller/OrderController.java)

## 代码快速入门

- [中台架构特色的DDD分层架构](order-center-cp)
   - 也可以通过[dddplus-archetype](https://github.com/dddplus/dddplus-archetype)，快速从零开始搭建中台的分层架构，并融入最佳实践
   - [domain层是如何通过依赖倒置模式与infrastructure层交互的](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/facade/mq/IMessageProducer.java)
   - [Repository同理](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/facade/repository/IOrderRepository.java)
   - [为什么依赖倒置统一存放在facade包](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/facade/package-info.java)
- 扩展点：订单的防并发
   - [如何识别该业务属于KA业务前台](order-center-bp-ka/src/main/java/org/example/bp/oms/ka/KaPartner.java)
   - [一个扩展点声明](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/ext/ISerializableIsolationExt.java)
   - [该扩展点，KA业务前台的实现](order-center-bp-ka/src/main/java/org/example/bp/oms/ka/extension/SerializableIsolationExt.java)
   - [该扩展点，ISV业务前台的实现](order-center-bp-isv/src/main/java/org/example/bp/oms/isv/extension/SerializableIsolationExt.java)
   - [该扩展点，中台的一个业务场景实现](order-center-pattern/src/main/java/org/example/cp/oms/pattern/extension/coldchain_b2b/SerializableIsolationExt.java)
   - [扩展点被封装到DomainAbility](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/ability/SerializableIsolationAbility.java)
   - [扩展点被调用](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/service/SubmitOrder.java)
- [前台对中台的步骤编排](order-center-bp-ka/src/main/java/org/example/bp/oms/ka/extension/DecideStepsExt.java)
   - [动态的步骤编排](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/step/submitorder/BasicStep.java)
- [扩展属性通过扩展点的实现](order-center-bp-isv/src/main/java/org/example/bp/oms/isv/extension/CustomModelExt.java)
- [业务约束规则的显式化](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/specification/ProductNotEmptySpec.java)
   - [如何使用](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/ability/extension/DefaultAssignOrderNoExt.java)
- [中台统一定义，兼顾前台个性化的错误码机制](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/exception/OrderException.java)
- [中台特色的领域模型](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/model/IOrderMain.java)
   - spec jar里定义受限的领域模型输出给业务前台：通过接口，而不是类
   - 一种中台控制力更强的shared kernel机制
   - domain层是如何实现的领域模型接口的：[订单主档](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/model/OrderMain.java)
   - [Creator的作用](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/model/OrderMain.java#L50)
- 领域步骤，业务模式等，中台如何统一定义，并输出给前台使用？
   - 例如，业务前台是可以编排中台的步骤的，它必须要知道中台有哪些步骤
   - [领域步骤的统一输出](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/Steps.java)
   - [业务模式的统一输出](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/Patterns.java)
- [中台如何输出资源给业务前台使用](order-center-cp/cp-oc-spec/src/main/java/org/example/cp/oms/spec/resource/IStockRpc.java)
   - 如果业务前台要使用中台的MQ怎么办？能否中台封装一下，不直接暴露给前台
- [库存支撑域给订单核心域的能力输出](order-center-domain-stock/order-center-stock-spec/src/main/java/org/example/oms/d/stock/spec/)
   - [能力的实现](order-center-domain-stock/order-center-stock-domain/src/main/java/org/example/oms/d/stock/domain/service/StockService.java)
   - [订单核心域对库存支撑域的调用](order-center-cp/cp-oc-domain/src/main/java/org/example/cp/oms/domain/step/submitorder/StockStep.java)
- [按需打包](order-center-cp/cp-oc-main/pom.xml)
   - 这样才能做到一套代码，支撑国内、国际业务
   - 灵活的部署形式

## 代码结构

### 依赖关系

![](/doc/assets/img/ddd-depgraph.png)

### [order-center-cp](order-center-cp)

订单履约中台，通过[spec jar](order-center-cp/cp-oc-spec)为业务前台赋能，输出中台标准，并提供扩展机制。

#### [order-center-pattern](order-center-pattern)

订单履约中台本身的个性化业务，即个性化的业务模式包。

### 订单履约中台的多个业务前台

#### [order-center-bp-ka](order-center-bp-ka)

KA，关键客户的个性化业务通过扩展点的实现完成。

#### [order-center-bp-isv](order-center-bp-isv)

ISV，独立软件开发商的个性化业务通过扩展点的实现完成。

#### [order-center-bp-fresh](order-center-bp-fresh)

Fresh，生鲜业务前台的个性化业务通过扩展点的实现完成。

这个业务BP，被中台要求不能使用Spring框架开发，不能在业务扩展包里使用AOP等Spring机制，只能严格实现中台定义的扩展点。

为了演示，ISV和KA这2个业务前台BP在开发业务扩展包时，可以使用Spring框架。

### 支撑域

![](http://www.plantuml.com/plantuml/svg/RPBFRh905CNtFWMhRy4pV6byWQwwwDf4cfYeCTNkmJzeDKHGI9lM5ajCQfk8DgcK0jMNcJiCRz7HcKObBCyzltivSCZN6uNhHgLKBLOAjPme4DS1pSBc4eyCiErSJXG52CQmkDyfaQh__setvRfqbZXjqARCInoLsdYYGV-5GfH2FnQ-ynY3Rz_WmugRtynYAyXVmBR50B8UW1mbSaWsHWecNeVUWLaxrjMK5KT1qXrcFY9fpQ6dPbY7zAO2xaDs-WFKxMDpam5HIhWylq3130MZz8T1_W261Wh7TF74OAFOj57m6lSzB2lm-8oYwVvSqj78LZ--A82bWkinXe-G7s9hXJNt21ahNB3k86g2x--xAqjN3Q5UAaginiuShvLuFY1VDl7V-HBDShYmIxWCSQ1pJKETQFBfqDVd0YOhU9Ave2LXg_Ut9gkW6pkHbwf5_dFz0W00)

- [库存支撑域](order-center-domain-stock)
- 更多的支撑域...

## 如何快速搭建中台工程骨架

使用 [dddplus-archetype](https://github.com/dddplus/dddplus-archetype)，可以快速搭建中台的工程骨架。

