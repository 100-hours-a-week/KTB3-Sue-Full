package com.example.spring_restapi.service;

import com.example.spring_restapi.dto.request.CreatePostRequest;
import com.example.spring_restapi.dto.request.UpdatePostRequest;
import com.example.spring_restapi.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostService {

    PostResponse write(CreatePostRequest req);

    PostResponse getPostByPostId(Long post_id);

    List<PostResponse> getPostByAuthorId(Long authorId);

    Page<PostResponse> getPostsOfPage(int page, int size);

    PostResponse updatePost(Long post_id, UpdatePostRequest req);

    PostResponse deletePost(Long post_id, Long user_id);

    List<PostResponse> searchAsList(String keyword);

    Page<PostResponse> searchAsPage(String keyword, int page, int size, String sortBy, String direction);

    Slice<PostResponse> searchAsSlice(String keyword, int page, int size, String sortBy, String direction);

}