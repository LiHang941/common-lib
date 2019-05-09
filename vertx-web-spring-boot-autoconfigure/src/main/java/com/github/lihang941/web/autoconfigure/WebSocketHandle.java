package com.github.lihang941.web.autoconfigure;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

/**
 * @author : lihang941
 * @since : 2018/12/25
 */
public interface WebSocketHandle {

    default void exceptionHandler(ServerWebSocket webSocket, Throwable throwable) {
    }

    default void endHandler(ServerWebSocket webSocket) {
    }

    default void closeHandler(ServerWebSocket webSocket) {
    }

    default void handler(ServerWebSocket webSocket, Buffer buffer) {
    }

    default void connectSuccess(ServerWebSocket webSocket) {
    }
}
