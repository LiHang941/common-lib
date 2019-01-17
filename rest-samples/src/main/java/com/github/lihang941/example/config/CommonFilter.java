package com.github.lihang941.example.config;

import com.alibaba.fastjson.JSON;
import com.github.lihang941.vertx.rest.*;
import com.github.lihang941.web.autoconfigure.WebContext;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
        for (Map.Entry<String, String> header : request.headers()) {
            log.info("{} --- {}",header.getKey(),header.getValue());
        }
        log.info(request.headers().get("Size"));
        log.info(request.headers().get("size"));
        log.info(request.headers().get("Offset"));
        log.info(request.headers().get("offset"));
    }

    @Override
    public void filter(HttpServerRequest request, HttpServerResponse response) {
        log.info("rps filter");
    }

}
