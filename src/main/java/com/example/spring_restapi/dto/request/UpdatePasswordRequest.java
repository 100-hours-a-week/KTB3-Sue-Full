package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {
    @Schema(description = "비밀번호 수정 - 현재 비밀번호", example = "currentPassword")
    private String currentPassword;

    @Schema(description = "비밀번호 수정 - 새 비밀번호", example = "newPassword")
    private String newPassword;

    @Schema(description = "비밀번호 수정 - 새 비밀번호 Confirm", example = "newPasswordConfirm")
    private String newPasswordConfirm;

}
