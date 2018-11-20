## 开发工具库
[![](https://jitpack.io/v/LiHang941/common-lib.svg)](https://jitpack.io/#LiHang941/common-lib)


### 集成自定义spring工具包,开箱即用

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    testCompile 'com.github.LiHang941.common-lib:common-lib:lastVersion'
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

```java
 @Component // spring Bean
 public class MyBean {
     @Log
     private Logger logger;
     
     @PostConstruct
     public void onCreate(){
        logger.info("test slf4j logger ");
     }   
 }
```

- GRPC-SERVER-VERT.X

```java
@Configuration
public class MyConfig {
    @Bean
    public GrpcScanService grpcScanService( Vertx vertx) {
        return new GrpcScanService(8500,vertx);
    }
}
```

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
    public GrpcClientRegister grpcClientRegister( List<ManagedChannelConfig> managedChannelConfigs) {
        return new GrpcClientRegister(managedChannelConfigs);
    }
}
```


```java
@Component
public class AccountServiceRpcClient  {

    @GrpcClient(serverName = "user")
    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;

    // 你的rpc调用方法
}
```

- Redis







