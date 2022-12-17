package com.lwl.ggkt.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableFeignClients(basePackages = "com.lwl")
@ComponentScan(basePackages = "com.lwl")
@MapperScan("com.lwl.ggkt.live.mapper")
public class ServiceLiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceLiveApplication.class, args);
    }
}