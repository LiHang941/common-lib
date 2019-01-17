package com.github.lihang941.example.config;

import com.github.lihang941.vertx.rest.*;
import com.github.lihang941.web.autoconfigure.WebContext;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : lihang941
 * @since : 2019/1/17
 */
@Slf4j
@WebContext(registerClass = {RequestFilter.class, ResponseFilter.class, MethodInterceptor.class})
public class CommonFilter implements RequestFilter, ResponseFilter, MethodInterceptor {

    @Override
    public void intercept(MethodCaller caller) {
        log.info("intercept");
    }

    @Override
    public void filter(HttpServerRequest request) {
        log.info("req filter");
    }

    @Override
    public void filter(HttpServerRequest request, HttpServerResponse response) {
        log.info("rps filter");
    }

}
