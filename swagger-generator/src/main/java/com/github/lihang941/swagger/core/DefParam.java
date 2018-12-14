package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.base.DataType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface DefParam {
    String val();

    DataType type();

    Xml xml() default @Xml;
}
