package com.example.spring_restapi.dto.request;

import com.example.spring_restapi.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @Schema(description = "회원가입 요청 - 이메일", example = "email@email.com")
    private String email;

    @Schema(description = "회원가입 요청 - 비밀번호", example = "password")
    private String password;

    @Schema(description = "회원가입 요청 - 비밀번호 Confirm", example = "password")
    private String passwordConfirm;

    @Schema(description = "회원가입 요청 - 유저 역할", example = "USER/ADMIN")
    private UserRole userRole;

    @Schema(description = "회원가입 요청 - 닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "회원가입 요청 - 프로필 이미지", example = "profile.jpg")
    private String profileImage;

    @Schema(description = "회원가입 요청 - 소개", example = "singup!")
    private String introduce;

    @Schema(description = "회원가입 요청 - 성별", example = "F")
    private String gender;
}
