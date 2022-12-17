package com.lwl.ggkt.live.config;

import com.lwl.ggkt.live.mtcloud.MTCloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MTCloudConfig {

    @Resource
    private MTCloudAccountConfig mtCloudAccountConfig;

    @Bean
    public MTCloud mtCloudClient(){
        return new MTCloud(mtCloudAccountConfig.getOpenId(), mtCloudAccountConfig.getOpenToken());
    }
}