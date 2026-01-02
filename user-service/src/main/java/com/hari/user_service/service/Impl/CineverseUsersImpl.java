package com.hari.user_service.service.Impl;


import com.hari.user_service.dto.CineverseUserRequest;
import com.hari.user_service.dto.CineverseUserResponse;
import com.hari.user_service.entity.CineverseUser;
import com.hari.user_service.repo.CineverseUserRepo;
import com.hari.user_service.service.CineverseUserService;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CineverseUsersImpl implements CineverseUserService {

    private final CineverseUserRepo cineverseUserRepo;
    //    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public ResponseEntity<List<CineverseUserResponse>> viewUsers() {
        List<CineverseUserResponse> responses = new ArrayList<>();
        List<CineverseUser> users = cineverseUserRepo.findAll();
        for (CineverseUser user : users) {
            CineverseUserResponse cineverseUserResponse = new CineverseUserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDateOfBirth(), user.getMobileNumber(), user.getUserType());
            responses.add(cineverseUserResponse);
        }
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<String> saveUser(CineverseUserRequest cineverseUserRequest) {
        CineverseUser user = cineverseUserRepo.findByUserName(cineverseUserRequest.username());
        if (user == null) {
            CineverseUser cineverseUser = CineverseUser.builder()
                    .userName(cineverseUserRequest.username()).
                    email(cineverseUserRequest.email())
                    .password(getHashcode(cineverseUserRequest.password()))
                    .mobileNumber(cineverseUserRequest.mobileNumber())
                    .dateOfBirth(cineverseUserRequest.dateOfBirth())
                    .userType(cineverseUserRequest.userType())
                    .isActive(true).createdAt(LocalDateTime.now())
                    .build();
            Long id = cineverseUserRepo.save(cineverseUser).getId();
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully, Id is " + id);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("Username existed already with the Id of " + user.getId());
        }
    }

    private String getHashcode(String password) {
        return encoder.encode(password);
    }

}

    /*

    private String validEmail(String email) {

        String regex = "^[a-z][a-zA-Z0-9]+@[a-z]+\\.[a-z]{2,6}$";
//        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";


    return email.matches(regex) ? email : "invalid-email";

        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email;

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


     */

