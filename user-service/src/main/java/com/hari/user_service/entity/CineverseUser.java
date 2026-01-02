package com.hari.user_service.entity;

import com.hari.user_service.model.USER_TYPE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CINEVERSE_USERS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CineverseUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;

        @Column(name = "USER_NAME",nullable = false)
        private String userName;

        @Column(name = "EMAIL",nullable = false)
        private String email;

        @Column(name = "PASSWORD",nullable = false)
        private String password;

        @Column(name = "MOBILE_NUMBER",nullable = false)
        private String mobileNumber;

        @Column(name = "DOB")
        private LocalDate dateOfBirth;

        @Enumerated(EnumType.STRING)
        private USER_TYPE userType;

        private boolean isActive = true;

        @Column(name = "CREATED_AT")
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "UPDATED_AT")
        private LocalDateTime updatedAt;


}



