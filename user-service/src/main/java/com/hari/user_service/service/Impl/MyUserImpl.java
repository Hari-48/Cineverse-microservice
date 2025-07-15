package com.hari.user_service.service.Impl;

import com.hari.user_service.entity.MyUser;
import com.hari.user_service.model.MyUserPayload;
import com.hari.user_service.model.ROLE;
import com.hari.user_service.repo.MyUserRepo;
import com.hari.user_service.service.MyUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MyUserImpl implements MyUserService {

    private final MyUserRepo myUserRepo;
//    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public ResponseEntity<?> createUser(MyUserPayload user) {
        MyUser myUser = new MyUser();
        myUser.setAge(user.getAge());
        myUser.setDob(user.getDob());
        myUser.setName(user.getName());
        myUser.setEmailId(user.getMail());
        myUser.setRole(getRole(user.getAge()));
        myUser.setMyUserId(getMyUserId(user.getName()));
        myUser.setPassword(getHashcode(user.getPassword()));
        return  new ResponseEntity<>(myUserRepo.save(myUser), HttpStatus.CREATED);
    }

    private String getHashcode(String password) {
        return encoder.encode(password);
    }

    private String getMyUserId(String name) {
        Random random  = new Random();
        int randomNumber = random.nextInt(10);
        return name + "_" + randomNumber;
    }

    private ROLE getRole(Long age) {
        return age > 18 ? ROLE.SUPER_USER : ROLE.NORMAL_USER;

    }


    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        myUserRepo.deleteById(id);
        return  new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> listAllUser(){
       return new ResponseEntity<>(myUserRepo.findAll(),HttpStatus.OK);
    }

}
