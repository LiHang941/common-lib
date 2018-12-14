package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.base.MIME;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Example {
    MIME mime() default MIME.APPLICATION$JSON;
}
