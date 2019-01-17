package com.github.lihang941.web.autoconfigure;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RequestHandler {

    @AliasFor(annotation = Component.class)
    String value() default "";

    String path() default "";

}
