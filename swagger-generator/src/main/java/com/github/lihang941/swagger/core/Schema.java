package com.github.lihang941.swagger.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Schema {
    String type() default "object";

    String $ref() default "";
}
