package com.hari.user_service.service;


import com.hari.user_service.dto.CineverseUserRequest;
import com.hari.user_service.dto.CineverseUserResponse;
import com.hari.user_service.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CineverseUserService {

    ResponseEntity<List<CineverseUserResponse>> viewUsers();

    ResponseEntity<String> saveUser(CineverseUserRequest cineverseUserRequest);


}
