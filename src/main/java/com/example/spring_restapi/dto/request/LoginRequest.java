package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Schema(description = "사용자 로그인 요청 - 이메일", example = "email@email.com")
    private String email;

    @Schema(description = "사용자 로그인 요청 - 비밀번호", example = "password")
    private String password;

}
