package com.atguigu.springcloud.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class RequestUUIDConfig {
    @Bean
    @LoadBalanced//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端       负载均衡的工具。
    public RestTemplate getRestTemplate()
    {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(headerInterceptor(null)));
        return template;
    }
    /**
     * 这个bean用于从当前请求中获取token信息，并将信息写入转发的请求头
     *
     * @param value
     * @return
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ClientHttpRequestInterceptor headerInterceptor(
            @Value("#{request.getHeader('requestUUID')}") final String value) {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().add("requestUUID", value);
                return execution.execute(request, body);
            }
        };
    }

    @Bean
    public RequestInterceptor feignConfig() {
        // 指定一个tokenHolder,用于存储token
        TokenHolder tokenHolder = tokenHolder(null);
        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 取出tokenHolder中存储的值
                template.header("requestUUID", tokenHolder.getValue());
            }
        };
        return interceptor;
    }
    /**
     * 这个容器，用户从获取并保存当前请求头中的Authorization
     **/
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public TokenHolder tokenHolder(
            @Value("#{request.getHeader('requestUUID')}") String value) {
        return new TokenHolder() {
            @Override
            public String getValue() {
                return value;
            }
        };
    }
    public interface TokenHolder {
        public String getValue();
    }
}
