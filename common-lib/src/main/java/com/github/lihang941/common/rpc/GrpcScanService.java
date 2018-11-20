package com.github.lihang941.common.rpc;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.vertx.core.Vertx;
import io.vertx.grpc.BlockingServerInterceptor;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 扫描GrpcService 注解 并交给Vert.x 管理
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
public class GrpcScanService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int port;
    private VertxServer vertxServer;
    private Vertx vertx;

    public GrpcScanService(int port, Vertx vertx) {
        this.port = port;
        this.vertx = vertx;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        VertxServerBuilder vertxServerBuilder = VertxServerBuilder.forAddress(vertx, "127.0.0.1", port);
        event.getApplicationContext().getBeansWithAnnotation(GrpcService.class).values()
                .stream()
                .filter(obj -> obj != null && obj instanceof BindableService)
                .forEach(obj -> {
                            GrpcService grpcService = obj.getClass().getAnnotation(GrpcService.class);
                            Class<? extends ServerInterceptor>[] interceptor = grpcService.interceptor();
                            BindableService service = (BindableService) obj;
                            if (interceptor.length == 0) {
                                vertxServerBuilder.addService(service);
                            } else {
                                // 拦截器
                                ServerInterceptor[] serverInterceptors = new ServerInterceptor[interceptor.length];
                                for (int i = 0; i < interceptor.length; i++) {
                                    serverInterceptors[i] = BlockingServerInterceptor.wrap(vertx, applicationContext.getBean(interceptor[i]));
                                }
                                vertxServerBuilder.addService(ServerInterceptors.intercept(service, serverInterceptors));
                            }
                        }
                );
        try {
            vertxServer = vertxServerBuilder.build().start();
            logger.info("Vertx Grpc Server start success port : {}", port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    private void onStop() throws InterruptedException {
        vertxServer.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
