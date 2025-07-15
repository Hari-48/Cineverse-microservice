package com.hari.user_service.controller;

import com.hari.user_service.entity.MyUser;
import com.hari.user_service.model.Login;
import com.hari.user_service.model.MyUserPayload;
import com.hari.user_service.repo.MyUserRepo;
import com.hari.user_service.service.AuthFeignClient;

import com.hari.user_service.service.MyUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyUserController {


    @GetMapping("/list")
    public ResponseEntity<?> listUser(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        System.out.println("🧾 user-service received token: " + auth);
        return myUserService.listAllUser();
    }


    private final  MyUserService myUserService;

    private final AuthFeignClient authFeignClient;


    private final MyUserRepo myUserRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody MyUserPayload user){
         return myUserService.createUser(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody Login user) {
        return authFeignClient.login(user);
    }



    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){
        return myUserService.deleteUser(userId);

    }


    @GetMapping("/user/by-username/{username}")
    MyUser getUserByUsername(@PathVariable("username") String username){
        return myUserRepo.findByName(username);
    }
}

