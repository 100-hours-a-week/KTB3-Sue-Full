package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateUserGenderRequest {
    @Schema(description = "유저 성별 수정", example = "M")
    private String gender;

}
