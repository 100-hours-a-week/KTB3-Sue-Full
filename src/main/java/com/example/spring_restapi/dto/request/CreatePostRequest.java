package com.example.spring_restapi.dto.request;

import java.util.List;

public class CreatePostRequest {
    private Long author_id;
    private String title;
    private String content;
    private List<String> images;

    public Long getAuthor_id() { return author_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }
}
