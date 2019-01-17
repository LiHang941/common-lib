package com.github.lihang941.web.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(VertxWebAutoConfiguration.class)
@Documented
public @interface EnableVertxWeb {


}
