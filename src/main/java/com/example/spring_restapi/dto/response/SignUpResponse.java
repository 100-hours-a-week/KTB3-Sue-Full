package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SignUpResponse {
    @Schema(description = "회원가입 응답 - 사용자 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "회원가입 응답 - 이메일", example = "email@user.com")
    private String email;

    @Schema(description = "회원가입 응답 - 닉네임", example = "sue")
    private String nickname;

    @Schema(description = "회원가입 응답 - 프로필 이미지", example = "profile.jpg")
    private String profile_image;

    @Schema(description = "회원가입 응답 - 소개말", example = "hi, i'm new user")
    private String introduce;

    public SignUpResponse(Long user_id, String email, String nickname, String profile_image, String introduce){
        this.user_id = user_id;
        this.email = email;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
    }

    public Long getUser_id() { return user_id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getProfile_image() { return profile_image; }
    public String getIntroduce() { return introduce; }
}
