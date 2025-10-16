package com.example.spring_restapi.dto.request;

public class CreateCommentRequest {
    private Long author_id;
    private String content;

    public Long getAuthor_id() { return author_id; }

    public String getContent() { return content; }
}
