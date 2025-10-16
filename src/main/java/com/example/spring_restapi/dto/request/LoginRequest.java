package com.example.spring_restapi.dto.request;

public class LoginRequest {
    private String email;
    private String password;

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}
