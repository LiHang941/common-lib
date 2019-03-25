package com.github.lihang941.grpc.autoconfigure.client;

import io.grpc.ManagedChannelBuilder;

/**
 * Channel 生成器
 *
 * @author : lihang941
 * @since : 2019/3/6
 */
public interface ManagedChannelFilter {

    ManagedChannelBuilder handle(ClientServiceProperties clientServiceProperties);

}
