package com.github.lihang941.swagger.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Tags {
    /**
     * @return 每一个tag对应一个分组
     */
    Tag[] tag() default {};
}
