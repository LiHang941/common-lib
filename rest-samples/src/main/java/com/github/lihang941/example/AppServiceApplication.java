package com.github.lihang941.example;

import com.github.lihang941.grpc.autoconfigure.client.EnableGrpcClient;
import com.github.lihang941.grpc.autoconfigure.server.EnableGrpcServer;
import com.github.lihang941.web.autoconfigure.EnableVertxWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@EnableVertxWeb
@EnableGrpcServer
@EnableGrpcClient
@MapperScan(basePackages="com.github.lihang941.example.dao")
@SpringBootApplication
public class AppServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppServiceApplication.class, args);
    }

}
