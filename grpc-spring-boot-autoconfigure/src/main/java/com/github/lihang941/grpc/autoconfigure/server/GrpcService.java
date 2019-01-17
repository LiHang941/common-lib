package com.github.lihang941.grpc.autoconfigure.server;

import io.grpc.ServerInterceptor;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface GrpcService {

    @AliasFor(annotation = Component.class)
    String value() default "";

    Class<? extends ServerInterceptor>[] interceptor() default {};
}
