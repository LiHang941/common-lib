package com.github.lihang941.vertx.rest;

/**
 * @author HanikLZ
 * @since 2017/3/8
 */
public interface ContextProvider {
    <T> T provideContext(Class<T> clz);
}

