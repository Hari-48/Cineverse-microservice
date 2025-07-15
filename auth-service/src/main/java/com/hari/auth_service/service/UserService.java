package com.hari.auth_service.service;

import com.hari.auth_service.entity.Admin;
import com.hari.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  public Optional<Admin> findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }
}
