package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
@Getter
public class DeletePostRequest {
    @Schema(description = "게시물 삭제 - 유저 아이디", example = "1L")
    private Long user_id;
}
