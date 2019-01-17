package com.github.lihang941.web.autoconfigure;

import com.github.lihang941.vertx.rest.SimpleRestServer;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */

@ConfigurationProperties(prefix = VertxWebProperties.VERTX_WEB_SERVER_PREFIX)
public class VertxWebProperties extends SimpleRestServer.Options {

    public static final String VERTX_WEB_SERVER_PREFIX = "vertx.web";

    private boolean webSocketEnable = false;

    public boolean isWebSocketEnable() {
        return webSocketEnable;
    }

    public VertxWebProperties setWebSocketEnable(boolean webSocketEnable) {
        this.webSocketEnable = webSocketEnable;
        return this;
    }
}
