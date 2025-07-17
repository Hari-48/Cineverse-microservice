package com.hari.user_service.entity;

import com.hari.user_service.model.USER_TYPE;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CINEMA_USER")
public class CinemaUser {

        public CinemaUser(String userName, String email, Long age,
                          String firstName, String lastName, String mobileNumber,
                          LocalDate dateOfBirth, USER_TYPE userType) {
                this.userName = userName;
                this.email = email;
                this.age = age;
                this.firstName = firstName;
                this.lastName = lastName;
                this.mobileNumber = mobileNumber;
                this.dateOfBirth = dateOfBirth;
                this.userType = userType;
        }


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String userName;
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

        public CinemaUser() {
        }
}



