package com.github.lihang941.grpc.autoconfigure.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@ConfigurationProperties(prefix = GrpcClientProperties.GRPC_CLIENT_PREFIX)
public class GrpcClientProperties {

    public static final String GRPC_CLIENT_PREFIX = "grpc.client";

    private List<ClientServiceProperties> servers;


    public List<ClientServiceProperties> getServers() {
        return servers;
    }

    public GrpcClientProperties setServers(List<ClientServiceProperties> servers) {
        this.servers = servers;
        return this;
    }
}
