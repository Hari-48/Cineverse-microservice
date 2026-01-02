package com.hari.user_service.dto;

import com.hari.user_service.model.USER_TYPE;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record CineverseUserResponse (Long id , String username , String email,  LocalDate dateOfBirth , String mobileNumber , USER_TYPE userType) {
}
