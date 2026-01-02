package com.hari.user_service.dto;

import com.hari.user_service.model.USER_TYPE;
import com.hari.user_service.validation.ValidMobileNumber;
import com.hari.user_service.validation.ValidPassword;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record CineverseUserRequest(

        @NotNull(message = "username should be null")
        String username,

        @NotNull(message = "email should not be null")
        String email,

        LocalDate dateOfBirth,

        @NotNull(message = "password should not be null")
        @ValidPassword
        String password,

        @NotNull(message = "mobileNumber should not be null")
        @ValidMobileNumber
        String mobileNumber,

        USER_TYPE userType) {
}
