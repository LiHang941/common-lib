package com.github.lihang941.grpc.autoconfigure.client;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
public class ClientServiceProperties {

    private String address;
    private int port;
    private String name;

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
