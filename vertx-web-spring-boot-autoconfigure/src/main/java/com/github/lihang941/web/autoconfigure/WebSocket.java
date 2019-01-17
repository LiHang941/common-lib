package com.github.lihang941.web.autoconfigure;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author : lihang941
 * @since : 2018/12/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface WebSocket {
    @AliasFor(annotation = Component.class)
    String value() default "";

    String url();
}
