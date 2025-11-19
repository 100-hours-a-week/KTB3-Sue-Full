package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
@Getter
public class UserIdBodyRequest {
    @Schema(description = "유저 아이디 요청", example = "user_id")
    private Long user_id;
}
