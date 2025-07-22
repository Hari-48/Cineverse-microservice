package com.hari.auth_service.model;

public class LoginResponseDTO {

  private final String token;

  public LoginResponseDTO(String token) {

    this.token = token;
  }

  public String getToken() {

    return token;
  }
}
