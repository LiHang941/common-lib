package com.github.lihang941.grpc.autoconfigure.server;

import io.grpc.BindableService;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 扫描GrpcService 注解 并交给Vert.x 管理
 *
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
public class GrpcScanService {

    private static final Logger logger = Logger.getLogger(GrpcScanService.class.getName());
    private VertxServer vertxServer;
    private VertxServerBuilder vertxServerBuilder;
    private String host;
    private int port;

    public GrpcScanService(String host, int port, VertxServerBuilder vertxServerBuilder) {
        this.host = host;
        this.port = port;
        this.vertxServerBuilder = vertxServerBuilder;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        event.getApplicationContext().getBeansWithAnnotation(GrpcService.class)
                .values()
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
                                    serverInterceptors[i] = applicationContext.getBean(interceptor[i]);
                                }
                                vertxServerBuilder.addService(ServerInterceptors.intercept(service, serverInterceptors));
                            }
                        }
                );
        try {
            vertxServer = vertxServerBuilder.build().start();
            logger.info(MessageFormat.format("vert.x grpc server start Success listening {0}:{1}", host, String.valueOf(port)));
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
