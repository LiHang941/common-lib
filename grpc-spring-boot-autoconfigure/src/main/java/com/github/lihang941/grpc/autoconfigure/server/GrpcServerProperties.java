package com.github.lihang941.grpc.autoconfigure.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@ConfigurationProperties(prefix = GrpcServerProperties.GRPC_SERVER_PREFIX)
public class GrpcServerProperties {

    public static final String GRPC_SERVER_PREFIX = "grpc.server";

    private String host = "127.0.0.1";
    private int port = 9000;

    public String getHost() {
        return host;
    }

    public GrpcServerProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public GrpcServerProperties setPort(int port) {
        this.port = port;
        return this;
    }
}
