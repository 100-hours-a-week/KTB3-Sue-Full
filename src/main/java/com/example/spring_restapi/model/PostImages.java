package com.example.spring_restapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PostImages {

    @Schema(description = "게시글 이미지 아이디", example = "1L")
    private Long id;

    @Schema(description = "게시글 아이디", example = "1L")
    private Long post_id;

    @Schema(description = "게시글 콘텐츠 이미지", example = "post1.jpg")
    private String image_url;

    @Schema(description = "게시글 썸네일 여부", example = "false")
    private Boolean is_thumbnail;

    protected PostImages() {}

    public PostImages(Long post_id, String image_url, Boolean is_thumbnail){
        this.post_id = post_id;
        this.image_url = image_url;
        this.is_thumbnail = is_thumbnail;
    }
}
