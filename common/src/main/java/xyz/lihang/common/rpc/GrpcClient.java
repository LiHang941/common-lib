package xyz.lihang.common.rpc;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/7/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GrpcClient {
    String serverName();
}
