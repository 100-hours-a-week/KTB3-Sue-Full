package com.example.spring_restapi.dto.request;

import java.util.List;

public class UpdatePostRequest {
    private Long user_id;
    private String title;
    private String content;
    private List<String> images;

    public Long getUser_id() { return user_id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public List<String> getImages() { return images; }
}
