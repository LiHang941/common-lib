package com.github.lihang941.grpc.autoconfigure.server;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用GRPC Server 默认配置
 * @author : lihang941
 * @since : 2019/1/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(GrpcServiceAutoConfiguration.class)
@Documented
public @interface EnableGrpcServer {
}
