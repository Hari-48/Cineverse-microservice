package com.hari.user_service.controller;

import com.hari.user_service.dto.CineverseUserRequest;
import com.hari.user_service.dto.CineverseUserResponse;

import com.hari.user_service.service.CineverseUserService;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cineverse")
@RequiredArgsConstructor
public class CineverseUserController {

    private final CineverseUserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<CineverseUserResponse>> viewUsers(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        System.out.println("user-service received token: " + auth);
        return userService.viewUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody CineverseUserRequest cineverseUserRequest) {
        return userService.saveUser(cineverseUserRequest);
    }


}

/*
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/by-username/{username}")
    CineVerseUser getUserByUsername(@PathVariable("username") String username) {
        return cinemaUserRepo.findByUserName(username);
    }


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String userName, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.changePassword(userName, oldPassword, newPassword);
    }


    @GetMapping("/by-userId/{userId}/email")
        public String getUserEmailByUserId (@PathVariable Long userId){
            return cinemaUserRepo.getEmailByUserId(userId);

        }

 */




