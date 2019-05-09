package com.github.lihang941.web.autoconfigure;

import com.github.lihang941.vertx.rest.Serializer;
import com.github.lihang941.vertx.rest.SimpleRestServer;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
public class DefaultServiceVerticle extends SimpleRestServer {

    private ApplicationContext applicationContext;

    private VertxWebProperties vertxWebProperties;

    public static ErrorHandle defaultErrorHandle = (response, serializer, throwable) -> {
        response.setStatusCode(500).end(buildErrorMessage(throwable, serializer));
    };

    private static Map<Class<? extends Throwable>, ErrorHandle> methodMap = new HashMap<>();

    public static <T extends Throwable> void register(Class<T> clazz, ErrorHandle<T> callbackMethod) {
        methodMap.put(clazz, callbackMethod);
        LOGGER.log(Level.CONFIG, "vertx web registration Error handler " + clazz);
    }

    public void defaultErrorHandle(HttpServerResponse response, Serializer serializer, Throwable throwable) {
        LOGGER.log(Level.WARNING, "Http Server ERROR", throwable);
        if (defaultErrorHandle != null) {
            defaultErrorHandle.invoke(response, serializer, throwable);
        } else {
            response.setStatusCode(500).end(buildErrorMessage(throwable, serializer));
        }
    }

    public DefaultServiceVerticle(
            VertxWebProperties vertxWebProperties,
            ApplicationContext applicationContext) {
        super(vertxWebProperties);
        this.applicationContext = applicationContext;
        this.vertxWebProperties = vertxWebProperties;
    }


    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        restRouteMapper.setExtraContextProvider(new SpringContextProvider(applicationContext));
        applicationContext.getBeansWithAnnotation(Controller.class).values().forEach(v ->
                restRouteMapper.addContextInstances(v)
        );
    }

    @Override
    protected void onInitServerRouter(HttpServer server, Router router) {

        applicationContext.getBeansWithAnnotation(RequestHandler.class).
                values().forEach(v -> {
                    RequestHandler annotation = v.getClass().getAnnotation(RequestHandler.class);
                    String path = annotation.path();
                    Route route;
                    if (path.isEmpty()) {
                        route = router.route();
                    } else {
                        route = router.route(path);
                    }
                    route.handler((Handler<RoutingContext>) v);
                }
        );
        applicationContext.getBeansWithAnnotation(WebContext.class).values().forEach(v -> {
            WebContext annotation = v.getClass().getAnnotation(WebContext.class);
            if (annotation.registerClass().length == 0) {
                restRouteMapper.registerContext((Class<Object>) v.getClass(), v);
            } else {
                for (Class registerClass : annotation.registerClass()) {
                    if (registerClass.isInstance(v)) {
                        restRouteMapper.registerContext(registerClass, v);
                    } else {
                        LOGGER.log(Level.WARNING, MessageFormat.format("class :{0} register context error ", v.getClass()));
                    }
                }
            }
        });

        if (vertxWebProperties.isWebSocketEnable()) {
            initWebSocket(server);
        }

        super.onInitServerRouter(server, router);
    }


    private Map<String, WebSocketHandle> socketHandle = new HashMap<>();


    public void addWebSocketHandle(String url, WebSocketHandle webSocketHandle) {
        socketHandle.put(url, webSocketHandle);
    }


    public void initWebSocket(HttpServer server) {
        applicationContext.getBeansWithAnnotation(WebSocket.class).values().forEach(v -> {
                    if (v instanceof WebSocketHandle) {
                        WebSocket annotation = v.getClass().getAnnotation(WebSocket.class);
                        addWebSocketHandle(annotation.url(), (WebSocketHandle) v);
                    } else {
                        LOGGER.log(Level.WARNING, MessageFormat.format("class :{0} webSocket 注册失败", v.getClass()));
                    }
                }
        );
        server.websocketHandler(webSocket -> {
            if (socketHandle.containsKey(webSocket.path())) {
                WebSocketHandle webSocketHandle = socketHandle.get(webSocket.path());
                webSocket.closeHandler(c ->
                        webSocketHandle.closeHandler(webSocket)
                );

                webSocket.endHandler(end ->
                        webSocketHandle.endHandler(webSocket)
                );

                webSocket.exceptionHandler(ex ->
                        webSocketHandle.exceptionHandler(webSocket, ex)
                );

                webSocket.handler(buffer ->
                        webSocketHandle.handler(webSocket, buffer)
                );

                webSocketHandle.connectSuccess(webSocket);
            } else {
                webSocket.reject();
            }
        });
    }


    /***
     * 异常处理
     * @param response
     * @param throwable
     * @param serializer
     */
    @Override
    protected void onRouterFailure(HttpServerResponse response, Throwable throwable, Serializer serializer) {
        if (response.getStatusCode() <= 0) response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        if (throwable != null) {
            if (throwable instanceof InvocationTargetException) {
                throwable = ((InvocationTargetException) throwable).getTargetException();
                if (throwable == null) {
                    response.end();
                    return;
                }
            }
            ErrorHandle callbackMethod = methodMap.get(throwable.getClass());
            if (callbackMethod != null) {
                callbackMethod.invoke(response, serializer, throwable);
            } else {
                defaultErrorHandle(response, serializer, throwable);
            }
        } else {
            response.end();
        }
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }


    @EventListener
    private void onApplicationEvent(ContextRefreshedEvent event) {
        Vertx vertx = event.getApplicationContext().getBean(Vertx.class);
        vertx.deployVerticle(this, new DeploymentOptions());
    }
}
