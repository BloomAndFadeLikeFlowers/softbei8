package com.atguigu.springcloud.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationStaticConfig {

    @Value("${microservicecloud.url.consumer}")
    private String consumerUrl;

    @Value("${microservicecloud.url.provider}")
    private String providerUrl;

    @Value("${microservicecloud.url.gateway}")
    private String gatewayUrl;

    @Value("${spring.application.name}")
    private String applicationName;
}
