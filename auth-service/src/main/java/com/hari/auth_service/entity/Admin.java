package com.hari.auth_service.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "ADMIN")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "MY_USER_ID")
    private String myUserId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ROLE")

    private String role;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGE")
    private Long age;

    @Column(name = "DOB")
    private Date dob;


}




