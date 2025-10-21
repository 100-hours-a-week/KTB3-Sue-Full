package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.model.Post;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class ReadPostByPageResponse {
    @Schema(description = "게시물 조회 데이터", example = "title: ..., content: ...,")
    private List<Post> postList = new ArrayList<>();

    public ReadPostByPageResponse(List<Post> postList) {
        this.postList = postList;
    }
    public List<Post> getPostList(){ return postList; }
}
