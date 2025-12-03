package com.example.spring_restapi.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LoginResponse {

    private String token;

    private UserResponse user;

    public LoginResponse(String token, UserResponse user){
        this.token = token;
        this.user = user;
    }
}
