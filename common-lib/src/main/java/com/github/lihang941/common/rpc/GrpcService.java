package com.github.lihang941.common.rpc;

import io.grpc.ServerInterceptor;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GrpcService {
    Class<? extends ServerInterceptor>[] interceptor() default {};
}
