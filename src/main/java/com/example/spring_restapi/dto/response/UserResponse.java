package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserResponse {
    @Schema(description = "유저 정보 응답 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "유저 정보 응답 - 유저 이메일", example = "user@email.com")
    private String email;

    @Schema(description = "유저 정보 응답 - 유저 역할", example = "USER or ADMIN")
    private UserRole userRole;

    @Schema(description = "유저 정보 응답 - 유저 닉네임", example = "userNickname")
    private String nickname;

    @Schema(description = "유저 정보 응답 - 유저 프로필 이미지", example = "image.jpg")
    private String profileImage;

    @Schema(description = "유저 정보 응답 - 유저 프로필 소개", example = "hello world")
    private String introduce;

    @Schema(description = "유저 정보 응답 - 유저 프로필 성별", example = "hello world")
    private String gender;

    @Schema(description = "유저 정보 응답 - 유저 프로필 비공개 여부", example = "hello world")
    private Boolean isPrivate;

    public UserResponse(Long user_id, String email, UserRole userRole, String nickname, String profileImage, String introduce, String gender, Boolean isPrivate) {
        this.user_id = user_id;
        this.email = email;
        this.userRole = userRole;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.gender = gender;
        this.isPrivate = isPrivate;
    }
}
