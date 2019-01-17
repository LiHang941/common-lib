## 开发工具库
[![](https://jitpack.io/v/LiHang941/common-lib.svg)](https://jitpack.io/#LiHang941/common-lib)


### 通用工具包,集成多个工厂类

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    compile 'com.github.LiHang941.common-lib:common-lib:lastVersion'
}
```


- 日志自动注入

```java
@Configuration
public class MyConfig {
    @Bean
    public LoggerBeanPostProcessor loggerBeanPostProcessor() {
        return new LoggerBeanPostProcessor();
    }
}
```



### grpc-spring-boot-starter

> 基于 vert.x 的 grpc 库

```
 compile 'com.github.LiHang941.common-lib:grpc-spring-boot-autoconfigure:lastVersion'
```

- GRPC-SERVER-VERT.X

```java

@Configuration
public class MyConfig {
   @Bean
   public GrpcScanService grpcScanService(Vertx vertx) {
      return new GrpcScanService(clientServiceProperties.getHost(),clientServiceProperties.getPort(),vertx);
   }
}
```

or

```java
@EnableGrpcServer
@SpringBootApplication
public class AppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppServiceApplication.class, args);
    }
}
```

config

```yaml
grpc:
  server:
    host: 127.0.0.1
    port: 9000
```

example

```java
@GrpcService
public class MyRpcService extends CurrencyServiceGrpc.CurrencyServiceVertxImplBase {

    @Override
    public void getDefaultFee(Parameters.IdParameter request, Future<Message.CurrencyWithdrawFee> response) {
        // ...
    }
}
```


- GRPC-CLIENT

```java
@Configuration
public class MyConfig {
    @Bean
    public GrpcClientRegister grpcClientRegister( List<ClientServiceProperties> clientServiceProperties) {
        return new GrpcClientRegister(clientServiceProperties);
    }
}
```
or

```java
@EnableGrpcClient
@SpringBootApplication
public class AppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppServiceApplication.class, args);
    }
}
```

config


```yaml
grpc:
  client:
    servers:
      - name: user
        address: 1.1.1.1
        port: 8080
      - name: odrer
        address: 1.1.1.1
        port: 8081
      ## more ...
```


example


```java
@Component
public class AccountServiceRpcClient  {

    @GrpcClient(name = "user")
    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;

    // 你的rpc调用方法
}
```


### vertx-web-spring-boot-autoconfigure

> 基于VERT.X WEB restful 框架 

```
 compile 'com.github.LiHang941.common-lib:vertx-web-spring-boot-autoconfigure:lastVersion'
```

[查看 rest-samples ](rest-samples)






