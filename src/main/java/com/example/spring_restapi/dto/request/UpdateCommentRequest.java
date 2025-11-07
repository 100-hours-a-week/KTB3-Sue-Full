package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @Schema(description = "댓글 수정 - 작성자 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "댓글 수정 - 내용", example = "comment update...")
    private String content;

}
