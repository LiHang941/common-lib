package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.kv.BooleanKV;
import com.github.lihang941.swagger.kv.StringKV;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Xml {
    StringKV[] attr() default {};

    BooleanKV[] booleanAttr() default {};
}
