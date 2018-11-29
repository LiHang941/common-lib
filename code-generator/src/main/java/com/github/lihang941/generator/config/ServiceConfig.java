package com.github.lihang941.generator.config;

/**
 * @author : lihang941
 * @since : 2018/11/28
 */
public class ServiceConfig {

    private PathPackage service;
    private PathPackage serviceImpl;

    public ServiceConfig(PathPackage service, PathPackage serviceImpl) {
        this.service = service;
        this.serviceImpl = serviceImpl;
    }

    public ServiceConfig() {
    }

    public PathPackage getService() {
        return service;
    }

    public ServiceConfig setService(PathPackage service) {
        this.service = service;
        return this;
    }

    public PathPackage getServiceImpl() {
        return serviceImpl;
    }

    public ServiceConfig setServiceImpl(PathPackage serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }
}
