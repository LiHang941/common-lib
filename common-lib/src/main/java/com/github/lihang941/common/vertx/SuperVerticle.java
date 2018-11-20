package com.github.lihang941.common.vertx;

import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.mdvsc.common.service.RestServiceVerticle;
import org.mdvsc.vertx.rest.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/6/20
 */
public abstract class SuperVerticle extends RestServiceVerticle {

    protected Logger logger = LoggerFactory.getLogger(SuperVerticle.class);

    private Map<Class<? extends Throwable>, ErrorHandle> methodMap = new HashMap<>();


    @Override
    protected void onInitServerRouter(HttpServer server, Router router) {
        setStaticResourcePath("");
        super.onInitServerRouter(server, router);
    }

    protected <T extends Throwable> void register(Class<T> clazz, ErrorHandle<T> callbackMethod) {
        methodMap.put(clazz, callbackMethod);
        logger.debug("注册错误处理 :: {}", clazz);
    }

    /***
     * 异常处理
     * @param response
     * @param throwable
     * @param serializer
     */
    @Override
    protected void onRouterFailure(HttpServerResponse response, Throwable throwable, Serializer serializer) {
        response.setStatusCode(200);
        if (throwable instanceof InvocationTargetException) {
            throwable = ((InvocationTargetException) throwable).getTargetException();
        }
        ErrorHandle callbackMethod = methodMap.get(throwable.getClass());
        if (callbackMethod != null) {
            callbackMethod.invoke(response, throwable);
        } else {
            defaultErrorHandle(response, throwable);
        }
    }

    protected void defaultErrorHandle(HttpServerResponse response, Throwable throwable) {
        logger.warn("未捕获的一个错误:", throwable);
    }

    protected interface ErrorHandle<T extends Throwable> {
        void invoke(HttpServerResponse response, T throwable);
    }

}
