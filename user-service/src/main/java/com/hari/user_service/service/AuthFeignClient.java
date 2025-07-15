package com.hari.user_service.service;


import com.hari.user_service.model.Login;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Authentication-service") // service name from Eureka
public interface AuthFeignClient {
    @PostMapping("auth/login")
    public String login(@RequestBody Login user);
}
