package com.hari.user_service.model;

import com.hari.user_service.validation.ValidMobileNumber;
import com.hari.user_service.validation.ValidPassword;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String userName;
    private String email;

    @ValidPassword
    private String password;
    private Long age;

    private String firstName;
    private String lastName;

    @ValidMobileNumber
    private String mobileNumber;
    private LocalDate dateOfBirth;

    private USER_TYPE userType;

    private String specialId;

}
