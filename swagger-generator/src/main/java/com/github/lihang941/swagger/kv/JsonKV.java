package com.github.lihang941.swagger.kv;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface JsonKV {
    String key();

    String json();
}
