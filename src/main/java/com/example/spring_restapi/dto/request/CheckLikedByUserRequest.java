package com.example.spring_restapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CheckLikedByUserRequest {

    @Schema(description = "좋아요 여부 확인 - 유저 아이디", example = "1L")
    private Long user_id;

    @Schema(description = "좋아요 여부 확인 - 게시글 아이디", example = "2L")
    private Long post_id;
}
