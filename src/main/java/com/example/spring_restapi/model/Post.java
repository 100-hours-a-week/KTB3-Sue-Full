package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Post {
    @Schema(description = "게시글 아이디", example = "1L")
    private Long id;

    @Schema(description = "게시글 작성자 아이디", example = "2L")
    private Long author_id;

    @Schema(description = "게시글 제목", example = "TIL")
    private String title;

    @Schema(description = "게시글 내용", example = "오늘은 Swagger에 대해 배웠다...")
    private String content;

    @Schema(description = "게시글의 조회수", example = "5")
    private Integer watch;

    @Schema(description = "게시글의 좋아요 수", example = "3")
    private Integer like_count;

    @Schema(description = "게시글의 댓글 수", example = "1")
    private Integer comment_count;

    @Schema(description = "게시글 작성 일자", example = "20251020T10:00:00")
    private LocalDateTime write_date;

    @Schema(description = "게시글 수정 일자", example = "20251022T10:00:00")
    private LocalDateTime rewrite_date;

    protected Post() {}

    public Post(Long author_id, String title, String content, Integer watch, Integer like_count, Integer comment_count, LocalDateTime write_date, LocalDateTime rewrite_date){
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.watch = watch;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.write_date = write_date;
        this.rewrite_date = rewrite_date;
    }

}
