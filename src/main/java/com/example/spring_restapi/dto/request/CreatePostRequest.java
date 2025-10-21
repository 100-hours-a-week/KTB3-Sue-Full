package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CreatePostRequest {
    @Schema(description = "게시글 작성 - 작성자 아이디", example = "1L")
    private Long author_id;

    @Schema(description = "게시글 작성 - 제목", example = "TIL")
    private String title;

    @Schema(description = "게시글 작성 - 내용", example = "Today I learned...")
    private String content;

    @Schema(description = "게시글 작성 - 콘텐츠 이미지", example = "content images")
    private List<String> images;

    public Long getAuthor_id() { return author_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }
}
