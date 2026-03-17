package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.model.PostImages;
import com.example.spring_restapi.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {

    @Schema(description = "게시글 응답 - 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "게시글 응답 - 작성자 닉네임", example = "sue")
    private String authorNickname;

    @Schema(description = "게시글 응답 - 작성자 프로필 이미지", example = "sue.jpg")
    private String authorProfileImage;

    @Schema(description = "게시글 응답 - 제목", example = "TIL")
    private String title;

    @Schema(description = "게시글 응답 - 내용", example = "오늘은 Swagger에 대해 배웠다...")
    private String content;

    @Schema(description = "게시글 응답 - 이미지", example = "[1.png, 2,.jpg, ...]")
    private List<PostImageResponse> images;

    @Schema(description = "게시글 응답 - 카테고리", example = "NOTICE/FREE")
    private PostType postType;

    @Schema(description = "게시글 응답 - 조회수", example = "5")
    private Integer watch;

    @Schema(description = "게시글 응답 - 좋아요 수", example = "3")
    private Integer likeCount;

    @Schema(description = "게시글 응답 - 댓글 수", example = "1")
    private Integer commentCount;

    @Schema(description = "게시글 응답 - 생성일자", example = "20251020T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 응답 - 수정일자", example = "20251020T10:00:00")
    private LocalDateTime updatedAt;

    public PostResponse(
            Long post_id,
            String authorNickname,
            String authorProfileImage,
            String title,
            String content,
            List<PostImageResponse> images,
            PostType postType,
            Integer watch,
            Integer likeCount,
            Integer commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.post_id = post_id;
        this.authorNickname = authorNickname;
        this.authorProfileImage = authorProfileImage;
        this.title = title;
        this.content = content;
        this.images = images;
        this.postType = postType;
        this.watch = watch;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
