package com.github.lihang941.common.vertx;

import com.github.lihang941.vertx.rest.ContextProvider;
import com.github.lihang941.vertx.rest.Serializer;
import com.github.lihang941.vertx.rest.SimpleRestServer;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
public abstract class DefaultVerticle extends SimpleRestServer {

    @Autowired
    private ApplicationContext applicationContext;


    public class SpringContextProvider implements ContextProvider {

        private ApplicationContext applicationContext;

        public SpringContextProvider(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Override
        public <T> T provideContext(Class<T> clz) {
            if (clz.isInstance(applicationContext)) {
                return (T) applicationContext;
            }
            try {
                return applicationContext.getBean(clz);
            } catch (Exception e) {
                return null;
            }
        }
    }


    private Map<Class<? extends Throwable>, ErrorHandle> methodMap = new HashMap<>();

    protected <T extends Throwable> void register(Class<T> clazz, ErrorHandle<T> callbackMethod) {
        methodMap.put(clazz, callbackMethod);
        LOGGER.log(Level.CONFIG, "Registration error handling " + clazz);
    }


    protected void defaultErrorHandle(HttpServerResponse response, Serializer serializer, Throwable throwable) {
        LOGGER.log(Level.WARNING, "未捕获的一个错误:", throwable);
        response.setStatusCode(500).end(buildErrorMessage(throwable, serializer));
    }

    protected interface ErrorHandle<T extends Throwable> {
        void invoke(HttpServerResponse response, Serializer serializer, T throwable);
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
        super.onInitServerRouter(server, router);
    }

    /***
     * 异常处理
     * @param response
     * @param throwable
     * @param serializer
     */
    @Override
    protected void onRouterFailure(HttpServerResponse response, Throwable throwable, Serializer serializer) {
        if (throwable instanceof InvocationTargetException) {
            throwable = ((InvocationTargetException) throwable).getTargetException();
        }
        ErrorHandle callbackMethod = methodMap.get(throwable.getClass());
        if (callbackMethod != null) {
            callbackMethod.invoke(response, serializer, throwable);
        } else {
            defaultErrorHandle(response, serializer, throwable);
        }
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }


}
