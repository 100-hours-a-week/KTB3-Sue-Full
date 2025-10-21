package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserResponse {
    @Schema(description = "회원정보 수정 응답 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "회원정보 수정 응답 - 닉네임", example = "sujing")
    private String nickname;

    @Schema(description = "회원정보 수정 응답 - 프로필 이미지", example = "updateImage.jpg")
    private String profile_image;

    @Schema(description = "회원정보 수정 응답 - 소개말", example = "It's updated profile...")
    private String introduce;

    public UpdateUserResponse(Long user_id, String nickname, String profile_image, String introduce){
        this.user_id = user_id;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
    }

    public Long getUser_id() { return user_id; }
    public String getNickname() { return nickname; }
    public String getProfile_image() { return profile_image; }
    public String getIntroduce() { return  introduce; }
}
