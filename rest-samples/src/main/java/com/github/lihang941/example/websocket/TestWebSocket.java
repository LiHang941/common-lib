package com.github.lihang941.example.websocket;

import com.github.lihang941.web.autoconfigure.WebSocket;
import com.github.lihang941.web.autoconfigure.WebSocketHandle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : lihang941
 * @since : 2019/1/17
 */
@Slf4j
@WebSocket(url = "/")
public class TestWebSocket implements WebSocketHandle {

    @Override
    public void handler(ServerWebSocket webSocket, Buffer buffer) {
        log.info(buffer.toString());
    }
}
