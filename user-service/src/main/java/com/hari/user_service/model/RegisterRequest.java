package com.hari.user_service.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class RegisterRequest {

    private String userName;
    private String email;
    private String password;
    private Long age;

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private LocalDate dateOfBirth;


    private USER_TYPE userType;

    private String specialId;


}
