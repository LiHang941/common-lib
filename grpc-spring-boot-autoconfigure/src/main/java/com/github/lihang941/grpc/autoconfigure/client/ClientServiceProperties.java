package com.github.lihang941.grpc.autoconfigure.client;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
public class ClientServiceProperties {
    /**
     * GRPC 服务地址
     */
    private String address;
    /**
     * GRPC 服务名称 （全局唯一）
     */
    private String name;
    /**
     * GRPC 端口
     */
    private int port;

    public String getAddress() {
        return address;
    }

    public ClientServiceProperties setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ClientServiceProperties setPort(int port) {
        this.port = port;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClientServiceProperties setName(String name) {
        this.name = name;
        return this;
    }

}
