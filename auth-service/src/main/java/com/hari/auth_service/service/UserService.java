package com.hari.auth_service.service;

import com.hari.auth_service.entity.CinemaUser;
import com.hari.auth_service.repository.UserProxy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserProxy userProxy;
  public Optional<CinemaUser> findByUserName(String userName) {
    return Optional.ofNullable(userProxy.getUserByUsername(userName));

  }




}
