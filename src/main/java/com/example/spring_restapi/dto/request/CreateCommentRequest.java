package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateCommentRequest {
    @Schema(description = "댓글 작성 - 작성자 아이디", example = "1L")
    private Long author_id;

    @Schema(description = "댓글 작성 - 내용", example = "cool")
    private String content;

    public Long getAuthor_id() { return author_id; }

    public String getContent() { return content; }
}
