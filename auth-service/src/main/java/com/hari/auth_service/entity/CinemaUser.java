package com.hari.auth_service.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data

public class CinemaUser {


        private Long id;

        private String username;
        private String email;
        private String password;
        private Long age;


        private String firstName;
        private String lastName;
        private String mobileNumber;
        private LocalDate dateOfBirth;

        @Enumerated(EnumType.STRING)
        private USER_TYPE userType;

        private boolean isActive = true;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private String specialId;

    }



