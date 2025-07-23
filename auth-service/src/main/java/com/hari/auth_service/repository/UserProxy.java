package com.hari.auth_service.repository;


import com.hari.auth_service.entity.CinemaUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service") // service name from Eureka
public interface UserProxy {

    @GetMapping("user/by-username/{username}")
    CinemaUser getUserByUsername(@PathVariable("username") String username);

    }

