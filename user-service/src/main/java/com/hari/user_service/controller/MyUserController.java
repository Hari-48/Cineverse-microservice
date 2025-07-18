package com.hari.user_service.controller;


import com.hari.user_service.entity.CinemaUser;
import com.hari.user_service.model.Login;

import com.hari.user_service.model.RegisterRequest;
import com.hari.user_service.repo.CinemaUserRepo;

import com.hari.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyUserController {

    private final UserService userService;

    private final CinemaUserRepo cinemaUserRepo;

    @GetMapping("/view-user")
    public ResponseEntity<?> listUser(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        System.out.println("🧾 user-service received token: " + auth);
        return userService.listAllUser();
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest user){
         return userService.createUser(user);
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/by-username/{username}")
    CinemaUser getUserByUsername(@PathVariable("username") String username){
        return cinemaUserRepo.findByUserName(username);
    }
}

