package com.hari.user_service.service;


import com.hari.user_service.model.RegisterRequest;
import org.springframework.http.ResponseEntity;



public interface UserService{

    ResponseEntity<?> createUser(RegisterRequest user);
    ResponseEntity<?>  deleteUser(Long id);
    ResponseEntity<?> listAllUser();

    ResponseEntity<?> changePassword(String userName , String oldPassword, String newPassword);
}
