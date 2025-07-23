package com.hari.gateway_service_api.filter;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced  // 👈 Required for "lb://" URIs to work
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
