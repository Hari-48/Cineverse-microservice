package com.hari.user_service.model;

import lombok.Data;

import java.time.LocalDate;

@Data

public class RegisterResponse {
    public RegisterResponse(Long id, String userName, String email, Long age, String firstName, String lastName, String mobileNumber, LocalDate dateOfBirth, String specialId) {
        this.userName = userName;
        this.email = email;

        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialId = specialId;
    }

    private String userName;
    private String email;

    private Long age;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private LocalDate dateOfBirth;
    private String specialId;



}
