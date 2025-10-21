package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UpdatePostRequest {
    @Schema(description = "게시물 수정 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "게시물 수정 - 제목", example = "TIL updated")
    private String title;

    @Schema(description = "게시물 수정 - 내용", example = "update... Today I learned...")
    private String content;

    @Schema(description = "게시물 수정 - 콘텐츠 이미지", example = "udpateImages.jpg")
    private List<String> images;

    public Long getUser_id() { return user_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }
}
