package com.github.lihang941.grpc.autoconfigure.client;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GrpcClient {

    /**
     * 对应配置项中的 ClientServiceProperties.name
     * @return
     */
    String serverName();
}
