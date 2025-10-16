package com.example.spring_restapi.dto;

import java.time.LocalDateTime;

public class Comment {
    private Long comment_id;
    private Long post_id;
    private Long author_id;
    private String content;
    private LocalDateTime date;
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
