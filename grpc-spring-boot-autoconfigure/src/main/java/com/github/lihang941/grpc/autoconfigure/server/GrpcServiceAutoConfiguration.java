package com.github.lihang941.grpc.autoconfigure.server;

import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@Configuration
@EnableConfigurationProperties(GrpcServerProperties.class)
public class GrpcServiceAutoConfiguration {

    @Autowired
    private GrpcServerProperties clientServiceProperties;


    @ConditionalOnMissingBean
    @Bean
    public VertxServerBuilder vertxServerBuilder(Vertx vertx) {
        return VertxServerBuilder.forAddress(vertx, clientServiceProperties.getHost(), clientServiceProperties.getPort());
    }


    @ConditionalOnMissingBean
    @Bean
    public GrpcScanService grpcScanService(VertxServerBuilder vertxServerBuilder) {
        return new GrpcScanService(clientServiceProperties.getHost(), clientServiceProperties.getPort(), vertxServerBuilder);
    }

    @ConditionalOnMissingBean
    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }
}
