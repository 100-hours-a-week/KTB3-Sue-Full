package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserResponse {
    @Schema(description = "유저 정보 응답 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "유저 정보 응답 - 유저 닉네임", example = "sue")
    private String nickname;

    @Schema(description = "유저 정보 응답 - 유저 프로필 이미지", example = "profileImage.jpg")
    private String profile_image;

    @Schema(description = "유저 정보 응답 - 유저 소개말", example = "hello i'm sue")
    private String introduce;

    public UserResponse(Long user_id, String nickname, String profile_image, String introduce){
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
    }

    public Long getUser_id() { return user_id; }
    public String getNickname() { return nickname; }
    public String getProfile_image() { return profile_image; }
    public String getIntroduce() { return introduce; }
}
