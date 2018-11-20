package com.github.lihang941.vertx.rest;

import java.lang.annotation.*;

/**
 * @author HanikLZ
 * @since 2017/3/1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface Path {
    String value();
    String defaultValue() default Constants.DEFAULT_VALUE_NULL;
}

