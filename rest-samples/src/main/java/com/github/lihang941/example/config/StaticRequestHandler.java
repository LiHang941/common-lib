package com.github.lihang941.example.config;

import com.github.lihang941.common.utils.FileService;
import com.github.lihang941.web.autoconfigure.RequestHandler;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : lihang941
 * @since : 2019/1/17
 */
@Slf4j
@RequestHandler(path = "/static/*")
public class StaticRequestHandler implements Handler<RoutingContext> {

    private StaticHandler staticHandler;

    public StaticRequestHandler(FileService fileService) {
        log.info(fileService.getStaticDir().getAbsolutePath());
        StaticHandler staticHandler = StaticHandler.create().setAllowRootFileSystemAccess(true)
                .setWebRoot(fileService.getStaticDir().getAbsolutePath());
    }


    @Override
    public void handle(RoutingContext event) {
        staticHandler.handle(event);
    }

}
