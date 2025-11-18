package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EmailCheckRequest {
    @Schema(description = "이메일 중복 체크 요청", example = "email@email.com")
    private String email;
}
