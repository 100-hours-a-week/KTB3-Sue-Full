package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.response.PostResponse;
import com.example.spring_restapi.model.Post;

import java.util.List;

public interface PostService {

    PostResponse write(CreatePostRequest req);

    PostResponse getPostByPostId(Long post_id);

    List<PostResponse> getPostByAuthorId(Long authorId);

    List<PostResponse> getPostsOfPage(int page, int size);

    PostResponse updatePost(Long post_id, UpdatePostRequest req);

    PostResponse deletePost(Long post_id, Long user_id);

}