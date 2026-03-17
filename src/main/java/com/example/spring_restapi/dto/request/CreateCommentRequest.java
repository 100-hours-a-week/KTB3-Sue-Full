package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @Schema(description = "댓글 작성 - 작성자 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "댓글 작성 - 내용", example = "cool")
    private String content;
}
