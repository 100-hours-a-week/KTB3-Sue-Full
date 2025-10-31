package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.model.Post;

import java.util.List;

public interface PostService {

    Post write(CreatePostRequest req);

    Post getPostByPostId(Long post_id);

    List<Post> getPostByAuthorId(Long authorId);

    List<Post> findAllPosts();

    List<Post> getPostsOfPage(int page, int size);

    Post updatePost(Long post_id, UpdatePostRequest req);

    Post deletePost(Long post_id, Long user_id);

}