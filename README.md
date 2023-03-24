
## Eureka

### Eureka 自我保护机制
当我们在本地调试基于 Eureka 的程序时，Eureka 服务注册中心很有可能会出现如下图所示的红色警告

***EMERGENCYI EUREKAMAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALSARE LESSER THAN THRESHOLD AND HENCETHE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.***

实际上，这个警告是触发了 Eureka 的自我保护机制而出现的。默认情况下，如果 Eureka Server 在一段时间内（默认为 90 秒）没有接收到某个服务提供者（Eureka Client）的心跳，就会将这个服务提供者提供的服务从服务注册表中移除。 这样服务消费者就再也无法从服务注册中心中获取到这个服务了，更无法调用该服务。

但在实际的分布式微服务系统中，健康的服务（Eureka Client）也有可能会由于网络故障（例如网络延迟、卡顿、拥挤等原因）而无法与 Eureka Server 正常通讯。若此时 Eureka Server 因为没有接收心跳而误将健康的服务从服务列表中移除，这显然是不合理的。而 Eureka 的自我保护机制就是来解决此问题的。

所谓 “Eureka 的自我保护机制”，其中心思想就是“好死不如赖活着”。如果 Eureka Server 在一段时间内没有接收到 Eureka Client 的心跳，那么 Eureka Server 就会开启自我保护模式，将所有的 Eureka Client 的注册信息保护起来，而不是直接从服务注册表中移除。一旦网络恢复，这些 Eureka Client 提供的服务还可以继续被服务消费者消费。

综上，Eureka 的自我保护机制是一种应对网络异常的安全保护措施。它的架构哲学是：宁可同时保留所有微服务（健康的服务和不健康的服务都会保留）也不盲目移除任何健康的服务。通过 Eureka 的自我保护机制，可以让 Eureka Server 集群更加的健壮、稳定。

> Eureka 的自我保护机制也存在弊端。如果在 Eureka 自我保护机制触发期间，服务提供者提供的服务出现问题，那么服务消费者就很容易获取到已经不存在的服务进而出现调用失败的情况，此时，我们可以通过客户端的容错机制来解决此问题

- 在 DS Replicas 选项上面出现了红色警告信息“RENEWALS ARE LESSER THAN THE THRESHOLD. THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.”，出现该信息则表示 Eureka 的自我保护模式已关闭，且已经有服务被移除。


- 在 DS Replicas 选项上面出现了红色警告信息“EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.”，出现该信息表明 Eureka 的自我保护机制处于开启状态，且已经被触发。