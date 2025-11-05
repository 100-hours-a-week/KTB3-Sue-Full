package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LikeResponse {

    @Schema(description = "좋아요 응답 - 좋아요 아이디", example = "3L")
    private Long id;

    @Schema(description = "좋아요 응답 - 게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "좋아요 응답 - 게시글에 좋아요를 누른 유저 아이디", example = "1L")
    private Long user_id;

    public LikeResponse(Long id, Long post_id, Long user_id){
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
    }
}
