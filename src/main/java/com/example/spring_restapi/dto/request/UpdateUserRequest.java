package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserRequest {
    @Schema(description = "유저 정보 수정 - 현재 비밀번호", example = "currentPassword")
    private String currentPassword;

    @Schema(description = "유저 정보 수정 - 새 비밀번호", example = "newPassword")
    private String newPassword;

    @Schema(description = "유저 정보 수정 - 새 닉네임", example = "newNickname")
    private String nickname;

    @Schema(description = "유저 정보 수정 - 새 프로필 이미지", example = "newProfileImage")
    private String profile_image;

    @Schema(description = "유저 정보 수정 - 새 소개말", example = "newIntroduce")
    private String introduce;

    public String getCurrentPassword(){ return currentPassword; }

    public String getNewPassword(){ return newPassword; }

    public String getNickname(){ return nickname; }

    public String getProfile_image() { return profile_image; }

    public String getIntroduce(){ return introduce; }
}
