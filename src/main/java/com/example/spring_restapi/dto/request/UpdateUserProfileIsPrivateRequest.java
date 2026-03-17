package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateUserProfileIsPrivateRequest {
    @Schema(description = "유저 프로필 비공개 여부 수정", example = "true")
    private Boolean isPrivate;
}
