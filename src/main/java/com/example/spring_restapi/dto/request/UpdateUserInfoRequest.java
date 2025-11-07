package com.example.spring_restapi.dto.request;

import com.example.spring_restapi.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateUserInfoRequest {
    @Schema(description = "유저 정보 수정 - 이메일", example = "newEmail@email.com")
    private String email;

    @Schema(description = "유저 정보 수정 - 현재 비밀번호", example = "currentPassword")
    private String currentPassword;

    @Schema(description = "유저 정보 수정 - 새 비밀번호", example = "newPassword")
    private String newPassword;

    @Schema(description = "유저 정보 수정 - 새 비밀번호 Confirm", example = "newPasswordConfirm")
    private String newPasswordConfirm;

    @Schema(description = "유저 정보 수정 - 역할", example = "User/ADNIN")
    private UserRole userRole;
}
