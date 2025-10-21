package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class SignUpRequest {
    @Schema(description = "회원가입 요청 - 이메일", example = "email@email.com")
    private String email;

    @Schema(description = "회원가입 요청 - 비밀번호", example = "password")
    private String password;

    @Schema(description = "회원가입 요청 - 닉네임", example = "sue")
    private String nickname;

    @Schema(description = "회원가입 요청 - 프로필 이미지", example = "profileimage.jpg")
    private String profile_image;

    @Schema(description = "회원가입 요청 - 소개말", example = "Hi, i'm sue")
    private String introduce;

    public String getEmail(){ return email; }

    public String getPassword(){ return password; }

    public String getNickname() { return nickname; }

    public String getProfile_image() { return profile_image; }

    public String getIntroduce() { return introduce; }
}
