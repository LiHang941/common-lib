package com.github.lihang941.grpc.autoconfigure.client;

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
@EnableConfigurationProperties(GrpcClientProperties.class)
public class GrpcClientAutoConfiguration {

    @Autowired
    private GrpcClientProperties clientServiceProperties;

    @ConditionalOnMissingBean
    @Bean
    public GrpcClientRegister grpcClientRegister(ManagedChannelFilter managedChannelFilter) {
        return new GrpcClientRegister(clientServiceProperties.getServers(),managedChannelFilter);
    }

    @ConditionalOnMissingBean
    @Bean
    public ManagedChannelFilter managedChannelFilter() {
        return new ManagedChannelFilter();
    }
}
