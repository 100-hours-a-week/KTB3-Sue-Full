package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NicknameCheckRequest {
    @Schema(description = "닉네임 중복 체크 요청", example = "nickname")
    private String nickname;
}
