package com.github.lihang941.generator.config;

/**
 * @author : lihang941
 * @since : 2018/11/28
 */
public class ResourceConfig {

    private PathPackage resource;
    private boolean isPage;

    public ResourceConfig(PathPackage resource) {
        this.resource = resource;
    }

    public ResourceConfig(PathPackage resource, boolean isPage) {
        this.resource = resource;
        this.isPage = isPage;
    }

    public ResourceConfig() {
    }

    public PathPackage getResource() {
        return resource;
    }

    public ResourceConfig setResource(PathPackage resource) {
        this.resource = resource;
        return this;
    }

    public boolean isPage() {
        return isPage;
    }

    public ResourceConfig setPage(boolean page) {
        isPage = page;
        return this;
    }
}
