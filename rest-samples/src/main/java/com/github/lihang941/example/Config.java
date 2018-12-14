package com.github.lihang941.example;

import com.github.lihang941.common.logger.LoggerBeanPostProcessor;
import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
@ImportResource("classpath:persist.xml")
@Configuration
public class Config {

    @Bean
    public Vertx vertx (){
        return Vertx.vertx();
    }


    @Bean
    public LoggerBeanPostProcessor loggerBeanPostProcessor(){
        return new LoggerBeanPostProcessor();
    }


}
