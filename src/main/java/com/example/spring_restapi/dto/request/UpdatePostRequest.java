package com.example.spring_restapi.dto.request;

import com.example.spring_restapi.model.PostImages;
import com.example.spring_restapi.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePostRequest {
    @Schema(description = "게시물 수정 - 유저 아이디", example = "1L")
    private Long author_id;

    @Schema(description = "게시물 수정 - 제목", example = "TIL updated")
    private String title;

    @Schema(description = "게시물 수정 - 내용", example = "update... Today I learned...")
    private String content;

    @Schema(description = "게시글 수정 - 이미지", example = "[1.png, 2,.jpg, ...]")
    private List<String> images;

    @Schema(description = "게시물 수정 - 게시글 카테고리", example = "NOTICE/FREE")
    private PostType postType;
}
