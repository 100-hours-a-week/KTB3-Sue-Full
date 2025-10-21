package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class DeletePostRequest {
    @Schema(description = "게시물 삭제 - 유저 아이디", example = "1L")
    private Long user_id;

    public Long getUser_id() { return user_id; }
}
