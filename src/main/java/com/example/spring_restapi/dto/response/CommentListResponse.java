package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponse {

    @Schema(description = "댓글 목록 응답 - 게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "댓글 목록 응답 - 댓글 목록", example = "[comment1, comment2, ...]")
    private List<CommentResponse> comments;

    public CommentListResponse(Long post_id, List<CommentResponse> comments){
        this.post_id = post_id;
        this.comments = comments;
    }
}
