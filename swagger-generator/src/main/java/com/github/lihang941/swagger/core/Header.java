package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.base.CollectionFormat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Header {
    String val();

    String desc() default "";

    String type();

    String format() default "";

    CollectionFormat collectionFormat() default CollectionFormat.CSV;

    String def() default "";

    String[] $enum() default {};
}
