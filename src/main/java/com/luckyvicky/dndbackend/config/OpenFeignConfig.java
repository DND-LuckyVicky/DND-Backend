package com.luckyvicky.dndbackend.config;

import com.luckyvicky.dndbackend.hn.OdsayProperties;
import feign.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {
    "com.luckyvicky.dndbackend"
})
@EnableConfigurationProperties(OdsayProperties.class)
public class OpenFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
