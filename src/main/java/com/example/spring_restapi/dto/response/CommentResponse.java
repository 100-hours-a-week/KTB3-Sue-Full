package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommentResponse {

    @Schema(description = "댓글 응답 - 아이디", example = "2L")
    private Long id;

    @Schema(description = "댓글 응답 - 게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "댓글 응답 - 작성자 아이디", example = "3L")
    private Long user_id;

    @Schema(description = "댓글 응답 - 내용", example = "cool...")
    private String content;

    public CommentResponse(Long id, Long post_id, Long user_id, String content){
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.content = content;
    }
}
