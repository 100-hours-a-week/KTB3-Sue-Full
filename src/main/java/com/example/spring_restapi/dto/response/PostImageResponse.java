package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.model.PostImages;
import lombok.Getter;

import java.util.List;

@Getter
public class PostImageResponse {

    private Long post_id;

    private String image_url;

    private Boolean is_thumbnail;

    public PostImageResponse(Long post_id, String image_url, Boolean is_thumbnail){
        this.post_id = post_id;
        this.image_url = image_url;
        this.is_thumbnail = is_thumbnail;
    }
}
