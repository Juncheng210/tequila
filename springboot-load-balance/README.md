# springboot-load-balance

负载均衡示例代码。

## eureka-server

 提供服务注册和发现

## services-provider

服务消费方，从 Eureka 获取注册服务列表，从而能够消费服务，也就是请求的直接入口。


## services-consumer

服务提供方，一般有多个服务参与调度。

服务调用包含 RestTemplate 和 Feign 的例子。