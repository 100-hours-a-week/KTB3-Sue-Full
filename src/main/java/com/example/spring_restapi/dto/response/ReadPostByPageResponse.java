package com.example.spring_restapi.dto.response;

import com.example.spring_restapi.dto.Post;

import java.util.ArrayList;
import java.util.List;

public class ReadPostByPageResponse {
    List<Post> postList = new ArrayList<>();

    public ReadPostByPageResponse(List<Post> postList) {
        this.postList = postList;
    }
    public List<Post> getPostList(){ return postList; }
}
