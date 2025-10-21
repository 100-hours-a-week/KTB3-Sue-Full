package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdatePasswordRequest {
    @Schema(description = "비밀번호 수정 - 현재 비밀번호", example = "currentPassword")
    private String currentPassword;

    @Schema(description = "비밀번호 수정 - 새 비밀번호", example = "newPassword")
    private String newPassword;

    public String getCurrentPassword() { return currentPassword; }

    public String getNewPassword() { return newPassword; }
}
