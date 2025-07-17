package com.hari.user_service.service.Impl;

import com.hari.user_service.entity.CinemaUser;

import com.hari.user_service.model.RegisterRequest;

import com.hari.user_service.repo.CinemaUserRepo;

import com.hari.user_service.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Implementation implements UserService {

    private final CinemaUserRepo cinemaUserRepo;
//    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public ResponseEntity<?> createUser(RegisterRequest user) {

        CinemaUser cinemaUser = new CinemaUser(user.getUserName(),user.getEmail(),
                user.getAge(),user.getFirstName(),user.getLastName(),user.getMobileNumber(),user.getDateOfBirth(),user.getUserType());

        cinemaUser.setSpecialId(getMyUserId(user.getUserName()));
        cinemaUser.setPassword(getHashcode(user.getPassword()));
        cinemaUser.setCreatedAt(LocalDateTime.now());
        cinemaUser.setUpdatedAt(LocalDateTime.now());

        return  new ResponseEntity<>(cinemaUserRepo.save(cinemaUser), HttpStatus.CREATED);
    }



    private String getHashcode(String password) {
        return encoder.encode(password);
    }


    private String getMyUserId(String name) {
        Random random  = new Random();
        int randomNumber = random.nextInt(10);
        return name + "_" + randomNumber;
    }



    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        cinemaUserRepo.deleteById(id);
        return  new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> listAllUser(){
       return new ResponseEntity<>(cinemaUserRepo.findAll(),HttpStatus.OK);
    }

}
