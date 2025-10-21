package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class Comment {
    @Schema(description = "댓글 아이디", example = "2L")
    private Long comment_id;

    @Schema(description = "게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "작성자 아이디", example = "3L")
    private Long author_id;

    @Schema(description = "댓글 내용", example = "cool...")
    private String content;

    @Schema(description = "댓글 작성일자", example = "20251020T10:00:00")
    private LocalDateTime date;

    @Schema(description = "댓글 수정일자", example = "20251022T10:00:00")
    private LocalDateTime rewriteDate;

    public Comment() {}

    public Comment(Long comment_id, Long post_id, Long author_id, String content, LocalDateTime date){
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.author_id = author_id;
        this.content = content;
        this.date = date;
    }

    // Setter
    public void setComment_id(Long comment_id) { this.comment_id = comment_id; }

    public void setPost_id(Long post_id) { this.post_id = post_id; }

    public void setAuthor_id(Long author_id) { this.author_id = author_id; }

    public void setContent(String content) { this.content = content; }

    public void setDate(LocalDateTime date) { this.date = date; }

    public void setRewriteDate(LocalDateTime date) { this.date = date; }
    // Getter
    public Long getComment_id() { return comment_id; }

    public Long getPost_id() { return post_id; }

    public Long getAuthor_id() { return author_id; }

    public String getContent() { return this.content; }

    public LocalDateTime getDate() { return this.date; }
}
