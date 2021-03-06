package com.github.lihang941.swagger.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Sec {
    /**
     * @return 安全方案的名称
     */
    String key() default "";

    /**
     * @return 安全方案对应的值
     */
    String[] values() default {};
}
