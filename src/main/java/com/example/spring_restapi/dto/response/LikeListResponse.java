package com.example.spring_restapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class LikeListResponse {

    @Schema(description = "좋아요 목록 응답 - 좋아요를 누른 유저", example = "[1L, 2L, ...]")
    private List<LikeResponse> likes;

    @Schema(description = "좋아요 목록 응답 - 좋아요 수 ", example = "1L")
    private Integer likeCount;

    public LikeListResponse(List<LikeResponse> likes){
        this.likes = likes;
        this.likeCount = likes.size();
    }
}
