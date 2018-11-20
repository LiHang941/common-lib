package com.github.lihang941.vertx.annotation;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {
    String[] value() default {};
    boolean allMatchMode () default false;
}
