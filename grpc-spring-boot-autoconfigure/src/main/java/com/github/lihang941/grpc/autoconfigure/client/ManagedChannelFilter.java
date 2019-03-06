package com.github.lihang941.grpc.autoconfigure.client;

import io.grpc.ManagedChannelBuilder;

/**
 * @author : lihang941
 * @since : 2019/3/6
 */
public class ManagedChannelFilter {

    public ManagedChannelBuilder handle(ClientServiceProperties clientServiceProperties) {
        ManagedChannelBuilder<?> o = ManagedChannelBuilder
                .forAddress(clientServiceProperties.getAddress(), clientServiceProperties.getPort())
                .usePlaintext();
        return o;
    }

}
