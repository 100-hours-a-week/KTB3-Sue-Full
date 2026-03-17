package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@NoArgsConstructor
@Getter
public class UpdateUserNicknameProfileImageReqeust {
    @Schema(description = "회원 정보 수정 요청 - 유저 아이디", example = "1")
    private Long user_id;

    @Schema(description = "회원 정보 수정 요청 -  현재 닉네임", example = "nickname")
    private String currentNickname;

    @Schema(description = "회원 정보 수정 요청 -  새 닉네임", example = "nickname")
    private String newNickname;

    @Schema(description = "회원 정보 수정 요청 - 현재 프로필 이미지", example = "currentProfile.jpg")
    private String currentProfileImage;

    @Schema(description = "회원 정보 수정 요청 - 새 프로필 이미지", example = "newProfile.jpg")
    private MultipartFile newProfileImage;
}
