package com.github.lihang941.example.config;

import com.github.lihang941.web.autoconfigure.RequestHandler;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.impl.CorsHandlerImpl;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author : lihang941
 * @since : 2019/1/17
 */
@RequestHandler
public class CorsRequestHandler extends CorsHandlerImpl {
    public CorsRequestHandler() {
        super("*");
        this.allowedMethods(new HashSet<>(Arrays.asList(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.HEAD,
                HttpMethod.OPTIONS,
                HttpMethod.PUT,
                HttpMethod.OPTIONS)))
                .exposedHeaders(new HashSet<>(Arrays.asList(
                        "Origin", "X-Requested-With", "Accept", "User-Agent", "Authorization", "Content-Type", "Offset", "Size", "VerifyCode", "TotalSize", "Timestamp", "TotalCount"
                )))
                .allowedHeaders(new HashSet<>(Arrays.asList(
                        "Origin", "X-Requested-With", "Accept", "User-Agent", "Authorization", "Content-Type", "Offset", "Size", "VerifyCode", "TotalSize", "Timestamp", "TotalCount"
                )));
    }
}
