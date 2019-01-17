package com.github.lihang941.example.config;

import com.github.lihang941.common.utils.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : lihang941
 * @since : 2019/1/17
 */
@Component
public class BeanConfig {

    @Bean
    public FileService fileService(){
        return new FileService("rest-samples/static/");
    }

}
