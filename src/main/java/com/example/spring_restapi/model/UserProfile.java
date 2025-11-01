package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserProfile {

    private Long id;

    @Schema(description = "사용자 닉네임", example = "sue")
    private String nickname;

    @Schema(description = "사용자 프로필 이미지", example = "profileImage.jpg")
    private String profile_image;

    @Schema(description = "사용자 소개말", example = "Hi, I'm sue")
    private String introduce;

    @Schema(description = "사용자 성별", example = "F")
    private String gender;

    @Schema(description = "사용자 계정 공개여부", example = "false")
    private Boolean is_private;

    protected UserProfile() {}

    public UserProfile(String nickname, String profile_image, String introduce, String gender, Boolean is_private){
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.introduce = introduce;
        this.gender = gender;
        this.is_private = is_private;
    }
}
