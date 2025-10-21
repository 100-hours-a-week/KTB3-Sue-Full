package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserIntroduceRequest {
    @Schema(description = "유저 소개말 수정", example = "newIntroduce")
    private String introduce;

    public String getIntroduce() { return introduce; }
}
