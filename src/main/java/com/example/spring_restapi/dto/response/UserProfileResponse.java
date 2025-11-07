package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    @Schema(description = "유저 프로필 응답 - 유저 프로필 아이디", example = "1L")
    private Long user_profile_id;

    @Schema(description = "유저 프로필 응답 - 유저 닉네임", example = "userNickname")
    private String nickname;

    @Schema(description = "유저 프로필 응답 - 유저 프로필 이미지", example = "image.jpg")
    private String profile_image;

    @Schema(description = "유저 프로필 응답 - 유저 프로필 소개", example = "hello world")
    private String introduce;

    @Schema(description = "유저 프로필 응답 - 유저 프로필 성별", example = "F")
    private String gender;

    @Schema(description = "유저 프로필 응답 - 유저 프로필 비공개 여부", example = "false")
    private Boolean isPrivate;

    public UserProfileResponse(Long user_profile_id, String nickname, String profile_image, String introduce, String gender, Boolean isPrivate){
        this.user_profile_id = user_profile_id;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
        this.gender = gender;
        this.isPrivate = isPrivate;
    }

}
