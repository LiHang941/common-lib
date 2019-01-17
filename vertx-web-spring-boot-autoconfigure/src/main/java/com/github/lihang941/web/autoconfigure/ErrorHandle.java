package com.github.lihang941.web.autoconfigure;

import com.github.lihang941.vertx.rest.Serializer;
import io.vertx.core.http.HttpServerResponse;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
public interface ErrorHandle<T extends Throwable> {

    void invoke(HttpServerResponse response, Serializer serializer, T throwable);

}
