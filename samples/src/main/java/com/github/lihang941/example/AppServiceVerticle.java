package com.github.lihang941.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.lihang941.common.logger.Log;
import com.github.lihang941.common.vertx.DefaultVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/20
 */
@Component
public class AppServiceVerticle extends DefaultVerticle {

    @Log
    private Logger logger;


    @EventListener
    private void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
        Json.prettyMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json;
        File file = new File("service.json");
        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                json = IOUtils.toString(fileInputStream);
                logger.info("选择配置文件 ==> {}", file.getAbsoluteFile());
            }
        } else {
            json = IOUtils.toString(getClass().getResourceAsStream("/service.json"), "UTF-8");
            logger.info("选择配置文件 ==> {}", "class:/service.json");
        }
        JsonObject config = new JsonObject(json);
        Vertx vertx = event.getApplicationContext().getBean(Vertx.class);
        vertx.deployVerticle(this, new DeploymentOptions(config));
    }



}
