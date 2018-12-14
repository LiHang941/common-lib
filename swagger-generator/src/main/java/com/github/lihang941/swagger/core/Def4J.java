package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.base.Json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Def4J {
    String val();

    String type() default "object";

    String json();

    Json jsonType() default Json.OBJECT;
}
