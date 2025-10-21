package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserProfileImageRequest {
    @Schema(description = "유저 프로필 이미지 수정", example = "newProfileImage.jpg")
    private String profile_image;

    public String getProfile_image() { return profile_image; }
}
