package com.github.lihang941.vertx.rest;

import java.lang.annotation.*;

/**
 * @author haniklz
 * @since 17-3-1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Context { }

