package com.github.lihang941.swagger.core;

import com.github.lihang941.swagger.base.CollectionFormat;
import com.github.lihang941.swagger.base.DataType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Items {
    DataType type() default DataType.STRING;

    CollectionFormat collectionFormat() default CollectionFormat.CSV;

    String def() default "";

    String[] $enum() default {};
}
