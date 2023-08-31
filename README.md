
## Spring Cloud Eureka 服务注册中心

### Eureka 自我保护机制
当我们在本地调试基于 Eureka 的程序时，Eureka 服务注册中心很有可能会出现如下图所示的红色警告

***EMERGENCYI EUREKAMAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALSARE LESSER THAN THRESHOLD AND HENCETHE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.***

实际上，这个警告是触发了 Eureka 的自我保护机制而出现的。默认情况下，如果 Eureka Server 在一段时间内（默认为 90 秒）没有接收到某个服务提供者（Eureka Client）的心跳，就会将这个服务提供者提供的服务从服务注册表中移除。 这样服务消费者就再也无法从服务注册中心中获取到这个服务了，更无法调用该服务。

但在实际的分布式微服务系统中，健康的服务（Eureka Client）也有可能会由于网络故障（例如网络延迟、卡顿、拥挤等原因）而无法与 Eureka Server 正常通讯。若此时 Eureka Server 因为没有接收心跳而误将健康的服务从服务列表中移除，这显然是不合理的。而 Eureka 的自我保护机制就是来解决此问题的。

所谓 “Eureka 的自我保护机制”，其中心思想就是“好死不如赖活着”。如果 Eureka Server 在一段时间内没有接收到 Eureka Client 的心跳，那么 Eureka Server 就会开启自我保护模式，将所有的 Eureka Client 的注册信息保护起来，而不是直接从服务注册表中移除。一旦网络恢复，这些 Eureka Client 提供的服务还可以继续被服务消费者消费。

综上，Eureka 的自我保护机制是一种应对网络异常的安全保护措施。它的架构哲学是：宁可同时保留所有微服务（健康的服务和不健康的服务都会保留）也不盲目移除任何健康的服务。通过 Eureka 的自我保护机制，可以让 Eureka Server 集群更加的健壮、稳定。

默认情况下，Eureka 的自我保护机制是开启的，如果想要关闭，则需要在配置文件中添加以下配置。

```xml
eureka:
  server:
    enable-self-preservation: false # false 关闭 Eureka 的自我保护机制，默认是开启,一般不建议大家修改
```

> Eureka 的自我保护机制也存在弊端。如果在 Eureka 自我保护机制触发期间，服务提供者提供的服务出现问题，那么服务消费者就很容易获取到已经不存在的服务进而出现调用失败的情况，此时，我们可以通过客户端的容错机制来解决此问题
> 
> - 在 DS Replicas 选项上面出现了红色警告信息“RENEWALS ARE LESSER THAN THE THRESHOLD. THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.”，出现该信息则表示 Eureka 的自我保护模式已关闭，且已经有服务被移除。
> 
> 
> - 在 DS Replicas 选项上面出现了红色警告信息“EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.”，出现该信息表明 Eureka 的自我保护机制处于开启状态，且已经被触发。

## Spring Cloud Ribbon 负载均衡和服务调度组件

客户端负载均衡是将负载均衡逻辑以代码的形式封装到客户端上，即负载均衡器位于客户端。客户端通过服务注册中心（例如 Eureka Server）获取到一份服务端提供的可用服务清单。有了服务清单后，负载均衡器会在客户端发送请求前通过负载均衡算法选择一个服务端实例再进行访问，以达到负载均衡的目的；

客户端负载均衡也需要心跳机制去维护服务端清单的有效性，这个过程需要配合服务注册中心一起完成。

客户端负载均衡具有以下特点：
- 负载均衡器位于客户端，不需要单独搭建一个负载均衡服务器。
- 负载均衡是在客户端发送请求前进行的，因此客户端清楚地知道是哪个服务端提供的服务。
- 客户端都维护了一份可用服务清单，而这份清单都是从服务注册中心获取的。

Ribbon 就是一个基于 HTTP 和 TCP 的客户端负载均衡器，当我们将 Ribbon 和 Eureka 一起使用时，Ribbon 会从 Eureka Server（服务注册中心）中获取服务端列表，然后通过负载均衡策略将请求分摊给多个服务提供者，从而达到负载均衡的目的。

Spring Cloud Ribbon 提供了一个 IRule 接口，该接口主要用来定义负载均衡策略，它有 7 个默认实现类，每一个实现类都是一种负载均衡策略。

1. RoundRobinRule	按照线性轮询策略，即按照一定的顺序依次选取服务实例
2. RandomRule	随机选取一个服务实例
3. RetryRule	按照 RoundRobinRule（轮询）的策略来获取服务，如果获取的服务实例为 null 或已经失效，则在指定的时间之内不断地进行重试（重试时获取服务的策略还是 RoundRobinRule 中定义的策略），如果超过指定时间依然没获取到服务实例则返回 null 。
4. WeightedResponseTimeRule	WeightedResponseTimeRule 是 RoundRobinRule 的一个子类，它对 RoundRobinRule 的功能进行了扩展。根据平均响应时间，来计算所有服务实例的权重，响应时间越短的服务实例权重越高，被选中的概率越大。刚启动时，如果统计信息不足，则使用线性轮询策略，等信息足够时，再切换到 WeightedResponseTimeRule。
5. BestAvailableRule	继承自 ClientConfigEnabledRoundRobinRule。先过滤点故障或失效的服务实例，然后再选择并发量最小的服务实例。
6. AvailabilityFilteringRule	先过滤掉故障或失效的服务实例，然后再选择并发量较小的服务实例。
7. ZoneAvoidanceRule	默认的负载均衡策略，综合判断服务所在区域（zone）的性能和服务（server）的可用性，来选择服务实例。在没有区域的环境下，该策略与轮询（RandomRule）策略类似。


## Spring Cloud Config 分布式配置组件

在分布式微服务系统中，几乎所有服务的运行都离不开配置文件的支持，这些配置文件通常由各个服务自行管理，以 properties 或 yml 格式保存在各个微服务的类路径下，例如 application.properties 或 application.yml 等。

这种将配置文件散落在各个服务中的管理方式，存在以下问题：
管理难度大：配置文件散落在各个微服务中，难以管理。
安全性低：配置跟随源代码保存在代码库中，容易造成配置泄漏。
时效性差：微服务中的配置修改后，必须重启服务，否则无法生效。
局限性明显：无法支持动态调整，例如日志开关、功能开关。

为了解决这些问题，通常我们都会使用配置中心对配置进行统一管理。市面上开源的配置中心有很多，例如百度的 Disconf、淘宝的 diamond、360 的 QConf、携程的 Apollo 等都是解决这类问题的。Spring Cloud 也有自己的分布式配置中心，那就是 Spring Cloud Config。

### Config+Bus 实现配置的动态刷新

Spring Cloud Bus 又被称为消息总线，它能够通过轻量级的消息代理（例如 RabbitMQ、Kafka 等）将微服务架构中的各个服务连接起来，实现广播状态更改、事件推送等功能，还可以实现微服务之间的通信功能。

目前 Spring Cloud Bus 支持两种消息代理：RabbitMQ 和 Kafka。

#### Spring Cloud Bus 的基本原理

Spring Cloud Bus 会使用一个轻量级的消息代理来构建一个公共的消息主题 Topic（默认为“springCloudBus”），这个 Topic 中的消息会被所有服务实例监听和消费。当其中的一个服务刷新数据时，Spring Cloud Bus 会把信息保存到 Topic 中，这样监听这个 Topic 的服务就收到消息并自动消费。

#### Spring Cloud Bus 动态刷新配置的原理

利用 Spring Cloud Bus 的特殊机制可以实现很多功能，其中配合 Spring Cloud Config 实现配置的动态刷新就是最典型的应用场景之一。

当 Git 仓库中的配置发生了改变，我们只需要向某一个服务（既可以是 Config 服务端，也可以是 Config 客户端）发送一个 POST 请求，Spring Cloud Bus 就可以通过消息代理通知其他服务重新拉取最新配置，以实现配置的动态刷新。

