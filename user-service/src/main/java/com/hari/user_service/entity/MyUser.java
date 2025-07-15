package com.hari.user_service.entity;

import com.hari.user_service.model.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "MY_USERS")
@Data
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "MY_USER_ID")
    private String myUserId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    @Enumerated
    private ROLE role;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGE")
    private Long age;

    @Column(name = "DOB")
    private Date dob;


}




