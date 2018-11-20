package com.github.lihang941.vertx.rest;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public interface ResponseFilter {
    void filter(HttpServerRequest request, HttpServerResponse response);
}
