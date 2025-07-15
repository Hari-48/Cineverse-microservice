package com.hari.user_service.service;

import com.hari.user_service.entity.MyUser;

import com.hari.user_service.model.MyUserPayload;
import org.springframework.http.ResponseEntity;



public interface MyUserService {


    ResponseEntity<?> createUser(MyUserPayload user);
    ResponseEntity<?>  deleteUser(Long id);

    ResponseEntity<?> listAllUser();
}
