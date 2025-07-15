package com.hari.user_service.model;

import lombok.Data;

import java.util.Date;

@Data
public class MyUserPayload {

    private String name ;
    private String mail;
    private Long age ;
    private String password;
    private Date dob;
}
