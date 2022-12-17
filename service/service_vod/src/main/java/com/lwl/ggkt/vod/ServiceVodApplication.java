package com.lwl.ggkt.vod;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author user-lwl
 * @createDate 2022/12/1 14:32
 */
@SpringBootApplication
@MapperScan("com.lwl.ggkt.vod.mapper")
@ComponentScan(basePackages = "com.lwl")
@EnableDiscoveryClient
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
