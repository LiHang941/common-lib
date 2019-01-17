package com.github.lihang941.web.autoconfigure;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Controller {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
