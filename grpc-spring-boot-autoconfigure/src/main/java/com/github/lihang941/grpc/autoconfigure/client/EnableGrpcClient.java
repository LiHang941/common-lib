package com.github.lihang941.grpc.autoconfigure.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(GrpcClientAutoConfiguration.class)
@Documented
public @interface EnableGrpcClient {
}
