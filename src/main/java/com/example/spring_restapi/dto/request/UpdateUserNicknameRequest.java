package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserNicknameRequest {
    @Schema(description = "유저 닉네임 수정", example = "newNickname")
    private String nickname;

    public String getNickname() { return nickname; }
}
