package com.github.lihang941.web.autoconfigure;

import com.github.lihang941.vertx.rest.RestMapper;
import com.github.lihang941.vertx.rest.Serializer;
import io.vertx.core.Vertx;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
@Configuration
@EnableConfigurationProperties(VertxWebProperties.class)
public class VertxWebAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @ConditionalOnMissingBean
    @Bean
    public Serializer serializer() {
        return RestMapper.DEFAULT_SERIALIZER;
    }

    @ConditionalOnMissingBean
    @Bean
    public DefaultServiceVerticle defaultServiceVerticle(VertxWebProperties vertxWebProperties, ApplicationContext applicationContext) {
        return new DefaultServiceVerticle(vertxWebProperties, applicationContext);
    }
}
