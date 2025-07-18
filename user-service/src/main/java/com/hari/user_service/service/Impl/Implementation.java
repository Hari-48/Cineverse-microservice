package com.hari.user_service.service.Impl;

import com.hari.user_service.entity.CinemaUser;

import com.hari.user_service.model.RegisterRequest;

import com.hari.user_service.model.RegisterResponse;
import com.hari.user_service.repo.CinemaUserRepo;

import com.hari.user_service.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        CinemaUser username = cinemaUserRepo.findByUserName(user.getUserName());
        if (username == null) {
            CinemaUser cinemaUser = new CinemaUser(
                    validateUserName(user.getUserName()),
                    validEmail(user.getEmail()),
                    user.getAge(), user.getFirstName(), user.getLastName(), user.getMobileNumber(), user.getDateOfBirth(), user.getUserType());

            cinemaUser.setSpecialId(getMyUserId(user.getUserName()));
            cinemaUser.setPassword(getHashcode(user.getPassword()));
            cinemaUser.setCreatedAt(LocalDateTime.now());
            cinemaUser.setUpdatedAt(LocalDateTime.now());
            Long id = cinemaUserRepo.save(cinemaUser).getId();

            RegisterResponse registerResponse = new RegisterResponse(
                    id
                    ,user.getUserName()
                    , user.getEmail()
                    , user.getAge()
                    , user.getFirstName()
                    , user.getLastName()
                    , user.getMobileNumber()
                    , user.getDateOfBirth()
                    , getMyUserId(user.getUserName()));

            return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("USER ALREADY , PRESENT", HttpStatus.CREATED);
        }
    }

    private String validateUserName(String userName) {
       CinemaUser cinemaUser =  cinemaUserRepo.findByUserName(userName);
       if(cinemaUser==null){
           return userName;
       }
       else {
           throw new RuntimeException("user already present, try with another name.");
       }
    }



    private String validEmail(String email) {

        String regex = "^[a-z][a-zA-Z0-9]+@[a-z]+\\.[a-z]{2,6}$";
//        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

//        return email.matches(regex) ? email : "invalid-email";

        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email;

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


    @Override
    public ResponseEntity<?> changePassword(String userName ,String oldPassword, String newPassword) {
        CinemaUser cinemaUser = cinemaUserRepo.findByUserName(userName);
        if (cinemaUser!= null) {
            if (oldPassword.equals(cinemaUser.getPassword())) {
                cinemaUser.setPassword(newPassword);
                cinemaUserRepo.saveAndFlush(cinemaUser);
                return new ResponseEntity<>("New password  has update", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Enter the correct password ", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }

}
