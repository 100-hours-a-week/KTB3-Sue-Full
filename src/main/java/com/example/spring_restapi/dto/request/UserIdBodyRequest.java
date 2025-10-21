package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserIdBodyRequest {
    @Schema(description = "유저 아이디 요청", example = "user_id")
    private Long user_id;

    public Long getUser_id() { return user_id; }
}
